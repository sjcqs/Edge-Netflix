package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class KeepAlive extends Message {
    public KeepAlive() {
        super(-1);
    }

    @Override
    public String serialize() {
        return gson.toJson(this);
    }
}
