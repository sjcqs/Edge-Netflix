package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.URIUtil;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.UnsupportedEncodingException;
import java.net.URI;
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
                    "ERROR\n" +
                            "\tName of the video to download must be provided\n" +
                    "USAGE\n" +
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
        System.out.println(response.getContentAsString());
    }
}
