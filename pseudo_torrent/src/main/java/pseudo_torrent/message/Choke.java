package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class Choke extends Message {
    public Choke( String id) {
        super(Type.CHOKE, id);
    }
}
