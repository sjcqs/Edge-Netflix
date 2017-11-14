package client.cli.command;

import client.Client;
import client.DownloadManager;
import client.RequestManager;
import client.cli.Command;
import model.Seeder;
import model.Video;
import model.util.VideoUtil;
import org.eclipse.jetty.client.api.ContentResponse;
import pseudo_torrent.Peer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * Download a file
 */
public class DownloadFileCommand extends Command {
    private final static String ADDRESS = "/video/download";
    private final static String DOWNLOAD_DIR = "./videos/";

    public DownloadFileCommand(List<String> args){
        super(args);
        if (args == null || args.isEmpty()){
            throw new IllegalArgumentException(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tName of the video to download must be provided\n" +
                            HelpCommand.ANSI_BOLD_TEXT + "USAGE" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tdownload FILE"
            );
        }
    }

    @Override
    public void run(Client client) throws UnsupportedEncodingException {
        RequestManager requestManager = client.getRequestManager();
        DownloadManager downloadManager = client.getDownloadManager();

        String query = "";
        for (String argument : arguments) {
            query += argument + " ";
        }

        query = URLEncoder.encode(query, "UTF-8");
        ContentResponse response = requestManager.sendRequest(ADDRESS + "?name=" + query);
        if (response != null && response.getStatus() == 200) {
            String json = response.getContentAsString();
            Seeder seeder = Seeder.deserialize(json);

            if(downloadManager.getPeerCount() < DownloadManager.MAX_PEER){
                Video video = seeder.getVideo();
                downloadManager.submitPeer(new Peer(seeder));
                System.out.println(
                        HelpCommand.ANSI_BOLD_TEXT + "DOWNLOADING" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                                "\t " + video.getName() + "\n" +
                                "\t\t destination: " + DOWNLOAD_DIR + video.getFilename() +
                                "\n\t\t checksum: " + video.getChecksum()
                );
            } else {
                System.out.printf(
                        HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "%n" +
                                "\tOnly %d video(s) can be downloaded at the match time.%n",
                        DownloadManager.MAX_PEER
                );
            }

        } else {
            System.out.println(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tThe video cannot be downloaded."
            );
        }
    }
}
