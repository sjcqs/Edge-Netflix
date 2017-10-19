package client.cli.command;

import client.RequestManager;
import client.cli.Command;
import org.eclipse.jetty.client.api.ContentResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by satyan on 10/19/17.
 * Command that consist of listing object or listing using keywords
 */
public class ListCommand extends Command {
    private String address;

    public ListCommand(String address){
        super();
        this.address = address;
    }
    public ListCommand(List<String> args, String address){
        super(args);
        this.address = address;
    }

    @Override
    public void run(RequestManager manager) throws UnsupportedEncodingException {
        String query = "";
        for (String argument : arguments) {
            query += argument + " ";
        }
        if (!query.isEmpty()) {
            query = URLEncoder.encode(query, "UTF-8");
            query = "?keywords=" + query;
        }
        ContentResponse response = manager.sendRequest(address + query);
        System.out.println(response.getContentAsString());
    }
}
