package info;

import route.Size;
import route.Video;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class VideoInfo implements RPCConvertible<Video> {
    private String name;
    private String size;
    private int bitrate;
    private List<String> keywords = new ArrayList<String>();

    public VideoInfo(String name, String size, int bitrate, List<String> keywordsList) {
        this.name = name;
        this.size = size;
        this.bitrate = bitrate;
        this.keywords = keywordsList;
    }

    public VideoInfo(Video video){
        this.name = video.getName();
        Size size = video.getSize();
        this.size = size.getWidth() + "x" + size.getHeight();
        this.bitrate = video.getBitrate();
        this.keywords = new LinkedList<String>(video.getKeywordList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }


    private void checkSize(String videoSize){
        if(!videoSize.matches("[0-9]+x[0-9]+")){
            throw new IllegalArgumentException("Invalid video size. Format must be \"widthxheight\"");
        }
    }

    private void checkBitrate(int bitrate){
        if(!(bitrate>0)){
            throw new IllegalArgumentException("Invalid bitrate");
        }
    }

    public Video convert(){
        Video.Builder builder = Video.newBuilder();

        builder.setName(name);
        builder.setBitrate(bitrate);
        builder.addAllKeyword(keywords);
        int width, height;
        String[] sizes = size.split("x");
        width = Integer.valueOf(sizes[0]);
        height = Integer.valueOf(sizes[1]);
        builder.setSize(Size.newBuilder().setWidth(width).setHeight(height).build());

        return builder.build();
    }

}
