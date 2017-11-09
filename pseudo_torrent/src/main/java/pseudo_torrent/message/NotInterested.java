package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class NotInterested extends Message {

    public NotInterested() {
        super(NOT_INTERESTED_ID);
    }

    @Override
    public String serialize() {
        return gson.toJson(this);
    }
}
