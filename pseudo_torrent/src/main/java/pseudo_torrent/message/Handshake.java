package pseudo_torrent.message;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by satyan on 11/2/17.
 *
 */
public class Handshake extends Message{

    private final byte[] checksum = new byte[64];

    public Handshake(String id, String checksum) {
        super(Type.HANDSHAKE, id);
        ByteBuffer buffer = ByteBuffer.wrap(this.checksum);
        buffer.put(checksum.getBytes());
    }

    public Handshake(String peerId, ByteBuffer buffer) {
        super(Type.HANDSHAKE,peerId);
        buffer.get(checksum);
    }

    public byte[] getBytes() {
        byte[] prefix = super.getBytes();
        return ByteBuffer.allocate(prefix.length + 64)
                .put(prefix)
                .put(checksum)
                .array();
    }

    public boolean check(String checksum) {
       return Arrays.equals(checksum.getBytes(), this.checksum);
    }
}
