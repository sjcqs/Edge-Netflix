package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import model.Video;
import model.util.VideoUtil;

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
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tName of the video to play must be provided\n" +
                            HelpCommand.ANSI_BOLD_TEXT + "USAGE" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tplay VIDEO"
            );
        }
        String str = "";
        for (String arg : args) {
            str += arg + " ";
        }
        name = str.trim();
    }
    @Override
    public void run(RequestManager manager) {
        Video video = VideoUtil.getVideo(name, false);
        if (video != null) {
            System.out.println(HelpCommand.ANSI_BOLD_TEXT + "PLAYING" + HelpCommand.ANSI_PLAIN_TEXT);
            System.out.println("\t" + video.getName() + " - " + video.getFormattedDuration());
            playVideo(video);
        } else {
            System.out.println(HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT);
            System.out.println("\tVideo not found.");
            System.out.println("\tType 'list files' to get all available videos.");
        }
    }

    private static void playVideo(Video video){
        ProcessBuilder pb = new ProcessBuilder("ffplay", video.getDirectory());
        File log = new File("/dev/null");
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

