package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class Choke extends Message {
    public Choke() {
        super(CHOKE_ID);
    }

    @Override
    public String serialize() {
        return gson.toJson(this);
    }
}
