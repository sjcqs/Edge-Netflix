package pseudo_torrent;

import com.google.gson.Gson;
import model.Seeder;
import model.Video;
import model.util.VideoUtil;
import pseudo_torrent.request.PeerAddress;
import pseudo_torrent.request.PeerEvent;
import pseudo_torrent.request.TrackerRequest;
import pseudo_torrent.request.TrackerResponse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.logging.Logger;

public class TrackerClient  implements Runnable{
    private static final Logger LOGGER = Logger.getLogger("TrackerClient");
    private final Peer peer;
    private String peerId;
    private PeerEvent event = PeerEvent.STARTED;
    private final Seeder seeder;

    public TrackerClient(final Peer peer, boolean stop) {
        this.peer = peer;
        this.seeder = peer.getSeeder();
        peerId = peer.getPeerId();
        if (stop){
            event = PeerEvent.STOPPED;
        }
    }

    @Override
    public void run() {
         DatagramSocket socket = null;
        try {
            LOGGER.info("Sending: " + seeder.getAddress() + ":" + seeder.getPort());
            socket = new DatagramSocket();
            Gson gson = new Gson();
            byte[] buf;
            if (event.equals(PeerEvent.STOPPED)){
                LOGGER.info("STOPPED");
                TrackerRequest req = new TrackerRequest(peerId);
                buf = gson.toJson(req).getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, seeder.getInetAddres(), seeder.getPort());
                socket.send(packet);

            } else {
                Video video;
                int port;
                // Access can be concurrent with the downloader thread
                synchronized (peer) {

                    video = seeder.getVideo();
                    port = peer.getUploadPort();
                }
                long downloaded = VideoUtil.downloaded(video);
                if (downloaded == video.getLength()) {
                    event = PeerEvent.COMPLETED;
                }

                TrackerRequest req;

                req = new TrackerRequest(video.getChecksum(), peerId, port, video.getLength() - downloaded, event);

                String json = gson.toJson(req);
                buf = json.getBytes();
                InetAddress address;
                address = InetAddress.getByName(seeder.getAddress());
                int seederPort = seeder.getPort();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, seederPort);
                socket.send(packet);

                buf = new byte[4096];
                packet = new DatagramPacket(buf, buf.length);
                socket.setSoTimeout(60 * 1000); // 60 sec of timeout

                // If we didn't send a stop command
                if (!event.equals(PeerEvent.STOPPED)) {
                    socket.receive(packet);
                    json = new String(packet.getData(), 0, packet.getLength());

                    TrackerResponse response = gson.fromJson(json, TrackerResponse.class);
                    if (response.getFailureReason() == null) {

                        synchronized (peer) {
                            peer.setAvailableUploaders(response.getPeerAddresses());
                            peer.setInterval(response.getInterval());
                        }

                    } else {
                        peer.interrupt();
                    }
                }
            }
        } catch (IOException e) {
            // we don't care a new message will be sent
            LOGGER.info(e.getMessage());
        } finally {

            if (socket != null){
                socket.close();
            }
        }
    }
}
