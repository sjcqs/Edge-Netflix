package client;

import model.Video;
import pseudo_torrent.Peer;
import pseudo_torrent.TrackerClient;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by satyan on 11/10/17.
 */
public class DownloadManager{
    private final static Logger logger = Logger.getLogger("DownloadManager");
    private final static int MAX_THREADS = 4;
    // For now one file at a time can be downloaded
    public static final int MAX_PEER = 1;

    private ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
    private Map<Peer,Future> peers = new HashMap<>();

    public void submitPeer(Peer peer){
        Future future = threadPool.submit(peer);
        peers.put(peer,future);
    }

    public List<Video> getDownloadingVideos(){
        List<Video> videos = new LinkedList<>();
        for (Map.Entry<Peer, Future> entry : peers.entrySet()) {
            Peer peer = entry.getKey();
            Future future = entry.getValue();

            if (future.isDone()){
                peers.remove(peer);
            } else {
                videos.add(peer.getVideo());
            }
        }
        return videos;
    }

    public int getPeerCount() {
        return peers.size();
    }

    public void stop() {
        for (Peer peer : peers.keySet()) {
            peer.closeCleanly();
        }
        peers.clear();
        threadPool.shutdownNow();
    }
}
