package info;

import java.util.ArrayList;
import java.util.List;

public class VideoInfo {
    private String name;
    private String size;
    private int bitrate;
    private List<String> keywordsList = new ArrayList<String>();

    public VideoInfo(String name, String size, int bitrate, List<String> keywordsList) {
        this.name = name;
        this.size = size;
        this.bitrate = bitrate;
        this.keywordsList = keywordsList;
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

    public List<String> getKeywordsList() {
        return keywordsList;
    }

    public void setKeywordsList(List<String> keywordsList) {
        this.keywordsList = keywordsList;
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

}
