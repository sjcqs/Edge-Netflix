package pseudo_torrent;

import model.Chunk;
import model.Seeder;
import model.Video;
import model.util.VideoUtil;
import pseudo_torrent.message.*;
import pseudo_torrent.request.PeerAddress;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.IntConsumer;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Created by satyan on 10/31/17.
 * The peer client is in charge of downloaded chunks and sending updates to the server
 * PEER PROTOCOL
 *  table: am_choke = 1, am_interested = 0, peer_chocked = 1, peer_interested = 0
 */
public class Peer extends Thread{
    private final static Logger LOGGER = Logger.getLogger(Peer.class.getName());
    private static final int DOWNLOADER_COUNT = 4;
    private static final int UPLOADER_COUNT = 3;
    private static final long SHUFFLE_INTERVAL = 30_000;

    private final Message choke;
    private final Message unchoke;
    private final Message interested;
    private final Message notInterested;
    private final Message handshake;
    private final Message keepAlive;

    private final MessageHandler handler = new MessageHandler() {

        @Override
        public void handleBitField(BitField msg) {
            LOGGER.info("BITFIELD from " + msg.getSenderId());
            String id = msg.getSenderId();
            if (tableMap.containsKey(id)) {
                CommunicationTable table = tableMap.get(id);
                table.setField(msg.getField());
                BitSet diff = diff(table);
                PeerAddress uploader = getPeer(id, uploaders);
                if (!table.getMyState().isChoked() && uploader != null) { // otherwise I cannot contact him
                    if (diff.isEmpty()) { // there's not block I don't already have
                        LOGGER.info("NOT INTERESTED");
                        postman.send(socket, msg.getAddress(), msg.getPort(), notInterested);
                        table.getMyState().setInterested(false);
                    } else {
                        // if I am not interested yet
                        if (!table.getMyState().isInterested()) {
                            LOGGER.info("INTERESTED AFTER BITFIELD");
                            postman.send(socket, msg.getAddress(), msg.getPort(), interested);
                            table.getMyState().setInterested(true);
                        } else {
                            LOGGER.info("TRY TO SEND REQUEST");
                            if (!awaitingRequests.containsKey(uploader.getId())){
                                LOGGER.info("NO REQUESTS");
                                createRequestQueue(uploader);
                            } else {
                                LOGGER.info("SEND REQUEST");
                                sendRequest(uploader);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void handleUnchoke(Unchoke msg) {
            LOGGER.info("Unchoke");
            String id = msg.getSenderId();
            CommunicationTable table = tableMap.get(id);
            if (table != null) {
                PeerState peerState = table.getPeerState();
                table.getMyState().setChoked(false);
                if (peerState.isChoked()) {
                    PeerAddress downloader = getPeer(id, downloaders);
                    if (downloader != null) {
                        postman.send(socket, msg.getAddress(), msg.getPort(), unchoke);
                        table.getPeerState().setChoked(false);
                    }
                } else {
                    PeerAddress uploader = getPeer(id, uploaders);
                    if (uploader != null){
                        LOGGER.info("INTERESTED AFTER UNCHOKE");
                        postman.send(socket, msg.getAddress(), msg.getPort(), interested);
                        table.getMyState().setInterested(true);
                    }
                }
            }
        }

        @Override
        public void handleInterested(Interested msg) {
            String senderId = msg.getSenderId();
            if (tableMap.containsKey(senderId)){
                CommunicationTable table = tableMap.get(senderId);
                PeerState state = table.getPeerState();
                state.setInterested(true);
                if (!state.isChoked()) {
                    if (!table.getMyState().isChoked()) {
                        postman.send(socket, msg.getAddress(), msg.getPort(), new BitField(myId, field));
                    }
                }
            }
        }

        @Override
        public void handleNotInterested(NotInterested msg) {
            String id = msg.getSenderId();
            CommunicationTable table = tableMap.get(id);
            if (table != null){
                table.getPeerState().setInterested(false);
            }
        }

        @Override
        public void handleHandshake(Handshake msg) {
            String id = msg.getSenderId();
            LOGGER.info(id);
            CommunicationTable table = tableMap.get(id);
            if (msg.check(video.getChecksum())) {
                if (table != null) {
                    table.handshakeReceived();
                    if (!table.isHandshakeSent()) {
                        postman.send(socket, msg.getAddress(), msg.getPort(), handshake);
                        table.handshakeSent();
                    } else {
                        PeerAddress uploader = getPeer(id, uploaders);
                        if (uploader != null) {
                            postman.send(socket, msg.getAddress(), msg.getPort(), unchoke);
                            table.getPeerState().setChoked(false);
                        }
                    }
                } else { // new downloader

                    table = new CommunicationTable(field.size());
                    table.handshakeReceived();
                    table.handshakeSent();
                    tableMap.put(msg.getSenderId(), table);

                    waitingDownloaders.add(new PeerAddress(id, msg.getAddress(), msg.getPort()));
                    postman.send(socket, msg.getAddress(), msg.getPort(), handshake);
                }
            }
        }

        @Override
        public void handleHave(Have msg) {
            String id = msg.getSenderId();
            CommunicationTable table = tableMap.get(id);
            if (table != null){
                table.setField(msg.getIndex(), true);
                // if I am not choked
                PeerAddress uploader = getPeer(id, uploaders);
                if (!table.getMyState().isChoked() && uploader != null){
                    if (!diff(table).isEmpty()){
                        if (!table.getMyState().isInterested()){
                            LOGGER.info("INTERESTED AFTER HAVE");
                            postman.send(socket, msg.getAddress(), msg.getPort(), interested);
                            table.getMyState().setInterested(true);
                            // DO SOMETHING ELSE TOO
                        }
                    } else {
                        if (table.getMyState().isInterested()){
                            postman.send(socket, msg.getAddress(), msg.getPort(), notInterested);
                            table.getMyState().setInterested(false);
                        }
                    }
                }
            }
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
            // nothing to do
        }

        @Override
        public void handleBlock(Block msg) {
            try {
                CommunicationTable table = tableMap.get(msg.getSenderId());
                if (table != null) {
                    PeerAddress uploader = getPeer(msg.getSenderId(), uploaders);
                    // NOTE: I shouldn't test if it's an uploader, he would be choked if he isn't
                    if (uploader != null && !downloaded && !field.get(msg.getIndex())) {
                        LOGGER.info("RECEIVE BLOCK");
                        final int index = msg.getIndex();
                        final int length = msg.getLength();

                        Chunk chunk = downloadingChunks.get(index);
                        if (chunk == null) {
                            String checksum = video.getChecksums().get(index);
                            chunk = new Chunk(index, checksum, video.getChunkSize(index));
                            downloadingChunks.set(index, chunk);
                        }
                        LOGGER.info("CHUNK: " + chunk.getIndex());
                        chunk.put(msg.getData(), 0, length);
                        LOGGER.info("PUT DATA");

                        // LAST REQUEST TREATED
                        if (awaitingRequests.containsKey(msg.getSenderId())) {
                            Deque<Request> requests = awaitingRequests.get(msg.getSenderId());
                            if (!requests.isEmpty()) {
                                requests.removeFirst();
                                LOGGER.info("RECEIVED LEFTS:" + (requests.size()));
                            }
                        }

                        if (chunk.remaining() == 0) {
                            awaitingRequests.remove(msg.getSenderId());
                            if (chunk.check()) {
                                try {
                                    chunk.write(VideoUtil.getPartFile(video, index));
                                    field.set(index, true);
                                    Have have = new Have(index, myId);
                                    for (PeerAddress downloader : waitingDownloaders) {
                                        postman.send(socket, downloader.getAddress(), downloader.getPort(), have);
                                    }
                                    for (PeerAddress up : uploaders) {
                                        postman.send(socket, up.getAddress(), up.getPort(), have);
                                    }
                                    LOGGER.info("DOWNLOADED AND SAVED !!!!!");
                                } catch (IOException e) {
                                    LOGGER.warning("ERROR: " + e.getMessage());
                                }

                            } else {
                                chunk.clear();
                                chunkLeft.add(index);
                            }
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }

        @Override
        public void handleRequest(Request msg) {
            LOGGER.info("Request: " + msg.getType() + " from " + msg.getSenderId());
            CommunicationTable table = tableMap.get(msg.getSenderId());
            if (table != null) {
                if (!table.getMyState().isChoked() && !table.getPeerState().isChoked()) {
                    LOGGER.info("Request: " + msg.getType());

                    ByteBuffer buffer = VideoUtil.getPiece(video, msg.getOffset(), msg.getLength(), msg.getIndex());
                    if (buffer != null) {
                        Block block = new Block(myId, msg.getIndex(), msg.getOffset(), msg.getLength(), buffer);
                        postman.send(socket, msg.getAddress(), msg.getPort(), block);
                    }
                }
            }
        }
    };

    private final Random random = new Random(System.currentTimeMillis());

    private final Seeder seeder;
    private final Video video;
    private final Set<Integer> chunkLeft = new HashSet<>();
    private List<PeerAddress> availableUploaders = new ArrayList<>();
    private LinkedList<PeerAddress> waitingDownloaders = new LinkedList<>();

    private List<PeerAddress> downloaders = new ArrayList<>(DOWNLOADER_COUNT);
    private List<PeerAddress> uploaders = new ArrayList<>(UPLOADER_COUNT);
    private Map<String, CommunicationTable> tableMap;
    private Map<String, Deque<Request>> awaitingRequests = new HashMap<>();
    private final Postman postman = Postman.getInstance();
    private final List<Chunk> downloadingChunks;

    private int interval = 30;
    private int uploadPort;
    private DatagramSocket socket;
    private BitSet field;

    private String myId;
    private long lastUploaderShuffle = System.currentTimeMillis();
    private Thread trackerThread;
    private Request lastRequest;
    private boolean downloaded = false;

    public Peer(Seeder seeder){
        setDefaultUncaughtExceptionHandler((thread, throwable) -> LOGGER.info(throwable.getMessage()));
        Random random = new Random(System.currentTimeMillis());
        myId = String.format("PST%017d",random.nextInt(1_000_000));

        tableMap = new HashMap<>();

        availableUploaders = new ArrayList<>();

        this.seeder = seeder;
        this.video = seeder.getVideo();
        field = VideoUtil.getBitField(video);
        LOGGER.info("Field size:" + field.size() + " Chunks: " + video.getChunkCount());
        LOGGER.info("ID SIZE:" + myId.getBytes().length);

        choke = new Choke(myId);
        unchoke = new Unchoke(myId);
        interested = new Interested(myId);
        notInterested = new NotInterested(myId);
        keepAlive = new KeepAlive();
        handshake = new Handshake(myId, video.getChecksum());
        downloadingChunks = new ArrayList<>(video.getChunkCount());
        for (int i = 0; i < video.getChunkCount(); i++) {
            downloadingChunks.add(null);
            chunkLeft.add(i);
        }
    }

    public Video getVideo() {
        return video;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Seeder getSeeder(){
        return seeder;
    }

    public List<PeerAddress> getAvailableUploaders() {
        return availableUploaders;
    }

    public int getUploadPort() {
        return uploadPort;
    }

    @Override
    public void run() {
        trackerThread = new Thread(() -> {
            long lastSent = System.currentTimeMillis();
            boolean stop = false;
            while (!stop) {
                try {
                    long current = System.currentTimeMillis();
                    if (current - lastSent > interval * 1000) {
                        TrackerClient client = new TrackerClient(Peer.this, false);
                        client.run();
                        lastSent = current;
                    }
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    stop = true;
                }
            }
            new TrackerClient(Peer.this, true).run();
            LOGGER.info("TRACKER SHUT DOWN");
        });

        try {
            socket = new DatagramSocket(0);
            socket.setSoTimeout(20_000);
            this.uploadPort = socket.getLocalPort();
        } catch (SocketException e) {
            System.out.println("ERROR: cannot download the video");
        }
        trackerThread.start();

        Message msg = null;
        long lastShuffle = System.currentTimeMillis();
        while (!isInterrupted()) {

            if (!downloaded && field.cardinality() == video.getChunkCount()){
                // RECOMPOSE THE FILE
                LOGGER.info("DOWNLOAD DONE" + video.getLength() + " : " + VideoUtil.downloaded(video));
                BitSet res = VideoUtil.createVideoFile(video);
                if (res.cardinality() == video.getChunkCount()){
                    downloaded = true;
                } else {
                    field = res;
                    field.stream().iterator().forEachRemaining((IntConsumer) chunkLeft::add);
                }
            }

            try {
                msg = postman.receive(socket);
            } catch (SocketTimeoutException ignored) {
            }

            if (msg != null) {
                handler.handle(msg);
            }
            LOGGER.info("MESSAGE TREATED");

            if (!downloaded) {
                contactUploaders();
            }
            long currentMilis = System.currentTimeMillis();
            if (currentMilis - lastShuffle < SHUFFLE_INTERVAL) {
                lastShuffle = currentMilis;
                downloaders = shuffle(waitingDownloaders, downloaders, DOWNLOADER_COUNT);
            }
        }
    }

    private void contactUploaders() {
        long current = System.currentTimeMillis();
        if (current - lastUploaderShuffle > SHUFFLE_INTERVAL) {
            lastUploaderShuffle = current;
            uploaders = shuffle(availableUploaders, uploaders, UPLOADER_COUNT);
        }
        for (PeerAddress uploader : uploaders) {
            String uploaderId = uploader.getId();
            CommunicationTable table = tableMap.get(uploaderId);
            if (table != null){

                LOGGER.info(  myId + " -> " + uploaderId + "\n" + table.toString());
                if (!table.isHandshakeSent()){
                    postman.send(socket, uploader.getAddress(), uploader.getPort(), handshake);
                    table.handshakeSent();
                } else if(table.isHandshakeReceived()){
                    PeerState state = table.getPeerState();
                    PeerState myState = table.getMyState();
                    if (state.isChoked()){
                        postman.send(socket, uploader.getAddress(), uploader.getPort(), unchoke);
                        state.setChoked(false);
                    } else if (!myState.isChoked()){
                        if (!myState.isInterested()){
                            LOGGER.info("INTERESTED ON MY OWN");
                            if (!diff(table).isEmpty() || random.nextInt(10) == 1) {
                                postman.send(socket, uploader.getAddress(), uploader.getPort(), interested);
                                table.getMyState().setInterested(true);
                            }
                        } else {
                            if (!awaitingRequests.containsKey(uploader.getId())){
                                createRequestQueue(uploader);
                            } else {
                                sendRequest(uploader);
                            }
                        }
                    }
                } else { // Try to resend the handshake
                    postman.send(socket, uploader.getAddress(), uploader.getPort(), handshake);
                    table.handshakeSent();
                }
            }
        }
    }

    private boolean sendRequest(PeerAddress uploader) {
        if (awaitingRequests.containsKey(uploader.getId())) {
            Deque<Request> requests = awaitingRequests.get(uploader.getId());
            Request request = requests.peekFirst();
            if (request != null ) {
                if (lastRequest != null && lastRequest.equals(request)){
                    return false;
                }
                LOGGER.info("Send request (first): \n index: " + request.getIndex() + " offset: " + request.getOffset());
                lastRequest =  request;
                postman.send(socket, uploader.getAddress(), uploader.getPort(), request);
                return true;
            }
        }
        return false;
    }

    /**
     * Try to create a request queue
     * @param uploader uploader
     */
    private void createRequestQueue(PeerAddress uploader) {
        int index = rarestChunkIndex(tableMap.get(uploader.getId()));
        Deque<Request> requests = new LinkedList<>();
        if (index >= 0) {
            int start = index * VideoUtil.CHUNK_SIZE;
            int end = Math.toIntExact(Math.min((index + 1) * VideoUtil.CHUNK_SIZE, video.getLength()));
            for (int offset = start; offset < end; offset += Request.BLOCK_SIZE) {
                Request request;
                if (offset + Request.BLOCK_SIZE < end) {
                    request = new Request(myId, index, offset);
                } else {
                    request = new Request(myId, index, offset, end - offset);
                }
                requests.addLast(request);
            }
            if (!requests.isEmpty()){
                awaitingRequests.put(uploader.getId(), requests);
                chunkLeft.remove(index);
            }
        } else {
            postman.send(socket, uploader.getAddress(), uploader.getPort(), notInterested);
            tableMap.get(uploader.getId()).getMyState().setInterested(false);
        }
    }

    private int rarestChunkIndex(CommunicationTable table) {
        BitSet diff = diff(table);
        if (!diff.isEmpty()) {
            int minIndex = -1;
            int minValue = Integer.MAX_VALUE;
            List<Integer> candidates = new ArrayList<>();

            IntStream stream = diff.stream();
            PrimitiveIterator.OfInt it = stream.iterator();
            Collection<CommunicationTable> tables = tableMap.values();
            while (it.hasNext()) {
                int index = it.next();
                if (chunkLeft.contains(index)) { // the chunk is not being downloaded
                    int count = 0;
                    for (CommunicationTable t : tables) {
                        BitSet set = t.getField();
                        count += set.get(index) ? 1 : 0;
                    }
                    if (count < minValue) {
                        minIndex = index;
                        minValue = count;
                        candidates.clear();
                        candidates.add(index);
                    } else if (count == minValue) {
                        if (random.nextBoolean()) {
                            candidates.add(index);
                        }
                    }
                }
            }
            if (candidates.size() <= 1) { // empty or only one
                return minIndex;
            } else {
                return candidates.get(random.nextInt(candidates.size()));
            }
        }
        return -1;
    }

    private BitSet diff(CommunicationTable table) throws IllegalArgumentException {
        BitSet set = table.getField();
        int size = table.getSize();
        BitSet res = new BitSet(size);
        for (int i = 0; i < size; i++) {
            if (!field.get(i) && set.get(i)){
                res.flip(i);
            }
        }
        return res;
    }

    /**
     * Shuffle unchoked uploaders/downloaders
     * TODO select using upload/download rate
     */
    private List<PeerAddress> shuffle(List<PeerAddress> fromList, List<PeerAddress> toList, int maxSize) {
        if (!fromList.isEmpty()){
            List<PeerAddress> deck = new ArrayList<>(fromList);
            List<PeerAddress> nextPeers = new ArrayList<>(maxSize);
            while (!deck.isEmpty() && nextPeers.size() < maxSize){
                int index = random.nextInt(deck.size());
                PeerAddress peer = deck.remove(index);
                if(unchoke(peer)){
                    LOGGER.info("ADD: " + peer.getId());
                    nextPeers.add(peer);
                }
            }
            // CHOKE PEERS TO BE CHOKE
            toList.removeAll(nextPeers);
            for (PeerAddress peer : toList) {
                LOGGER.info("REMOVE: " + peer.getId());
                postman.send(socket, peer.getAddress(), peer.getPort(), choke);
                tableMap.get(peer.getId()).getPeerState().setChoked(true);
            }
            toList.clear();

            // New selected peers
            toList.addAll(nextPeers);
        }
        return toList;
    }

    private boolean unchoke(PeerAddress peer)  {
        String peerID = peer.getId();
        if (tableMap.containsKey(peerID)) {
            CommunicationTable table = tableMap.get(peerID);
            // Contacted in the past and choked
            PeerState myState = table.getMyState();
            PeerState peerState = table.getPeerState();
            if (table.isHandshakeSent() && table.isHandshakeReceived()) {
                if (!myState.isChoked()) {
                    if (peerState.isChoked()) {
                        postman.send(socket, peer.getAddress(), peer.getPort(), unchoke);
                        peerState.setChoked(false);
                    }
                    return true;
                }
            } else {
                return true;
            }
        } else {
            // Not yet contacted
            tableMap.put(peerID, new CommunicationTable(field.size()));
            return true;
        }
        return false;
    }

    private PeerAddress getPeer(String id, List<PeerAddress> peers) {
        for (PeerAddress peerAddress : peers) {
            // If this is the active uploader, I don't want to wait
            if (peerAddress.match(id)) {
                return peerAddress;
            }
        }
        return null;
    }

    public String getPeerId() {
        return myId;
    }

    public void closeCleanly(){
        trackerThread.interrupt();
        Thread.currentThread().interrupt();
    }
}
