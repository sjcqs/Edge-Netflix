package client.cli.command;

import client.cli.Command;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by satyan on 10/19/17.
 * Command that consist of listing object or listing using keywords
 */
public abstract class ListCommand extends Command {
    protected String query;
    protected String address;

    ListCommand(String address){
        this(null,address);
    }
    ListCommand(List<String> args, String address){
        super(args);
        this.address = address;
        query = "";
        for (String argument : arguments) {
            query += argument + " ";
        }
        if (!query.isEmpty()) {
            try {
                query = URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException ignored) {
            }
            query = "?keywords=" + query;
        }
    }
}
