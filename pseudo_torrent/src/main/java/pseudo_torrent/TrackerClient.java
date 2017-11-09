package pseudo_torrent;

import com.google.gson.Gson;
import pseudo_torrent.request.TrackerRequest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class TrackerClient  implements Runnable{
    public  static void main(String[] args) throws IOException, ClassNotFoundException {
        new TrackerClient().run();
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();

            TrackerRequest.Event event;

            Random random = new Random(System.currentTimeMillis());
            String peerId = "PT"+ random.nextInt(1000);
            switch (random.nextInt(2)){
                case 0:
                    event = TrackerRequest.Event.STARTED;
                    break;
                case 1:
                    event = TrackerRequest.Event.COMPLETED;
                    break;
                default:
                    event = TrackerRequest.Event.STARTED;

            }
            TrackerRequest req = new TrackerRequest("vid",peerId,4444,2000, event);


            byte[] buf = new Gson().toJson(req).getBytes();
            InetAddress address;
            address = InetAddress.getByName("localhost");


            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4444);
            socket.send(packet);
            System.out.println("packet sent");


            //receive
            buf = new byte[4096];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);



            socket.close();
            System.out.println("socket closed");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
