package pseudo_torrent.message;

/**
 * Created by satyan on 11/12/17.
 */
public abstract class MessageHandler {
    public abstract void handleBitField(BitField msg);
    public abstract void handleUnchoke(Unchoke msg);
    public abstract void handleNotInterested(NotInterested msg);
    public abstract void handleHandshake(Handshake msg);
    public abstract void handleHave(Have msg);
    public abstract void handleChoke(Choke msg);
    public abstract void handleKeepAlive(KeepAlive msg);
    public abstract void handleBlock(Block msg);
    public abstract void handleRequest(Request msg);
    public abstract void handleInterested(Interested msg);

    public void handle(Message msg){
        switch (msg.getType()){
            case CHOKE:
                handleChoke((Choke) msg);
                break;
            case UNCHOKE:
                handleUnchoke((Unchoke) msg);
                break;
            case INTERESTED:
                handleInterested((Interested) msg);
                break;
            case NOT_INTERESTED:
                handleNotInterested((NotInterested) msg);
                break;
            case HAVE:
                handleHave((Have) msg);
                break;
            case BIT_FIELD:
                handleBitField((BitField) msg);
                break;
            case REQUEST:
                handleRequest((Request) msg);
                break;
            case BLOCK:
                handleBlock((Block) msg);
                break;
            case HANDSHAKE:
                handleHandshake((Handshake) msg);
                break;
            case KEEP_ALIVE:
                handleKeepAlive((KeepAlive) msg);
                break;
        }
    }
}
