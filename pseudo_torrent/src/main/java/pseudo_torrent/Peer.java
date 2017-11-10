package pseudo_torrent;

import model.Seeder;
import model.util.VideoUtil;
import pseudo_torrent.request.PeerAddress;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/31/17.
 * The peer client is in charge of downloaded chunks and sending updates to the server
 * PEER PROTOCOL
 *  STATES: am_choke = 1, am_interested = 0, peer_chocked = 1, peer_interested = 0
 */
public class Peer implements Runnable {
    private final static Logger LOG = Logger.getLogger(Peer.class.getName());

    private final Seeder seeder;

    private String peerID;
    private ConcurrentMap<String, CommunicationState> communicationStates;
    private Downloader downloader;
    private DatagramSocket socket;
    private Thread downloaderThread;
    private boolean running = true;
    private int port;
    private List<PeerAddress> availablePeers;
    private int interval = 10;
    private String failureReason = null;

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public static class State {
        private boolean choked = true;
        private boolean interested = false;

        public boolean isChoked() {
            return choked;
        }

        public void setChoked(boolean choked) {
            this.choked = choked;
        }

        public boolean isInterested() {
            return interested;
        }

        public void setInterested(boolean interested) {
            this.interested = interested;
        }

        @Override
        public String toString() {
            return "choked: " + choked + "; interested: " + interested;
        }
    }

    public static class CommunicationState {
        private State myState = new State();
        private State peerState = new State();

        public State getMyState() {
            return myState;
        }

        public State getPeerState() {
            return peerState;
        }
    }

    public Peer(Seeder seeder){
        Random random = new Random(System.currentTimeMillis());
        peerID = "PT" + random.nextInt(1000);
        communicationStates = new ConcurrentHashMap<>();
        downloader = new Downloader(this);
        downloaderThread = new Thread(downloader);
        availablePeers = new ArrayList<>();

        this.seeder = seeder;
    }

    public String getPeerID() {
        return peerID;
    }

    public int getPort() {
        return port;
    }

    public Seeder getSeeder(){
        return seeder;
    }

    public List<PeerAddress> getAvailablePeers() {
        return availablePeers;
    }

    @Override
    public void run() {
        Thread trackerThread = new Thread(() -> {
            while (running) {
                try {
                    TrackerClient client = new TrackerClient(Peer.this, false);
                    client.run();
                    Thread.sleep(interval*1000);
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });

        try {
            byte[] buff = new byte[4096];
            socket = new DatagramSocket(0);
            LOG.log(Level.INFO,"["+peerID+"] Ready on port: " + socket.getLocalPort());
            this.port = socket.getLocalPort();

            trackerThread.run();
            downloaderThread.run();

            DatagramPacket packet = new DatagramPacket(buff,buff.length);
            while (running) {
                socket.receive(packet);
            }
            socket.close();
            trackerThread.join();
            downloaderThread.join();
            if (failureReason != null){
                System.out.println(failureReason);
            }
        } catch (IOException | InterruptedException e) {
            LOG.warning(e.getMessage());
            socket.close();
        }
    }

    public void stop(String failureReason){
        running = false;
        this.failureReason = failureReason;
    }

    public static void main(String[] args){
        Seeder seeder = new Seeder(
                VideoUtil.getVideo("letter",true),
                "localhost",
                4444
        );
        Peer peer = new Peer(seeder);
        LOG.info("peerID: " + peer.getPeerID());
        peer.run();
    }


}
