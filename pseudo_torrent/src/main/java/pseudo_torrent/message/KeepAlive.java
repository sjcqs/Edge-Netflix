package pseudo_torrent.message;


/**
 * Created by satyan on 11/9/17.
 */
public class KeepAlive extends Message {
    public KeepAlive() {
        super(Type.KEEP_ALIVE, null);
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }
}
