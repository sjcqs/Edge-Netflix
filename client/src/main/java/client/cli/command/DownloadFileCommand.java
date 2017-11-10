package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import model.Seeder;
import model.util.VideoUtil;
import org.eclipse.jetty.client.api.ContentResponse;

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
    public void run(RequestManager manager) throws UnsupportedEncodingException {

        String query = "";
        for (String argument : arguments) {
            query += argument + " ";
        }
        if (VideoUtil.getVideo(query, false) != null){
            System.out.println(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tThe video is already downloaded."
            );
        } else {
            query = URLEncoder.encode(query, "UTF-8");
            ContentResponse response = manager.sendRequest(ADDRESS + "?name=" + query);
            if (response.getStatus() == 200) {
                String json = response.getContentAsString();
                Seeder seeder = Seeder.deserialize(json);

                String filename = VideoUtil.getTorrentFilename(seeder.getVideo());
                Path filePath = Paths.get(DOWNLOAD_DIR + filename);
                try {
                    Files.createDirectories(filePath.getParent());
                    filePath = Files.createFile(filePath);
                    try (BufferedWriter writer = Files.newBufferedWriter(filePath, Charset.forName("UTF-8"))) {
                        writer.write(json, 0, json.length());
                    }
                } catch (IOException e) {
                    System.out.println(
                            HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                                    "\tThe video cannot be downloaded."
                    );
                }
                // TODO create a new thread to download/seed the file
            } else {
                System.out.println(
                        HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                                "\tThe video cannot be downloaded."
                );
            }
        }
    }
}
