package pseudo_torrent;

import java.util.BitSet;

/**
 * Created by satyan on 11/12/17.
 */
public class CommunicationTable {
    private PeerState myState = new PeerState();
    private PeerState peerState = new PeerState();
    private boolean handshakeReceived = false;
    private boolean handshakeSent = false;
    private BitSet field;
    private int size;

    public CommunicationTable(int size) {
        field = new BitSet(size);
        this.size = size;
    }

    public PeerState getMyState() {
        return myState;
    }

    public PeerState getPeerState() {
        return peerState;
    }

    public boolean isHandshakeReceived() {
        return handshakeReceived;
    }

    public boolean isHandshakeSent() {
        return handshakeSent;
    }

    public void handshakeReceived() {
        handshakeReceived = true;
    }

    public void handshakeSent() {
        handshakeSent = true;
    }

    public void setField(BitSet field) {
        this.field = field.get(0, size);
    }

    public void setField(int index, boolean val) {
        this.field.set(index, val);
    }

    @Override
    public String toString() {
        return String.format(
                "%32s%16s%n"
                        + "%16s%16s%16s%n"
                        + "%16s%16b%16b%n"
                        + "%16s%16s%16s%n"
                        + "%32b%16b%n"
                        + field.toString()
                ,
                "Me", "Peer",
                "Choked", myState.isChoked(), peerState.isChoked(),
                "Interested", myState.isInterested(), peerState.isInterested(),
                "Handshake", "Sent", "Received",
                handshakeSent, handshakeReceived

        );
    }

    public BitSet getField() {
        return field;
    }

    public int getSize() {
        return size;
    }
}
