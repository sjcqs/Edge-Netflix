package pseudo_torrent.message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 * Created by satyan on 11/2/17.
 *      KEEP-ALIVE:         <len=0000>
 *      CHOKE:              <len=0001><type=0>
 *      UNCHOKE             <len=0001><type=1>
 *      INTERESTED:         <len=0001><type=2>
 *      NOT_INTERESTED:     <len=0001><type=3>
 *      HAVE:               <len=0005><type=4><index>
 *      BIT_FIELD:           <len=0001+X><type=5><X bits> 0: don't have 1: have
 *      REQUEST:            <len=0005><type=6><index>
 *      BLOCK:              <len=0009+X><type=7><index><begin><block of X bytes>
 *      (CANCEL:             <len=0013><type=8><index><begin><length>)
 *          use for end game when a few block are missing send request to all pseudo_torrent.peer
 *          and then cancel when pseudo_torrent.peer respond
 */
public abstract class Message {

    private static Logger logger = Logger.getLogger("Message");
    private String peerId;
    private Type type;
    private InetAddress address = null;
    private int port = -1;

    public static Message deserialize(ByteBuffer buffer) {
        int id = buffer.getInt();
        byte[] sender = new byte[20];
        buffer.get(sender);
        String peerId = new String(sender);
        logger.info(peerId + " " + id);
        switch (Type.get(id)){
            case CHOKE:
                return new Choke(peerId);
            case UNCHOKE:
                return new Unchoke(peerId);
            case INTERESTED:
                return new Interested(peerId);
            case NOT_INTERESTED:
                return new NotInterested(peerId);
            case HAVE:
                return new Have(peerId,buffer);
            case BIT_FIELD:
                return new BitField(peerId,buffer);
            case REQUEST:
                return new Request(peerId, buffer);
            case BLOCK:
                return new Block(peerId, buffer);
            case HANDSHAKE:
                return new Handshake(peerId, buffer);
        }
        return null;
    }


    public Message(Type type, String peerId){
        this.type = type;
        this.peerId = peerId;
    }
    public void setSource(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public Type getType() {
        return type;
    }

    public String getSenderId() {
        return peerId;
    }

    public byte[] getBytes(){
        byte[] sender = peerId.getBytes();
        byte[] bytes = ByteBuffer.allocate(24).putInt(type.getId()).put(sender).array();
        logger.info("Get bytes:" +  bytes.length);
        return bytes;
    }

    public enum Type {
        CHOKE(0),
        UNCHOKE(1),
        INTERESTED(2),
        NOT_INTERESTED(3),
        HAVE(4),
        BIT_FIELD(5),
        REQUEST(6),
        BLOCK(7),
        HANDSHAKE(8),
        KEEP_ALIVE(9);

        private int id;
        Type(int id){
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Type get(int id){
            switch (id){
                case 0:
                    return CHOKE;
                case 1:
                    return UNCHOKE;
                case 2:
                    return INTERESTED;
                case 3:
                    return NOT_INTERESTED;
                case 4:
                    return HAVE;
                case 5:
                    return BIT_FIELD;
                case 6:
                    return REQUEST;
                case 7:
                    return BLOCK;
                case 8:
                    return HANDSHAKE;
                default:
                    return KEEP_ALIVE;
            }
        }
    }
}
