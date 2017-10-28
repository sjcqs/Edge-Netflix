package client.cli.command;

import client.cli.Command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.lang.Object;

/**
 * Created by satyan on 10/8/17.
 */
public class PlayVideoCommand extends Command {
    public PlayVideoCommand(List<String> args){
        super(args);
        if (args == null || args.isEmpty()){
            throw new IllegalArgumentException(
                    "ERROR\n" +
                            "\tName of the video to play must be provided\n" +
                            "USAGE\n" +
                            "\tplay VIDEO"
            );
        }
    }
    @Override
    public void run() {
        System.out.println(getClass().getName());
    }

    public static class Video{
        private final String name;
        private final String directory;
        private final List<String> keywords;
        private final int bitrate;
        private final String size;

        public String getName() {
            return name;
        }

        public String getDirectory() {
            return directory;
        }

        public List<String> getKeywords() {
            return keywords;
        }

        public int getBitrate() {
            return bitrate;
        }

        public String getSize() {
            return size;
        }

        public Video(String name, String directory, List<String> keywords, int bitrate, String size){
            this.name = name;
            this.directory = directory;
            this.keywords = keywords;
            this.bitrate = bitrate;
            this.size = size;
        }

    }

    public static void main(String args[]){
        List<String> kw = new ArrayList<>();
        kw.add("Comedy");
        Video video = new Video("Popeye", "/home/japs/Desktop/PopeyeAliBaba_512kb.mp4", kw, 63, "321x240");

        playVideo(video);
    }

    public static void playVideo(Video video){
        List<String> cmdlist = new ArrayList<String>();
        cmdlist.add("man");
        //cmdlist.add("ffplay https://ia600306.us.archive.org/35/items/PopeyeAliBaba/PopeyeAliBaba_512kb.mp4");

        ProcessBuilder pb = new ProcessBuilder("ffplay", video.getDirectory());
       //ProcessBuilder pb = new ProcessBuilder("ls");
        File log = new File("log");
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
        try {
            Process p = pb.start();
            assert pb.redirectInput() == ProcessBuilder.Redirect.PIPE;
            assert pb.redirectOutput().file() == log;
            assert p.getInputStream().read() == -1;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

