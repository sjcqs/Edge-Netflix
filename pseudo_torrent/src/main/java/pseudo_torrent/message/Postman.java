package pseudo_torrent.message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 * Created by satyan on 11/11/17.
 */
public class Postman {
    private final static Logger LOGGER = Logger.getLogger("Postman");
    private static Postman instance = null;
    private byte[] buff = new byte[2*Request.BLOCK_SIZE];

    private Postman(){}

    public static Postman getInstance() {
        if (instance == null){
            instance = new Postman();
        }
        return instance;
    }

    public Message receive(DatagramSocket socket) throws SocketTimeoutException{
        Message message;
        DatagramPacket packet = new DatagramPacket(buff,buff.length);

        try {
            socket.receive(packet);
            LOGGER.info("Receiving message");
            ByteBuffer rcv = ByteBuffer.wrap(packet.getData());
            int length = rcv.getInt();
            LOGGER.info("Length: " + length + " remaining: " + rcv.remaining());

            ByteBuffer buffer = ByteBuffer.allocate(length);
            LOGGER.info("ALLOCATED " + length);
            buffer.put(packet.getData(),4,Math.min(length, rcv.remaining()));
            buffer.flip();

            message = Message.deserialize(buffer);
            if (message != null){
                LOGGER.info("Received: " + message );
                message.setSource(packet.getAddress(),packet.getPort());
                return message;
            }
        } catch (IOException e) {
            LOGGER.info("Error while receiving a message: " + e.getMessage());
        }
        return null;
    }

    public void send(DatagramSocket socket, InetAddress addr, int port, Message msg) throws IllegalArgumentException{
        try {
            LOGGER.info("Sending: " + msg.getType() + " to " + addr + ":" + port);
            byte[] msgBytes = msg.getBytes();

            byte[] size = ByteBuffer.allocate(4).putInt(msgBytes.length).array();

            LOGGER.info("MESSAGE SIZE: " + msgBytes.length);

            ByteBuffer buffer = ByteBuffer.wrap(buff);
            buffer.put(size);
            buffer.put(msgBytes);

            byte[] data = buffer.array();
            LOGGER.info("DATA: " + data.length);
            DatagramPacket packet = new DatagramPacket(data, 4 + msgBytes.length, addr, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                LOGGER.warning(e.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

}
