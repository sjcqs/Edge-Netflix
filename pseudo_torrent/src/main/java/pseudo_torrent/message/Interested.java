package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class Interested extends Message {
    public Interested( String id) {
        super(Type.INTERESTED, id);
    }
}
