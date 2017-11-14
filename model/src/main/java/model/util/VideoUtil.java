package model.util;

import model.FixedBitSet;
import model.Video;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/30/17.
 * Video utility to access video files, etc
 */
public final class VideoUtil {
    private final static Logger LOGGER = Logger.getLogger(VideoUtil.class.getName());
    public static final String METHOD = "SHA-256";

    private static final String VIDEO_DIR = "./videos/";
    public static final int CHUNK_SIZE = 1_048_576;

    private VideoUtil(){}

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * List downloaded video in "./videos" directory
     * @return a list of the downloaded videos
     */
    public static List<Video> listVideos(){
        File folder = new File(VIDEO_DIR);
        File[] files = folder.listFiles();
        if (files != null) {
            List<Video> videos = new ArrayList<>(files.length);
            for (File file : files) {
                if (!file.getName().matches(".*\\.part[0-9]*")) {
                    Video video = getVideoInfo(file, false);
                    if (video != null) {
                        videos.add(video);
                    }
                }
            }
            return videos;
        }
        return new ArrayList<>();
    }

    /**
     * Get a video using it's name
     * @param name video's name
     * @param computeChecksum  compute the checksum if true
     * @return the video
     */
    public static Video getVideo(String name, boolean computeChecksum){
        if (name.isEmpty()){
            return null;
        }
        name = name.replaceAll("\\s","").toLowerCase();
        List<Video> videos = listVideos();
        for (Video video : videos) {
            String title = video.getName().replaceAll("\\s","").toLowerCase();
            if (title.contains(name)){
                if (computeChecksum){
                    return getVideoInfo(new File(VIDEO_DIR + video.getFilename()),true);
                } else {
                    return video;
                }
            }
        }
        return null;
    }

    public static List<Video> getVideos(String name){
        if (name.isEmpty()){
            return null;
        }
        name = name.toLowerCase();
        List<Video> videos = listVideos();
        List<Video> result = new ArrayList<>(videos.size());
        for (Video video : videos) {
            String title = video.getName().toLowerCase();
            if (title.contains(name)){
                result.add(video);
            }
        }
        return result;
    }

