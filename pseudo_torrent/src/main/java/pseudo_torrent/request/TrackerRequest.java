package pseudo_torrent.request;

public class TrackerRequest {

    private String checksum = null;
    private String peerId = null;
    private Integer port = null;
    private Long leftBytes = null;
    private PeerEvent event = null;

    public TrackerRequest(String checksum, String peerId, int port, long leftBytes, PeerEvent event){
        this.checksum = checksum;
        this.peerId = peerId;
        this.port = port;
        this.leftBytes = leftBytes;
        this.event = event;
    }

    public TrackerRequest(String peerId) {
        this.peerId = peerId;
        this.event = PeerEvent.STOPPED;
    }

    public String getPeerId() {
        return peerId;
    }

    public String getChecksum() {
        return checksum;
    }

    public int getPort() {
        return port;
    }

    public long getLeftBytes() {
        return leftBytes;
    }

    public PeerEvent getEvent() {
        return event;
    }
}
