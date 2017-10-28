package info;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SeederInfo {
    @SerializedName("video")
    private VideoInfo video;
    @SerializedName("endpoint")
    private Endpoint endpoint;

    public SeederInfo(VideoInfo video, Endpoint endpoint) {
        this.video = video;
        this.endpoint = endpoint;
    }

    public static String getJSON(SeederInfo info){
        Gson gson = new Gson();
        String json = gson.toJson(info, SeederInfo.class);
        return json;
    }

    public static void main(String[] args){
        SeederInfo info = new SeederInfo(
                new VideoInfo("film1", "300x400", 30, new ArrayList<>()),
                new Endpoint("127.0.0.1", 8000));
        System.out.println(getJSON(info));
    }

    public static String getJSON(List<SeederInfo> info){
        return null;
    }

}
