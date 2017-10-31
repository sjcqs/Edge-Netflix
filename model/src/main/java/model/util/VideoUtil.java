package model.util;

import com.google.gson.Gson;
import model.Video;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by satyan on 10/30/17.
 */
public final class VideoUtil {

    private static final String VIDEO_DIR = "./videos";

    private VideoUtil(){}

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
                Video video = getVideoInfo(file);
                if (video != null) {
                    videos.add(video);
                }
            }
            return videos;
        }
        return new ArrayList<>();
    }

    /**
     * Get a video using it's name
     * @param name video's name
     * @return the video
     */
    public static Video getVideo(String name){
        if (name.isEmpty()){
            return null;
        }
        name = name.replaceAll("\\s","").toLowerCase();
        List<Video> videos = listVideos();
        for (Video video : videos) {
            String title = video.getName().replaceAll("\\s","").toLowerCase();
            if (title.contains(name)){
                return video;
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

    private static Video getVideoInfo(File file) {
        Video video = null;
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

                video = new Video(name, directory, size, bitrate, duration);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return video;
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
}
