package pseudo_torrent.message;

import com.google.gson.Gson;

import java.nio.ByteBuffer;
import java.util.BitSet;

/**
 * Created by satyan on 11/9/17.
 */
public class BitField extends Message {
    private BitSet field;

    public BitField(String sender, BitSet field) {
        super(Type.BIT_FIELD, sender);
        this.field = field;
    }

    public BitField(String sender , ByteBuffer buffer){
        super(Type.BIT_FIELD, sender);
        field = BitSet.valueOf(buffer);
    }

    public BitSet getField() {
        return field;
    }

    @Override
    public byte[] getBytes() {
        byte[] prefix = super.getBytes();
        byte[] data = field.toByteArray();
        return ByteBuffer.allocate(prefix.length + data.length)
                .put(prefix)
                .put(data)
                .array();
    }
}
