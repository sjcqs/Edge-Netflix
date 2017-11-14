package client;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by satyan on 10/19/17.
 * Send requests to the server
 */
public class RequestManager {
    private static final String DEFAULT_URL = "http://localhost";
    private static final int DEFAULT_PORT = 2222;
    private final HttpClient client;
    private String serverUrl = DEFAULT_URL;
    private int serverPort = DEFAULT_PORT;

    public RequestManager(String url, int port){
        SslContextFactory sslContextFactory = new SslContextFactory();
        client = new HttpClient(sslContextFactory);
        serverUrl = url;
        serverPort = port;
    }

    public ContentResponse sendRequest(String path){
        try {
            return client.GET(serverUrl + ":" + serverPort + path);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("ERROR");
        }
        return null;
    }

    public void run() throws Exception {
        if (!client.isStarted()){
            client.start();
        }
    }

    public void stop() throws Exception {
        client.stop();
    }
}
