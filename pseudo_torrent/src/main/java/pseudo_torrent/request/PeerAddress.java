package pseudo_torrent.request;

import java.net.InetAddress;

/**
 * Created by satyan on 11/9/17.
 */
public class PeerAddress {
    private String id;
    private InetAddress address;
    private int port;

    public PeerAddress(String id, InetAddress address, int port) {
        this.id = id;
        this.address = address;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public boolean match(String id){
        return id.equals(this.id);
    }
}
