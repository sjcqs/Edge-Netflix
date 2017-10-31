package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import com.google.gson.Gson;
import model.Seeder;
import org.eclipse.jetty.client.api.ContentResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * Download a file
 */
public class DownloadFileCommand extends Command {
    private final static String ADDRESS = "/video/download";

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
        query = URLEncoder.encode(query,"UTF-8");
        ContentResponse response = manager.sendRequest(ADDRESS + "?name=" + query);
        if (response.getStatus() == 200) {
            String json = response.getContentAsString();
            Gson gson = new Gson();
            Seeder seeder = Seeder.deserialize(json);
            // TODO do stuff with seeder info
            System.out.println(json);
        } else {
            System.out.println(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tThe video cannot be downloaded. Try later.\n"
            );
        }
    }
}
