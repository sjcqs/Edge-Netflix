package pseudo_torrent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/31/17.
 * The peer client is in charge of downloaded chunks and sending updates to the server
 * PEER PROTOCOL
 *  STATES: am_choke = 1, am_interested = 0, peer_chocked = 1, peer_interested = 0
 */
public class Peer implements Runnable {
    private final static Logger LOG = Logger.getLogger(Peer.class.getName());

    private String peerID;
    private ConcurrentMap<String, CommunicationState> communicationStates;
    private Downloader downloader;
    private DatagramSocket socket;
    private Thread downloaderThread;
    private boolean running = false;

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

    public Peer(){
        Random random = new Random(System.currentTimeMillis());
        peerID = "PT" + random.nextInt(1000);
        communicationStates = new ConcurrentHashMap<>();
        downloader = new Downloader(this);
        downloaderThread = new Thread(downloader);
    }

    public String getPeerID() {
        return peerID;
    }

    @Override
    public void run() {
        try {
            while (running) {
                downloaderThread.run();
                socket = new DatagramSocket(0);
                byte[] buff = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buff,buff.length);
                socket.receive(packet);

                LOG.info("Port: " + socket.getLocalPort());
            }
            socket.close();
            downloaderThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }

    public static void main(String[] args){
        Peer peer = new Peer();
        LOG.info("peerID: " + peer.getPeerID());
        peer.run();
    }


}
