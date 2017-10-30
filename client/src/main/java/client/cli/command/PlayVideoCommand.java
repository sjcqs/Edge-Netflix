package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import model.Video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * Play a video
 */
public class PlayVideoCommand extends Command {
    private final String name;
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
        String str = "";
        for (String arg : args) {
            str += arg + " ";
        }
        name = str.substring(0,str.length() - 1);
    }
    @Override
    public void run(RequestManager manager) {
        System.out.println(getClass().getName());
        System.out.println(name);
    }

    public static void main(String args[]){
        assert args.length == 1;
        List<String> kw = new ArrayList<>();
        kw.add("Comedy");
        Video video = new Video("Popeye", args[0], "321x240", 63, kw);
        playVideo(video);
    }

    private static void playVideo(Video video){
        ProcessBuilder pb = new ProcessBuilder("ffplay", video.getDirectory());
        //ProcessBuilder pb = new ProcessBuilder("pwd");
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

