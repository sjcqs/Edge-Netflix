package pseudo_torrent.request;

import java.util.ArrayList;
import java.util.List;

public class TrackerResponse{
    private int interval = 120;
    private int complete;
    private int incomplete;
    private List<PeerAddress> peerAddresses = new ArrayList<>();

    public TrackerResponse( int complete, int incomplete, List<PeerAddress> peerAddresses) {
        this.complete = complete;
        this.incomplete = incomplete;
        this.peerAddresses = peerAddresses;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public int getComplete() {
        return complete;
    }

    public int getIncomplete() {
        return incomplete;
    }

    public List<PeerAddress> getPeerAddresses() {
        return peerAddresses;
    }
}
