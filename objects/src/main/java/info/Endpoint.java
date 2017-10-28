package info;

import com.google.gson.annotations.SerializedName;

public class Endpoint implements RPCConvertible<route.Endpoint> {
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

    public Endpoint(route.Endpoint endpoint) {
        this.ip = endpoint.getIp();
        this.transport = endpoint.getTransport();
        this.port = endpoint.getPort();
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

    public route.Endpoint convert() {
        return route.Endpoint.newBuilder()
                .setTransport(transport)
                .setIp(ip)
                .setPort(port)
                .build();
    }
}
