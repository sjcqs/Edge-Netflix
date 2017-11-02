package pseudo_torrent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by satyan on 10/31/17.
 * The peer client is in charge of downloaded chunks and sending updates to the server
 * PEER PROTOCOL
 *  STATES: am_choke = 1, am_interested = 0, peer_chocked = 1, peer_interested = 0
 */
public class Peer implements Runnable{
    @Override
    public void run() {
        try {
            String sentence;
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket("localhost", 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(6789);
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    public static void main(String[] args){
        new Peer().run();
    }
}
