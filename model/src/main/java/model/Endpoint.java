package model;

import com.google.gson.annotations.SerializedName;
import route.EndpointMessage;

public class Endpoint implements Convertible<EndpointMessage> {
    @SerializedName("transport")
    private String transport = "tcp";
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

    public Endpoint(EndpointMessage endpointMessage) {
        this.ip = endpointMessage.getIp();
        this.transport = endpointMessage.getTransport();
        this.port = endpointMessage.getPort();
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

    public EndpointMessage convert() {
        return EndpointMessage.newBuilder()
                .setTransport(transport)
                .setIp(ip)
                .setPort(port)
                .build();
    }
}
