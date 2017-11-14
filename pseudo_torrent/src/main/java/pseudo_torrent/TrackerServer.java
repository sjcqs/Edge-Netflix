package pseudo_torrent;

import com.google.gson.Gson;
import model.Seeder;
import pseudo_torrent.request.PeerAddress;
import pseudo_torrent.request.PeerEvent;
import pseudo_torrent.request.TrackerRequest;
import pseudo_torrent.request.TrackerResponse;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/31/17.
 * The pseudo_torrent.tracker is in charge of keeping the file state up to date (which peer has which parts)
 */
public class TrackerServer extends Thread{
    private final static Logger LOGGER = Logger.getLogger("TrackerServer");
    private static final int PEER_SENT = 9;
    private final int port;
    private DatagramSocket socket = null;
    private final SeederServer seederServer;

    public TrackerServer(SeederServer seeder) throws SocketException {
        socket = new DatagramSocket(0);
        this.port = socket.getLocalPort();
        socket.setSoTimeout(30*1000);
        this.seederServer = seeder;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/edge_netflix?",
                "seeder",
                "Netflix");
    }

    public int getPort() {
        return port;
    }

    @Override
    public void run() {
        Seeder seeder;
        synchronized (seederServer) {
            seeder = seederServer.getSeeder();
        }

        LOGGER.info("Tracker ready on port: " + port);
        Connection connection;
        while (!isInterrupted()){
            Gson gson = new Gson();
            DatagramPacket packet = null;
            try {
                byte[] buf = new byte[4096];

                // receive request
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String target = new String(packet.getData(), 0, packet.getLength());
                TrackerRequest request = gson.fromJson(target,TrackerRequest.class);

                String checksum = seeder.getVideo().getChecksum();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                // READ DATABASE, UPDATE DB, etc
                connection = getConnection();
                PreparedStatement statement;
                if (checksum.equals(request.getChecksum())) {
                    LOGGER.info(request.getEvent() + "-> Update received: " + address.getHostAddress() + ":" + port + "\n"
                    + request.getPeerId());


                    Statement st = connection.createStatement();
                    st.executeUpdate("DELETE FROM peer WHERE updated < (NOW() - INTERVAL 10 MINUTE)");
                    //st.executeUpdate("DELETE FROM peer");
                    st.close();

                    if (request.getEvent() == PeerEvent.STOPPED) {
                        statement = connection
                                .prepareStatement("DELETE FROM peer WHERE peer_id = ? AND checksum = ?");
                        statement.setString(1, request.getPeerId());
                        statement.setString(2, checksum);
                    } else {

                        statement = connection
                                .prepareStatement(
                                        "INSERT INTO peer(peer_id, address, port, completed,checksum) VALUES (?,?,?,?,?)" +
                                                "ON DUPLICATE KEY UPDATE address=?,port=?,completed=?, checksum=?"
                                );

                        statement.setString(1, request.getPeerId());
                        statement.setString(2, address.getHostAddress());
                        statement.setString(6, address.getHostAddress());
                        statement.setInt(3, request.getPort());
                        statement.setInt(7, request.getPort());
                        statement.setInt(4, request.getEvent() == PeerEvent.COMPLETED ? 1 : 0);
                        statement.setInt(8, request.getEvent() == PeerEvent.COMPLETED ? 1 : 0);
                        statement.setString(5, request.getChecksum());
                        statement.setString(9, request.getChecksum());
                    }

                    statement.executeUpdate();
                    statement.close();
                    connection.commit();

                    List<PeerAddress> addresses = new ArrayList<>();

                    // Get the PEER_SENT last updated peers
                    statement = connection
                            .prepareStatement("SELECT * FROM peer WHERE peer_id <> ? AND checksum = ? ORDER BY updated DESC LIMIT ?");
                    statement.setString(1, request.getPeerId());
                    statement.setString(2,request.getChecksum());
                    statement.setInt(3, PEER_SENT);
                    ResultSet set = statement.executeQuery();

                    while (set.next()) {
                        String id = set.getString("peer_id");
                        InetAddress addr = InetAddress.getByName(set.getString("address"));
                        int port1 = set.getInt("port");
                        PeerAddress peer = new PeerAddress(id, addr, port1);

                        addresses.add(peer);
                    }
                    addresses.add(seederServer.getAddress());
                    statement.close();

                    // Get the number of complete and incomplete peer'sdownloads
                    int complete = 0;
                    statement = connection.prepareStatement("SELECT COUNT(peer_id) AS complete FROM peer WHERE completed = 1 AND checksum = ?;");
                    statement.setString(1, checksum);
                    set = statement.executeQuery();
                    if (set.next()) {
                        complete = set.getInt("complete");
                    }
                    statement.close();

                    int incomplete = 0;
                    statement = connection.prepareStatement("SELECT COUNT(peer_id) AS incomplete FROM peer WHERE completed = 0 AND checksum = ?;");
                    statement.setString(1, checksum);
                    set = statement.executeQuery();
                    if (set.next()) {
                        incomplete = set.getInt("incomplete");
                    }
                    statement.close();

                    TrackerResponse response;
                    response = new TrackerResponse(complete, incomplete, addresses);
                    byte[] buf2 = gson.toJson(response).getBytes();
                    packet = new DatagramPacket(buf2, buf2.length, address, port);

                    socket.send(packet);


                } else {
                    if (request.getEvent() == PeerEvent.STOPPED){
                        LOGGER.info("STOPPED: "+ request.getPeerId());
                        statement = connection.prepareStatement("DELETE FROM peer WHERE peer_id = ?");
                        statement.setString(1, request.getPeerId());
                        statement.executeUpdate();
                        statement.close();
                    } else {
                        buf = gson.toJson(new TrackerResponse("Checksum doesn't match")).getBytes();
                        packet = new DatagramPacket(buf, buf.length, address, port);
                        socket.send(packet);
                    }
                }
                connection.commit();
                connection.close();
            } catch (SocketTimeoutException ignored){
            } catch (InterruptedIOException e){
                Thread.currentThread().interrupt();
            } catch (IOException | SQLException e) {
                LOGGER.warning(e.getMessage());
                TrackerResponse response = new TrackerResponse("An internal error occurred.");
                byte[] bytes = gson.toJson(response).getBytes();
                try {
                    socket.send(new DatagramPacket(bytes, bytes.length, packet.getAddress(), packet.getPort()));
                } catch (IOException e1) {
                    LOGGER.warning("Error: " + e.getMessage());
                }

            }
        }
    }
}

