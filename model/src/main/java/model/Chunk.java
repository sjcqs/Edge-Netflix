package model;

import model.util.VideoUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by satyan on 11/11/17.
 * A chunk
 */
public class Chunk {
    private final Logger logger = Logger.getLogger("Chunk");
    private final int index;
    private final int length;
    private byte[] bytes;
    private final String checksum;
    private final ByteBuffer buffer;

    public Chunk(int index, String checksum, int length) {
        this.index = index;
        this.checksum = checksum;

        bytes = new byte[length];
        buffer = ByteBuffer.wrap(bytes);
        this.length = length;
    }

    public boolean check(){
        if (buffer.remaining() == 0) {
            logger.info("Not byte remaining");
            try {
                MessageDigest digest = MessageDigest.getInstance(VideoUtil.METHOD);

                digest.update(bytes);

                String res = VideoUtil.bytesToHex(digest.digest());
                logger.info(res + "\n" + checksum);
                return checksum.equals(res);
            } catch (NoSuchAlgorithmException ignored) {
            }
        }
        return false;
    }

    public void write(File file) throws IOException {
        if (!check()){
            throw new IllegalStateException("The chunk is invalid");
        }
        buffer.flip();

        FileChannel channel = new FileOutputStream(file, false).getChannel();
        channel.write(buffer);

        channel.close();
    }

    public void put(byte[] data, int offset, int length) {
        try {
            logger.info("Chunk: " + index + " | Buffer: " + buffer.remaining() + " " + buffer.position() + " " + length);
            buffer.put(data, offset, length);
        } catch (Exception e){
            logger.warning(e.getClass().getName() + e.getLocalizedMessage());
            throw e;
        }
    }

    public int getLength() {
        return length;
    }

    public int getPosition() {
        return buffer.position();
    }

    public int getIndex() {
        return index;
    }

    public int remaining() {
        return buffer.remaining();
    }

    public void clear() {
        buffer.clear();
    }
}
