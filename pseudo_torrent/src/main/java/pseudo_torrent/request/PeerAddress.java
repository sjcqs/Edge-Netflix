package pseudo_torrent.request;

/**
 * Created by satyan on 11/9/17.
 */
public class PeerAddress {
    private String name;
    private String address;
    private int port;

    public PeerAddress(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
