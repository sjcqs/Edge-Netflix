package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 */
public class Unchoke extends Message {
    public Unchoke() {
        super(UNCHOKE_ID);
    }

    @Override
    public String serialize() {
        return gson.toJson(this);
    }
}
