package pseudo_torrent;

import com.google.gson.Gson;
import pseudo_torrent.request.TRecData;
import pseudo_torrent.request.TSendData;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TrackerClient {
    public  static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();

        TRecData req = new TRecData("vid","peer0",4444,2000, TRecData.Event.STARTED);


        byte[] buf = new Gson().toJson(req).getBytes();
        InetAddress address = InetAddress.getByName("localhost");


        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4444);
        socket.send(packet);
        System.out.println("packet sent");


        //receive
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println(received);



        socket.close();
        System.out.println("socket closed");
    }
}
