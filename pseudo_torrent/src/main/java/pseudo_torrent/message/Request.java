package pseudo_torrent.message;

import java.nio.ByteBuffer;

/**
 * Created by satyan on 11/9/17.
 * Request a piece of chunk
 */
public class Request extends Message {
    public static final int BLOCK_SIZE = 16_384;
    private int index;  // The chunk index
    private long offset; // Offset in bytes
    private int length = BLOCK_SIZE; // Length requested

    public Request(String id, int index, long offset, int length) {
        super(Type.REQUEST, id);
        this.index = index;
        this.offset = offset;
        this.length = length;
    }

    public Request(String peerId, int index, long offset) {
        this(peerId,index,offset, BLOCK_SIZE);
    }

    public Request(String peerId, ByteBuffer buffer) {
        super(Type.REQUEST, peerId);
        index = buffer.getInt();
        offset = buffer.getInt();
        length = buffer.getInt();
    }

    public int getIndex() {
        return index;
    }

    public long getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    @Override
    public byte[] getBytes() {
        byte[] prefix = super.getBytes();
        return ByteBuffer.allocate(prefix.length + 4*3)
                .put(prefix)
                .putInt(index)
                .putInt((int) offset)
                .putInt(length)
                .array();
    }
}
