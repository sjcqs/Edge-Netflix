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
    private PeerEvent event = PeerEvent.STARTED;

    public TrackerClient(Peer peer, boolean stop) {
        this.peer = peer;
        if (stop){
            event = PeerEvent.STOPPED;
        }
    }

    @Override
    public void run() {
        try {
            Gson gson = new Gson();
            Seeder seeder;
            Video video;
            String peerId;
            int port;
            // Access can be concurrent with the downloader thread
            synchronized (peer) {
                seeder = peer.getSeeder();
                video = seeder.getVideo();
                peerId = peer.getPeerID();
                port = peer.getPort();
            }
            long downloaded = VideoUtil.videoDownloaded(video);
            if (downloaded == video.getLength()){
                event = PeerEvent.COMPLETED;
            }

            DatagramSocket socket = new DatagramSocket();
            TrackerRequest req =
                    new TrackerRequest(video.getChecksum(), peerId, port,video.getLength() - downloaded, event);

            String json = gson.toJson(req);
            LOGGER.info("Sending update: " +json);
            byte[] buf = json.getBytes();
            InetAddress address;
            address = InetAddress.getByName(seeder.getAddress());
            int seederPort = seeder.getPort();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, seederPort);
            socket.send(packet);

            //receive
            buf = new byte[4096];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            json = new String(packet.getData(), 0, packet.getLength());
            LOGGER.info(json);

            TrackerResponse response = gson.fromJson(json,TrackerResponse.class);
            if (response.getFailureReason() == null) {
                synchronized (peer) {
                    List<PeerAddress> addresses = peer.getAvailablePeers();
                    addresses.clear();
                    addresses.addAll(response.getPeerAddresses());
                    peer.setInterval(response.getInterval());
                }
            } else {
                LOGGER.warning(response.getFailureReason());
                synchronized (peer){
                    peer.stop(response.getFailureReason());
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
