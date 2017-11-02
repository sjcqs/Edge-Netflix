package model;

/*
    TODO
    ADD:
    * piece length: number of bytes in each piece (integer)
    * pieces: string consisting of the concatenation of all 20-byte SHA1 hash values, one per piece
    * length
    * md5sum
*/

import com.google.gson.annotations.SerializedName;
import route.SizeMessage;
import route.VideoMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Video implements Convertible<VideoMessage> {
    private String name;
    private String size;
    @SerializedName("bit_rate")
    private int bitRate;
    private String directory;
    private List<String> keywords = new ArrayList<>();
    private double duration = 0d;

    public Video(String name, String directory, String size, int bitRate, List<String> keywords){
        this.name = name;
        this.directory = directory;
        this.keywords = keywords;
        this.bitRate = bitRate;
        this.size = size;
    }

    public Video(String name, String size, int bitRate, List<String> keywordsList) {
        this(name,"",size, bitRate, keywordsList);
    }

    public Video(VideoMessage videoMessage){
        this.name = videoMessage.getName();
        SizeMessage size = videoMessage.getSize();
        this.size = size.getWidth() + "x" + size.getHeight();
        this.bitRate = videoMessage.getBitrate();
        this.keywords = new LinkedList<>(videoMessage.getKeywordList());
    }

    public Video(String name, String directory, String size, int bitRate, double duration) {
        this(name,directory,size, bitRate,null);
        this.duration = duration;
    }

    public String getDirectory() {
        return directory;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public int getBitRate() {
        return bitRate;
    }

    public double getDuration() {
        return duration;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public VideoMessage convert(){
        VideoMessage.Builder builder = VideoMessage.newBuilder();

        builder.setName(name);
        builder.setBitrate(bitRate);
        if (keywords != null){
            builder.addAllKeyword(keywords);
        }
        int width, height;
        String[] sizes = size.split("x");
        width = Integer.valueOf(sizes[0]);
        height = Integer.valueOf(sizes[1]);
        builder.setSize(SizeMessage.newBuilder().setWidth(width).setHeight(height).build());

        return builder.build();
    }

    public String getFormattedDuration() {
        double dur = getDuration();
        int min = (int) (dur / 60);
        int sec = (int) dur % 60;
        return min + " min " + sec + " sec";
    }
}
