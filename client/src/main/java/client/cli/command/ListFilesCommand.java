package client.cli.command;

import client.Client;
import client.DownloadManager;
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
    public void run(Client client) {
        DownloadManager downloadManager = client.getDownloadManager();
        List<Video> videos = VideoUtil.listVideos();
        printVideoList("DOWNLOADED",videos, true);
        System.out.println(HelpCommand.ANSI_BOLD_TEXT + "DOWNLOADING" + HelpCommand.ANSI_PLAIN_TEXT);
        videos = downloadManager.getDownloadingVideos();
        if (videos.isEmpty()){
            System.out.println("\tNo videos being downloaded.");
        } else {
            for (Video video : videos) {
                long downloaded = VideoUtil.downloaded(video) ;
                System.out.println("\t" + HelpCommand.ANSI_BOLD_TEXT + video.getName() + HelpCommand.ANSI_PLAIN_TEXT);
                float percent = 100f * (float) downloaded / (float) video.getLength();
                System.out.format("\t\tprogress: %02.02f%%%n", percent);
                System.out.format("\t\tlength: %.3fMB%n",video.getLength() / Math.pow(2,20));
            }
        }
    }

    static void printVideoList(String title, List<Video> videos, boolean metadata) {
        System.out.println(HelpCommand.ANSI_BOLD_TEXT + title + HelpCommand.ANSI_PLAIN_TEXT);
        if (videos.isEmpty()){
            System.out.println("\tempty");
        }
        for (Video video : videos){
            System.out.println("\t" + HelpCommand.ANSI_BOLD_TEXT + video.getName() + HelpCommand.ANSI_PLAIN_TEXT);
            if (metadata) {
                System.out.println("\t\tduration: " + video.getFormattedDuration());
                System.out.println("\t\tsize: " + video.getSize());
                System.out.println("\t\tpath: " + video.getDirectory());
                System.out.format("\t\tlength: %.2fMB%n", video.getLength() / 1_000_000d);
            }
        }
    }
}
