package pseudo_torrent.message;

import com.google.gson.Gson;

/**
 * Created by satyan on 11/9/17.
 */
public class Unchoke extends Message {
    public Unchoke( String id) {
        super(Type.UNCHOKE, id);
    }
}
