package pseudo_torrent.request;

public class TrackerRequest {


    public enum Event {
        STARTED, STOPPED, COMPLETED
    }

    private String videoName;
    private String peerId;
    private int port;
    private int leftBytes;
    private Event event;

    public TrackerRequest(String videoName, String peerId, int port, int leftBytes, Event event){
        this.videoName = videoName;
        this.peerId = peerId;
        this.port = port;
        this.leftBytes = leftBytes;
        this.event = event;
    }

    public String getPeerId() {
        return peerId;
    }

    public String getVideoName() {
        return videoName;
    }

    public int getPort() {
        return port;
    }

    public int getLeftBytes() {
        return leftBytes;
    }

    public Event getEvent() {
        return event;
    }
}
