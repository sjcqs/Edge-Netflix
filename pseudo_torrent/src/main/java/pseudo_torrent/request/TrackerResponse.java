package pseudo_torrent.request;

import java.util.List;

public class TrackerResponse{
    private final static int DEFAULT_INTERVAL = 120;

    private  String failureReason = null;
    private Integer interval = null;
    private Integer complete = null;
    private Integer incomplete = null;
    private List<PeerAddress> peerAddresses = null;

    public TrackerResponse( int complete, int incomplete, List<PeerAddress> peerAddresses) {
        this.interval = DEFAULT_INTERVAL;
        this.complete = complete;
        this.incomplete = incomplete;
        this.peerAddresses = peerAddresses;
    }

    public TrackerResponse(String failureReason){
        this.failureReason = failureReason;
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

    public String getFailureReason() {
        return failureReason;
    }

    public List<PeerAddress> getPeerAddresses() {
        return peerAddresses;
    }
}
