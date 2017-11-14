package pseudo_torrent.message;

import java.nio.ByteBuffer;

/**
 * Created by satyan on 11/9/17.
 * Send a block (piece of a chunk)
 */
public class Block extends Message {
    private final int index; // The chunk index
    private final long offset; // the offset of the piece
    private final int length;
    private final byte[] data;

    public Block(String id, int index, long offset, int length, ByteBuffer buffer) {
        super(Type.BLOCK, id);
        this.index = index;
        this.offset = offset;
        this.length = length;
        data = new byte[length];
        buffer.get(data, 0, length);
    }

    public Block(String peerId, ByteBuffer buffer) {
        super(Type.BLOCK, peerId);
        this.index = buffer.getInt();
        this.offset = buffer.getInt();
        this.length = buffer.getInt();
        data = new byte[length];
        buffer.get(data, 0, length);
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

    public byte[] getData() {
        return data;
    }

    @Override
    public byte[] getBytes() {
        byte[] prefix = super.getBytes();
        return ByteBuffer.allocate(prefix.length + 4*3 + data.length)
                .put(prefix)
                .putInt(index)
                .putInt((int) offset)
                .putInt(length)
                .put(data)
                .array();
    }
}
