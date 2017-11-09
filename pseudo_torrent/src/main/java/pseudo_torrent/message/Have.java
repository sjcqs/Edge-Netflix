package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class Have extends Message {
    private int index;

    public Have(int index) {
        super(HAVE_ID);
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
