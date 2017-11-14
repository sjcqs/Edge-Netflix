package model;

import com.google.gson.annotations.SerializedName;
import route.EndpointMessage;

public class Endpoint implements Convertible<EndpointMessage> {
    @SerializedName("transport")
    private String transport = "tcp";
    @SerializedName("address")
    private String address;
    @SerializedName("port")
    private int port;

    public Endpoint(String address, int port){
        this.address = address;
        this.port = port;
        if(!(port >= 0 && port <= 65535)){
            throw new IllegalArgumentException("Invalid Port number");
        }
    }

    public Endpoint(EndpointMessage endpointMessage) {
        this.address = endpointMessage.getIp();
        this.transport = endpointMessage.getTransport();
        this.port = endpointMessage.getPort();
    }

    public String getTransport() {
        return transport;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public EndpointMessage convert() {
        return EndpointMessage.newBuilder()
                .setTransport(transport)
                .setIp(address)
                .setPort(port)
                .build();
    }
}
