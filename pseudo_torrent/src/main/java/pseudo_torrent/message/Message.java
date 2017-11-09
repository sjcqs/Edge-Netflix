package pseudo_torrent.message;

import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by satyan on 11/2/17.
 *      KEEP-ALIVE:         <len=0000>
 *      CHOKE_ID:              <len=0001><id=0>
 *      UNCHOKE_ID             <len=0001><id=1>
 *      INTERESTED_ID:         <len=0001><id=2>
 *      NOT_INTERESTED_ID:     <len=0001><id=3>
 *      HAVE_ID:               <len=0005><id=4><index>
 *      BIT_FIELD_ID:           <len=0001+X><id=5><X bits> 0: don't have 1: have
 *      REQUEST_ID:            <len=0005><id=6><index>
 *      PIECE_ID:              <len=0009+X><id=7><index><begin><block of X bytes>
 *      (CANCEL:             <len=0013><id=8><index><begin><length>)
 *          use for end game when a few block are missing send request to all pseudo_torrent.peer
 *          and then cancel when pseudo_torrent.peer respond
 */
public abstract class Message {
    private static final Logger logger = Logger.getLogger(Message.class.getName());

    protected int id;
    protected final Gson gson = new Gson();

    public static final int CHOKE_ID = 0;
    public static final int UNCHOKE_ID = 1;
    public static final int INTERESTED_ID = 2;
    public static final int NOT_INTERESTED_ID = 3;
    public static final int HAVE_ID = 4;
    public static final int BIT_FIELD_ID = 5;
    public static final int REQUEST_ID = 6;
    public static final int PIECE_ID = 7;

    public Message(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Message deserialize(String json){
        Gson gson = new Gson();
        Message message = gson.fromJson(json,Message.class);
        switch (message.getId()){
            case CHOKE_ID:
                return gson.fromJson(json,Choke.class);
            case UNCHOKE_ID:
                return gson.fromJson(json,Unchoke.class);
            case INTERESTED_ID:
                return gson.fromJson(json,Interested.class);
            case NOT_INTERESTED_ID:
                return gson.fromJson(json,NotInterested.class);
            case HAVE_ID:
                return gson.fromJson(json,Have.class);
            case BIT_FIELD_ID:
                return gson.fromJson(json,BitField.class);
            case REQUEST_ID:
                return gson.fromJson(json,Request.class);
            case PIECE_ID:
                return gson.fromJson(json,Piece.class);
            default:
                return null;
        }
    }

    public abstract String serialize();

    // Pseudo test of serialization/deserialization
    public static void main(String[] args){
        Message keepAlive = new KeepAlive();
        Message piece = new Piece(0,0,0);
        Message request = new Request(0);
        Message bitField = new BitField(new boolean[]{false,true,false,true});
        Message have = new Have(2);
        Message notInterested = new NotInterested();
        Message interested = new Interested();
        Message unchoke = new Unchoke();
        Message choke = new Choke();

        logger.log(Level.INFO,keepAlive.serialize());
        logger.log(Level.INFO,piece.serialize());
        logger.log(Level.INFO,request.serialize());
        logger.log(Level.INFO,bitField.serialize());
        logger.log(Level.INFO,have.serialize());
        logger.log(Level.INFO,notInterested.serialize());
        logger.log(Level.INFO,interested.serialize());
        logger.log(Level.INFO,unchoke.serialize());
        logger.log(Level.INFO,choke.serialize());
        String str;
        str = keepAlive.serialize();
        assert Message.deserialize(str) instanceof KeepAlive;
        str = piece.serialize();
        assert Message.deserialize(str) instanceof Piece;
        str = request.serialize();
        assert Message.deserialize(str) instanceof Request;
        str = bitField.serialize();
        assert Message.deserialize(str) instanceof BitField;
        str = have.serialize();
        assert Message.deserialize(str) instanceof Have;
        str = notInterested.serialize();
        assert Message.deserialize(str) instanceof NotInterested;
        str = interested.serialize();
        assert Message.deserialize(str) instanceof Interested;
        str = unchoke.serialize();
        assert Message.deserialize(str) instanceof Unchoke;
        str = choke.serialize();
        assert Message.deserialize(str) instanceof Choke;
    }
}
