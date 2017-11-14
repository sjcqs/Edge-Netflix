package client.cli.command;

import client.Client;
import client.RequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Video;
import org.eclipse.jetty.client.api.ContentResponse;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by satyan on 10/19/17.
 * List videos available on the portal
 */
public class ListVideosCommand extends ListCommand{

    private static final String ADDRESS = "/video/list";

    public ListVideosCommand(){
        super(ADDRESS);
    }
    public ListVideosCommand(List<String> args){
        super(args,ADDRESS);
    }

    @Override
    public void run(Client client) throws Exception {
        RequestManager requestManager = client.getRequestManager();
        ContentResponse response = requestManager.sendRequest(address + query);
        if (response.getStatus() == 200) {
            String json = response.getContentAsString();
            Type listType = new TypeToken<List<Video>>(){}.getType();
            List<Video> videos = new Gson().fromJson(json,listType);
            if (videos == null){
                videos = new LinkedList<>();
            }
            ListFilesCommand.printVideoList("VIDEOS",videos, false);
        } else {
            System.out.println(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tThe list of seeders cannot be obtained. Try later.\n"
            );
        }
    }
}
