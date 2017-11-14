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
import model.util.VideoUtil;
import route.SizeMessage;
import route.VideoMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Video implements Convertible<VideoMessage> {
    private long length;
    private String checksum = null;
    private List<String> checksums = null;
    private String name;
    private String filename;
    private String size;
    @SerializedName("bit_rate")
    private int bitRate;
    private String directory;
    private List<String> keywords = new ArrayList<>();
    private double duration = 0d;

    private Video(
            String name, String filename,
            long length, String directory,
            String size, int bitRate,
            double duration, String checksum,
            List<String> keywords, List<String> checksums){
        this.name = name;
        this.filename = filename;
        this.directory = directory;
        this.keywords = keywords;
        this.bitRate = bitRate;
        this.size = size;
        this.length = length;
        this.checksum = checksum;
        this.duration = duration;
        this.checksums = checksums;
    }

    public Video(VideoMessage videoMessage){
        this.name = videoMessage.getName();
        SizeMessage size = videoMessage.getSize();
        this.size = size.getWidth() + "x" + size.getHeight();
        this.bitRate = videoMessage.getBitrate();
        this.keywords = new LinkedList<>(videoMessage.getKeywordList());
        this.checksum = videoMessage.getChecksum();
        this.length = videoMessage.getLength();
        this.filename = videoMessage.getFilename();
        this.checksums = new LinkedList<>(videoMessage.getChecksumsList());
    }

    public Video(String name, String filename, long length, String directory, String size, int bitRate, double duration, String checksum, List<String> checksums) {
        this(name, filename, length, directory, size, bitRate, duration, checksum, null, checksums);
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

    public long getLength() {
        return length;
    }

    public String getFilename() {
        return filename;
    }

    public String getChecksum() {
        return checksum;
    }

    public int getChunkCount(){
        return checksums.size();
    }

    public VideoMessage convert(){
        VideoMessage.Builder builder = VideoMessage.newBuilder();

        builder.setName(name);
        builder.setBitrate(bitRate);
        if (keywords != null){
            builder.addAllKeyword(keywords);
        }
        if (checksums != null){
            builder.addAllChecksums(checksums);
        }

        int width, height;
        String[] sizes = size.split("x");
        width = Integer.valueOf(sizes[0]);
        height = Integer.valueOf(sizes[1]);
        builder.setSize(SizeMessage.newBuilder().setWidth(width).setHeight(height).build());
        builder.setLength(length);
        builder.setChecksum(checksum);
        builder.setFilename(filename);

        return builder.build();
    }

    public String getFormattedDuration() {
        double dur = getDuration();
        int min = (int) (dur / 60);
        int sec = (int) dur % 60;
        return min + " min " + sec + " sec";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Video && ((Video) o).getChecksum().equals(checksum);
    }

    public List<String> getChecksums() {
        return checksums;
    }

    public int getChunkSize(int index) {
        if (index != checksums.size() - 1 ){
            return VideoUtil.CHUNK_SIZE;
        } else {
            return Math.toIntExact(length - index * VideoUtil.CHUNK_SIZE);
        }
    }
}
