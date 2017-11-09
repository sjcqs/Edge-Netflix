package pseudo_torrent.request;

import java.io.Serializable;

public class TRecData implements Serializable{
    public enum Event {
        STARTED, STOPPED, COMPLETED
    }
    private String video_name;
    private String peer_id;
    private int port;
    private int bytes_left;
    private Event event;

    public TRecData(String video_name, String peer_id, int port, int bytes_left, Event event){
        this.video_name = video_name;
        this.peer_id = peer_id;
        this.port = port;
        this.bytes_left = bytes_left;
        this.event = event;
    }

}
