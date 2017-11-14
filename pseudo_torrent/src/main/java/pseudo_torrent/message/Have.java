package pseudo_torrent.message;

import com.google.gson.Gson;

import java.nio.ByteBuffer;

/**
 * Created by satyan on 11/9/17.
 */
public class Have extends Message {
    private int index;

    public Have(int index,  String id) {
        super(Type.HAVE, id);
        this.index = index;
    }

    public Have(String peerId, ByteBuffer buffer) {
        super(Type.HAVE,peerId);
        index = buffer.getInt();
    }

    @Override
    public byte[] getBytes() {
        byte[] prefix = super.getBytes();
        return ByteBuffer.allocate(prefix.length + 4)
                .put(prefix)
                .putInt(index)
                .array();
    }

    public int getIndex() {
        return index;
    }
}
