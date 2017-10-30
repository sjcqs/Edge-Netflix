package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import model.Video;
import model.util.VideoUtil;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * List the file available for the client
 */
public class ListFilesCommand extends Command {
    @Override
    public void run(RequestManager manager) {
        List<Video> videos = VideoUtil.listVideos();
        System.out.println(HelpCommand.ANSI_BOLD_TEXT + "DOWNLOADED" + HelpCommand.ANSI_PLAIN_TEXT);
        if (videos.isEmpty()){
            System.out.println("\tempty");
        }
        for (Video video : videos){
            System.out.println("\t" + HelpCommand.ANSI_BOLD_TEXT + video.getName() + HelpCommand.ANSI_PLAIN_TEXT);
            System.out.println("\t\tduration: " + video.getFormattedDuration());
            System.out.println("\t\tsize: " + video.getSize());
            System.out.println("\t\tpath: " + video.getDirectory());
        }
        System.out.println(HelpCommand.ANSI_BOLD_TEXT + "DOWNLOADING" + HelpCommand.ANSI_PLAIN_TEXT);
        System.out.println("\tNo videos being downloaded.");
    }

}
