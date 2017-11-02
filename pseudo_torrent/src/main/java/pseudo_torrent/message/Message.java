package pseudo_torrent.message;

/**
 * Created by satyan on 11/2/17.
 *      KEEP-ALIVE:         <len=0000>
 *      CHOKE:              <len=0001><id=0>
 *      UNCHOKE             <len=0001><id=1>
 *      INTERESTED:         <len=0001><id=2>
 *      NOT_INTERESTED:     <len=0001><id=3>
 *      HAVE:               <len=0005><id=4><index>
 *      BIT_FIELD:           <len=0001+X><id=5><X bits> 0: don't have 1: have
 *      REQUEST:            <len=0005><id=6><index>
 *      PIECE:              <len=0009+X><id=7><index><begin><block of X bytes>
 *      (CANCEL:             <len=0013><id=8><index><begin><length>)
 *          use for end game when a few block are missing send request to all pseudo_torrent.peer
 *          and then cancel when pseudo_torrent.peer respond
 */
public abstract class Message extends Request {
    private int id;
    private int length;
    private byte[] payload;
}
