package pseudo_torrent.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TSendData implements Serializable{
    private int interval = 120;
    private int complete;
    private int incomplete;
    private List<String> peers = new ArrayList<>();

    public TSendData(int interval, int complete, int incomplete, List<String> peers) {
        this.interval = interval;
        this.complete = complete;
        this.incomplete = incomplete;
        this.peers = peers;
    }
}
