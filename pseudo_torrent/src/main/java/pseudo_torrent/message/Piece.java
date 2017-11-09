package pseudo_torrent.message;

/**
 * Created by satyan on 11/9/17.
 * Send a piece, this should be followed by a sending of the pieces after receiving a STAY ALIVE message
 */
public class Piece extends Message {
    private int index;
    private long begin;
    private long length;

    public Piece(int index, long begin, long length) {
        super(PIECE_ID);
        this.index = index;
        this.begin = begin;
        this.length = length;
    }

    public int getIndex() {
        return index;
    }

    public long getBegin() {
        return begin;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String serialize() {
        return gson.toJson(this);
    }
}
