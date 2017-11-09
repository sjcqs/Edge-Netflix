package pseudo_torrent;

import com.google.gson.Gson;
import pseudo_torrent.request.PeerAddress;
import pseudo_torrent.request.TrackerRequest;
import pseudo_torrent.request.TrackerResponse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/31/17.
 * The pseudo_torrent.tracker is in charge of keeping the file state up to date (which peer has which parts)
 */
public class TrackerServer implements Runnable{
    private final static Logger LOGGER = Logger.getLogger("TrackerServer");
    private static final int PEER_SENT = 30;
    protected DatagramSocket socket = null;

    public TrackerServer() throws IOException{
        super();
        socket = new DatagramSocket(4444);

    }

    /*Gson gson = new Gson();
    TrackerRequest target = new TrackerRequest();
    String json = gson.toJson(target);
    */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/edge_netflix?",
                "seeder",
                "Netflix");
    }

    @Override
    public void run() {
        Connection connection;
        LOGGER.info("Seeder is ready.");
        while (true){
            try {
                Gson gson = new Gson();
                byte[] buf = new byte[4096];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String target = new String(packet.getData(), 0, packet.getLength());
                TrackerRequest request = gson.fromJson(target,TrackerRequest.class);


                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                LOGGER.info(address.getHostAddress());

                // READ DATABASE, UPDATE DB, etc
                connection = getConnection();
                PreparedStatement statement;

                if (request.getEvent() == TrackerRequest.Event.STOPPED){
                    statement = connection
                            .prepareStatement("DELETE FROM peer WHERE peer_id = ?");
                    statement.setString(1,request.getPeerId());
                } else {

                    statement = connection
                            .prepareStatement(
                                    "INSERT INTO peer(peer_id, address, port, completed) VALUES (?,?,?,?)" +
                                            "ON DUPLICATE KEY UPDATE address=?,port=?,completed=?"
                            );
                    statement.setString(1, request.getPeerId());
                    statement.setString(2, address.getHostAddress());
                    statement.setString(5, address.getHostAddress());
                    statement.setInt(3, packet.getPort());
                    statement.setInt(6, packet.getPort());
                    statement.setInt(4, request.getEvent() == TrackerRequest.Event.COMPLETED ? 1 : 0);
                    statement.setInt(7, request.getEvent() == TrackerRequest.Event.COMPLETED ? 1 : 0);
                }

                statement.executeUpdate();
                statement.close();
                connection.commit();

                List<PeerAddress> addresses = new ArrayList<>();

                // Get the 30 last updated peers
                statement = connection
                        .prepareStatement("SELECT * FROM peer WHERE peer_id <> ? ORDER BY updated DESC LIMIT ?");
                statement.setString(1,request.getPeerId());
                statement.setInt(2,PEER_SENT);
                ResultSet set = statement.executeQuery();

                while (set.next()){
                    String id = set.getString("peer_id");
                    String addr = set.getString("address");
                    int port1 = set.getInt("port");
                    PeerAddress peer = new PeerAddress(id,addr,port1);
                    addresses.add(peer);
                }
                statement.close();

                // Get the number of complete and incomplete peer'sdownloads
                int complete = 0;
                Statement stat = connection.createStatement();
                set = stat.executeQuery("SELECT COUNT(peer_id) AS complete FROM peer WHERE completed = 1;");
                if (set.next()) {
                    complete = set.getInt("complete");
                }

                int incomplete = 0;
                stat = connection.createStatement();
                set = stat.executeQuery("SELECT COUNT(peer_id) AS incomplete FROM peer WHERE completed = 0;");
                if (set.next()) {
                    incomplete = set.getInt("incomplete");
                }

                TrackerResponse response = new TrackerResponse(complete,incomplete, addresses);
                byte[] buf2 = new Gson().toJson(response).getBytes();
                packet = new DatagramPacket(buf2, buf2.length, address, port);
                socket.send(packet);

                LOGGER.info("packet sent " + buf2.length + " peer count: " + addresses.size());

                connection.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        (new TrackerServer()).run();
    }
}

