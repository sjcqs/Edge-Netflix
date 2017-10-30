package model;

import route.SizeMessage;
import route.VideoMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Video implements Convertible<VideoMessage> {
    private String name;
    private String size;
    private int bitrate;
    private String directory;
    private List<String> keywords = new ArrayList<>();

    public Video(String name, String directory, String size, int bitrate, List<String> keywords){
        this.name = name;
        this.directory = directory;
        this.keywords = keywords;
        this.bitrate = bitrate;
        this.size = size;
    }

    public Video(String name, String size, int bitrate, List<String> keywordsList) {
        this(name,"",size,bitrate, keywordsList);
    }

    public Video(VideoMessage videoMessage){
        this.name = videoMessage.getName();
        SizeMessage size = videoMessage.getSize();
        this.size = size.getWidth() + "x" + size.getHeight();
        this.bitrate = videoMessage.getBitrate();
        this.keywords = new LinkedList<>(videoMessage.getKeywordList());
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

    public int getBitrate() {
        return bitrate;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public VideoMessage convert(){
        VideoMessage.Builder builder = VideoMessage.newBuilder();

        builder.setName(name);
        builder.setBitrate(bitrate);
        builder.addAllKeyword(keywords);
        int width, height;
        String[] sizes = size.split("x");
        width = Integer.valueOf(sizes[0]);
        height = Integer.valueOf(sizes[1]);
        builder.setSize(SizeMessage.newBuilder().setWidth(width).setHeight(height).build());

        return builder.build();
    }
}
