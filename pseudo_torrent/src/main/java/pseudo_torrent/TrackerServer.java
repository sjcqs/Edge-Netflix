package pseudo_torrent;

import com.google.gson.Gson;
import pseudo_torrent.request.TRecData;
import pseudo_torrent.request.TSendData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyan on 10/31/17.
 * The pseudo_torrent.tracker is in charge of keeping the file state up to date (which peer has which parts)
 */
public class TrackerServer implements Runnable{
    protected DatagramSocket socket = null;

    public TrackerServer() throws IOException{
        super();
        socket = new DatagramSocket(4444);

    }

    /*Gson gson = new Gson();
    TRecData target = new TRecData();
    String json = gson.toJson(target);
    */


    @Override
    public void run() {
        while (true){
            try {
                byte[] buf = new byte[4096];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String target = new String(packet.getData(), 0, packet.getLength());
                System.out.println(target);

                // READ DATABASE, UPDATE DB, etc
                Connection con = DriverManager.getConnection(
                        "jdbc:mariadb://35.187.4.11:3306",
                        "seeder",
                        "Netflix");

                Statement stmt = con.createStatement();
                




                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                List<String> list1 = new ArrayList<>();
                list1.add("peer001");


                TSendData response = new TSendData(120,200,300,list1);
                byte[] buf2 = new Gson().toJson(response).getBytes();
                packet = new DatagramPacket(buf2, buf2.length, address, port);
                socket.send(packet);
                System.out.println("packet sent");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //socket.close();
    }

    public static void main(String[] args) throws IOException {
        (new TrackerServer()).run();
    }
}

