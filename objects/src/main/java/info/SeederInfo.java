package info;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import route.Seeder;

import java.util.ArrayList;
import java.util.List;

public class SeederInfo implements RPCConvertible<Seeder> {
    @SerializedName("video")
    private VideoInfo video;
    @SerializedName("endpoint")
    private Endpoint endpoint;

    public SeederInfo(VideoInfo video, Endpoint endpoint) {
        this.video = video;
        this.endpoint = endpoint;
    }

    public SeederInfo(Seeder seeder){
        this.video = new VideoInfo(seeder.getVideo());
        this.endpoint = new Endpoint(seeder.getEndpoint());
    }

    public String getJSON(){
        Gson gson = new Gson();
        return  gson.toJson(this, this.getClass());
    }

    public static String getJSON(SeederInfo info){
        Gson gson = new Gson();
        return gson.toJson(info, SeederInfo.class);
    }

    public static void main(String[] args){
        SeederInfo info = new SeederInfo(
                new VideoInfo("film1", "300x400", 30, new ArrayList<>()),
                new Endpoint("127.0.0.1", 8000));
        System.out.println(getJSON(info));
        System.out.println(info.getJSON());
    }

    public static String getJSON(List<SeederInfo> infos){
        Gson gson = new Gson();
        return gson.toJson(infos, SeederInfo.class);
    }

    public VideoInfo getVideo() {
        return video;
    }

    public String getIp(){
        return endpoint.getIp();
    }

    public String getTransport(){
        return endpoint.getTransport();
    }

    public int getPort(){
        return endpoint.getPort();
    }

    public Seeder convert() {
        return Seeder.newBuilder()
                .setVideo(video.convert())
                .setEndpoint(endpoint.convert())
                .build();
    }

    @Override
    public String toString() {
        String str = "";
        VideoInfo video = getVideo();
        str += video.getName();
        if (!video.getKeywords().isEmpty()){
            str += " (";
            for (String keyword : video.getKeywords()){
                str += keyword + ";";
            }
            str += ")";
        }
        return str;
    }

    public static SeederInfo deserialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, SeederInfo.class);
    }
}
