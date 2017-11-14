package pseudo_torrent;

/**
 * Created by satyan on 11/12/17.
 */
public class PeerState {
    private boolean choked = true;
    private boolean interested = false;

    public boolean isChoked() {
        return choked;
    }

    public void setChoked(boolean choked) {
        this.choked = choked;
    }

    public boolean isInterested() {
        return interested;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    @Override
    public String toString() {
        return "choked: " + choked + "; interested: " + interested;
    }
}