    private static Video getVideoInfo(File file, boolean computeChecksum) {
        try {
            JSONObject json = new JSONObject(getJson(file));
            if (json.length() > 0) {
                JSONObject format = json.getJSONObject("format");
                JSONObject videoStream = json.getJSONArray("streams").getJSONObject(0);

                String name;
                try {
                    name = format.getJSONObject("tags").getString("title");
                    int middle = name.indexOf("-");
                    if (middle > 0){
                        name = name.substring(0,middle).trim();
                    }
                } catch (JSONException ex){
                    name = getName(file.getName());
                }
                String directory = file.getAbsolutePath().replace("./","");
                int width = videoStream.getInt("width");
                int height = videoStream.getInt("height");
                String size = width + "x" + height;
                int bitrate = Integer.parseInt(format.getString("bit_rate"));
                double duration = Double.parseDouble(format.getString("duration"));
                String checksum = "";
                long length = file.length();


                int count = Math.toIntExact( length / CHUNK_SIZE) + 1;
                List<String> checksums = new ArrayList<>(count);
                if (computeChecksum){
                    try (InputStream in = new FileInputStream(file)){
                        MessageDigest fileDigest = MessageDigest.getInstance(METHOD);
                        byte[] data = new byte[4096];
                        int read;
                        for (int i = 0; i < count; i++) {
                            int readLength = 0;
                            MessageDigest digest = MessageDigest.getInstance(METHOD);
                            while (readLength < CHUNK_SIZE && (read = in.read(data)) > 0) {
                                digest.update(data, 0, read);
                                fileDigest.update(data, 0, read);
                                readLength += read;
                            }
                            checksums.add(bytesToHex(digest.digest()));
                        }
                        checksum = bytesToHex(fileDigest.digest());
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }

                return new Video(name, file.getName(), length, directory, size, bitrate, duration, checksum,checksums);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getJson(File file) {
        final List<String> command = new ArrayList<>();
        command.add("ffprobe");
        command.add("-v");
        command.add("quiet");
        command.add("-print_format");
        command.add("json");
        command.add("-show_format");
        command.add("-show_streams");
        command.add(file.getAbsolutePath());

        String result = "";

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process;
        BufferedReader reader = null;
        try {
            process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            boolean endReach = false;
            while (!endReach) {
                String input = reader.readLine();
                if (input != null) {
                    result += input + "\n";
                } else {
                    endReach = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * Remove the extension.
     * Video format: "date of download"_"video name in camel case"."extension"
     * @param filename the filename to trim
     * @return the trimmed filename
     */
    private static String removeExtension(String filename){
        int start = filename.indexOf("_");
        int end = filename.lastIndexOf(".");
        String name = filename;
        if (end > 0){
            name = filename.substring(start + 1,end);
        }
        return name;
    }

    /**
     * Get the formatted video name from the file name
     * @param fileName the filename
     * @return the formatted filename ex: MyVideo.mp4 --> My Video
     */
    private static String getName(String fileName){
        fileName = removeExtension(fileName);
        return fileName.replaceAll("([a-z])([A-Z])","$1 $2");
    }

    public static String getPartFilename(Video video, int index){
        return video.getFilename() + String.format(".part%03d",index);
    }

    public static long downloaded(Video video){
        File folder = new File(VIDEO_DIR);
        File videoFile = new File(VIDEO_DIR + video.getFilename());
        int count = video.getChunkCount();
        long length = 0;

        if (videoFile.exists()){
            length = videoFile.length();
        } else {
            for (int i = 0; i < count; i++) {
                File file = getPartFile(video, i);
                length += file.length();
            }
        }
        return length;
    }

    public static ByteBuffer getPiece(Video video, long offset, int length, int index) {
        long relativeOffset = offset - index*CHUNK_SIZE;
        File file = getPartFile(video, index);

        if (file.exists()){
            try (FileChannel channel = new FileInputStream(file).getChannel()){
                ByteBuffer buffer = ByteBuffer.allocate(length);
                channel.position(relativeOffset);
                channel.read(buffer);
                buffer.flip();
                return buffer;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public static ByteBuffer getPiece(Video video, long offset, int length) {
        ByteBuffer buffer = null;
        // Has the file already been downloaded ?
        File videoFile = new File(VIDEO_DIR + video.getFilename());
        LOGGER.info("Filename: " + video.getFilename() + " " + videoFile.exists());
        if (videoFile.exists()) {
            try (FileChannel channel = new FileInputStream(videoFile).getChannel()) {
                LOGGER.info("Reading block");
                buffer = ByteBuffer.allocateDirect(length);
                channel.position(offset);
                channel.read(buffer);
                LOGGER.info("Read block");
                buffer.flip();
            } catch (IOException ignored) {
                return null;
            }
        }
        return buffer;
    }

    public static File getPartFile(Video video, int index) {
        File file = new File(VIDEO_DIR + getPartFilename(video,index));
        file.getParentFile().mkdirs();
        return file;
    }

    public static FixedBitSet getBitField(Video video) {
        int size = video.getChunkCount();
        FixedBitSet set = new FixedBitSet(size);
        LOGGER.info("Size: " + size);
        List<String> checksums = video.getChecksums();

        for (int i = 0; i < size; i++) {
            File file = getPartFile(video, i);
            String checksum = checksums.get(i);
            if (file.exists() ) {
                String fileChecksum = checksum(file);
                if (fileChecksum != null && fileChecksum.equals(checksum)) {
                    set.set(i,true);
                } else {
                    file.delete();
                }
            }
        }
        LOGGER.info("SIZE OF THE FIELD: " + set.size());
        return set;
    }

    private static String checksum(File file){
        try (InputStream in = new FileInputStream(file)){
            MessageDigest fileDigest = MessageDigest.getInstance(METHOD);
            byte[] data = new byte[4096];
            int read;
            while ((read = in.read(data)) > 0) {
                fileDigest.update(data, 0, read);
            }
            return bytesToHex(fileDigest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a video using parts and check each checksum.
     * @param video the video to create
     * @return a full bitset if every chunk is valid and the video file is created
     */
    public static BitSet createVideoFile(Video video) {
        int size = video.getChunkCount();
        BitSet res = new BitSet(size);
        String checksum = video.getChecksum();
        List<String> chunksChecksum = video.getChecksums();
        File videoFile = new File(VIDEO_DIR + video.getFilename());

        try(FileOutputStream outputStream = new FileOutputStream(videoFile)) {
            for (int i = 0; i < size; i++) {
                File file = getPartFile(video, i);
                String chunkChecksum = chunksChecksum.get(i);
                if (file.exists() ) {
                    String str = checksum(file);
                    if (str != null && str.equals(chunkChecksum) ){
                        try (FileInputStream inputStream = new FileInputStream(file)){
                            byte[] buf = new byte[4096];
                            int len;
                            while ((len = inputStream.read(buf)) > 0){
                                outputStream.write(buf, 0, len);
                            }
                        }
                        res.set(i);
                    } else {
                        file.delete();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String check = checksum(videoFile);
        if (check != null && checksum.equals(check)) {
            return res;
        } else {
            return new BitSet(size);
        }
    }
}
