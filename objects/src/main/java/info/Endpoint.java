package info;

import com.google.gson.annotations.SerializedName;

public class Endpoint {
    @SerializedName("transport")
    private final String transport = "tcp";
    @SerializedName("ip")
    private String ip;
    @SerializedName("port")
    private int port;

    public Endpoint(String ip, int port){
        this.ip = ip;
        this.port = port;
        if(!(port >= 0 && port <= 65535)){
            throw new IllegalArgumentException("Invalid Port number");
        }
    }

    public String getTransport() {
        return transport;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
