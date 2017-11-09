package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 * Request a piece
 */
public class Request extends Message {
    private int index;

    public Request(int index) {
        super(REQUEST_ID);
        this.index = index;
    }

    @Override
    public String serialize() {
        return gson.toJson(this);
    }

    public int getIndex() {
        return index;
    }
}
