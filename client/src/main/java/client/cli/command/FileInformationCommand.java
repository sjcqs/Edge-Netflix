package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import model.Video;
import model.util.VideoUtil;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * Get the file info
 */
public class FileInformationCommand extends Command {
    private final String name;

    public FileInformationCommand(List<String> args){
        super(args);
        if (args == null || args.isEmpty()){
            throw new IllegalArgumentException(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tName of the file (or the video) must be provided\n" +
                            HelpCommand.ANSI_BOLD_TEXT + "USAGE" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tinfo FILE"
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
        Video video = VideoUtil.getVideo(name);
        if (video != null){
            System.out.println(HelpCommand.ANSI_BOLD_TEXT + "INFO" + HelpCommand.ANSI_PLAIN_TEXT);
            System.out.println("\t" + HelpCommand.ANSI_BOLD_TEXT + video.getName() + HelpCommand.ANSI_PLAIN_TEXT);
            System.out.println("\t\tduration: " + video.getFormattedDuration());
            System.out.println("\t\tsize: " + video.getSize());
            System.out.println("\t\tpath: " + video.getDirectory());
        } else {
            // TODO Check if video is being downloaded or doesn't exist
            System.out.println(HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT);
            System.out.println("\tVideo not found.");
            System.out.println("\tType 'list files' to get all available videos.");
        }
    }

}
