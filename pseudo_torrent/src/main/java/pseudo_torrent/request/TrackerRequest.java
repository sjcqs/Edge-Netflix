package pseudo_torrent.request;

public class TrackerRequest {

    private String checksum;
    private String peerId;
    private int port;
    private long leftBytes;
    private PeerEvent event;

    public TrackerRequest(String checksum, String peerId, int port, long leftBytes, PeerEvent event){
        this.checksum = checksum;
        this.peerId = peerId;
        this.port = port;
        this.leftBytes = leftBytes;
        this.event = event;
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
