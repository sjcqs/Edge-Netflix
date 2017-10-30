package model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import route.SeederMessage;

import java.util.ArrayList;
import java.util.List;

public class Seeder implements Convertible<SeederMessage> {
    @SerializedName("video")
    private Video video;
    @SerializedName("endpoint")
    private Endpoint endpoint;

    public Seeder(Video video, Endpoint endpoint) {
        this.video = video;
        this.endpoint = endpoint;
    }

    public Seeder(SeederMessage seederMessage){
        this.video = new Video(seederMessage.getVideo());
        this.endpoint = new Endpoint(seederMessage.getEndpoint());
    }

    public String getJSON(){
        Gson gson = new Gson();
        return  gson.toJson(this, this.getClass());
    }

    public static String getJSON(Seeder info){
        Gson gson = new Gson();
        return gson.toJson(info, Seeder.class);
    }

    public static void main(String[] args){
        Seeder info = new Seeder(
                new Video("film1", "300x400", 30, new ArrayList<>()),
                new Endpoint("127.0.0.1", 8000));
        System.out.println(getJSON(info));
        System.out.println(info.getJSON());
    }

    public static String getJSON(List<Seeder> infos){
        Gson gson = new Gson();
        return gson.toJson(infos, Seeder.class);
    }

    public Video getVideo() {
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

    public SeederMessage convert() {
        return SeederMessage.newBuilder()
                .setVideo(video.convert())
                .setEndpoint(endpoint.convert())
                .build();
    }

    @Override
    public String toString() {
        String str = "";
        Video video = getVideo();
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

    public static Seeder deserialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Seeder.class);
    }
}
