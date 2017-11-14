package pseudo_torrent;

import model.FixedBitSet;
import model.Seeder;
import model.Video;
import model.util.VideoUtil;
import pseudo_torrent.message.*;
import pseudo_torrent.request.PeerAddress;

import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Created by satyan on 11/10/17.
 * A seeder server, a tracker and an uploader
 */
public class SeederServer extends Thread {
    private final static Logger LOGGER = Logger.getLogger("SeederServer");
    private final Seeder seeder;
    private final TrackerServer trackerServer;
    private final DatagramSocket socket;
    private final PeerAddress myAddr;
    private final Map<String, CommunicationTable> tableMap = new HashMap<>();
    private final BitSet field;
    private final Postman postman = Postman.getInstance();
    private Random random = new Random(System.currentTimeMillis());



    private final Message choke;
    private final Message unchoke;
    private final Message interested;
    private final Message notInterested;
    private final Message handshake;
    private final Message keepAlive;

    private final MessageHandler handler = new MessageHandler() {

        @Override
        public void handleBitField(BitField msg) {
        }

        @Override
        public void handleUnchoke(Unchoke msg) {
            LOGGER.info("Unchoked by " + msg.getSenderId());
            CommunicationTable table = tableMap.get(msg.getSenderId());
            if (table != null) {
                table.getMyState().setChoked(false);
                postman.send(socket, msg.getAddress(), msg.getPort(), unchoke);
                table.getPeerState().setChoked(false);
            }
        }

        @Override
        public void handleInterested(Interested msg) {
            LOGGER.info("Interested");
            String senderId = msg.getSenderId();
            if (tableMap.containsKey(senderId)){
                CommunicationTable table = tableMap.get(senderId);
                PeerState state = table.getPeerState();
                state.setInterested(true);
                if (!state.isChoked()) {
                    if (!table.getMyState().isChoked()) {
                        // each set bit has a certain probability to be sent
                        BitSet set = new BitSet(field.size());
                        IntStream in = field.stream();
                        Iterator<Integer> it = in.iterator();
                        while (it.hasNext()){
                            int index = it.next();
                            if (random.nextInt(100) < 85){
                                set.set(index, true);
                            }
                        }
                        postman.send(socket, msg.getAddress(), msg.getPort(), new BitField(myAddr.getId(), set.get(0,field.size())));
                    }
                }
            }
        }

        @Override
        public void handleNotInterested(NotInterested msg) {
            CommunicationTable table = tableMap.get(msg.getSenderId());
            if (table != null){
                table.getPeerState().setInterested(false);
            }
        }

        @Override
        public void handleHandshake(Handshake msg) {
            LOGGER.info("Received handshake from " + msg.getSenderId());
            String senderId = msg.getSenderId();
            Video video = seeder.getVideo();
            String checksum = video.getChecksum();
            // If the video check matches mine
            if (msg.check(checksum)) {
                if (!tableMap.containsKey(senderId)) {
                    CommunicationTable table = new CommunicationTable(video.getChunkCount());
                    table.handshakeReceived();
                    tableMap.put(senderId, table);
                    postman.send(socket, msg.getAddress(), msg.getPort(), handshake);
                    table.handshakeSent();
                } else {
                    CommunicationTable table = tableMap.get(senderId);
                    table.handshakeReceived();
                    if (!table.isHandshakeSent()){
                        postman.send(socket, msg.getAddress(), msg.getPort(), handshake);
                        table.handshakeSent();
                    }
                }
            }
        }

        @Override
        public void handleHave(Have msg) {
        }

        @Override
        public void handleChoke(Choke msg) {
            CommunicationTable table = tableMap.get(msg.getSenderId());
            if (table != null){
                table.getMyState().setChoked(true);
            }
        }

        @Override
        public void handleKeepAlive(KeepAlive msg) {
            //nothing to do
        }

        @Override
        public void handleBlock(Block msg) {
            // nothing to do, might cause some issues for the next message
        }

        @Override
        public void handleRequest(Request msg) {
            CommunicationTable table = tableMap.get(msg.getSenderId());
            if (table != null) {
                if (!table.getMyState().isChoked()) {
                    LOGGER.info("Request: " + msg.toString());
                    ByteBuffer buffer = VideoUtil.getPiece(seeder.getVideo(), msg.getOffset(), msg.getLength());
                    if (buffer != null) {
                        LOGGER.info("Piece retrieved: " + buffer.remaining());
                        Block block = new Block(myAddr.getId(), msg.getIndex(), msg.getOffset(), msg.getLength(),buffer);
                        LOGGER.info("Block length: " + block.getLength());
                        postman.send(socket, msg.getAddress(), msg.getPort(), block);
                        LOGGER.info("Block sent");
                    }
                }
            }
        }
    };


    public SeederServer(String address, Video video) throws SocketException, UnknownHostException {
        this.trackerServer = new TrackerServer(this);
        seeder = new Seeder(video, address, trackerServer.getPort());
        socket = new DatagramSocket(0);
        socket.setSoTimeout(30*1000);
        field = new BitSet(video.getChunkCount());
        LOGGER.info("Chunks: " + video.getChunkCount());
        field.flip(0,video.getChunkCount());
        int uploadPort = socket.getLocalPort();
        String id = String.format("SED%017d",random.nextInt(1000));
        this.myAddr = new PeerAddress(id, seeder.getInetAddres(), uploadPort);

        choke = new Choke(id);
        unchoke = new Unchoke(id);
        interested = new Interested(id);
        notInterested = new NotInterested(id);
        keepAlive = new KeepAlive();
        handshake = new Handshake(id, video.getChecksum());
    }

    public Seeder getSeeder() {
        return seeder;
    }

    public PeerAddress getAddress() {
        return myAddr;
    }

    @Override
    public void run() {
        trackerServer.start();
        long lastContact = System.currentTimeMillis() + 60_000;
        while(!isInterrupted()){
            LOGGER.info("Seeder on " + socket.getLocalPort());
            try {
                receiveRequests();
                long current = System.currentTimeMillis();
                if (lastContact - current > 60_000){
                    lastContact = current;
                }
            } catch (SocketTimeoutException e) {
                LOGGER.info("Timeout");
            }
        }

        LOGGER.warning("GAME OVER");
    }

    private void receiveRequests() throws SocketTimeoutException {
        Message msg = postman.receive(socket);
        if (msg != null){
            LOGGER.info("Received " + msg.getType() + " from " + msg.getSenderId());
            handler.handle(msg);
        }
    }
}
