package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class NotInterested extends Message {

    public NotInterested( String id) {
        super(Type.NOT_INTERESTED, id);
    }
}
