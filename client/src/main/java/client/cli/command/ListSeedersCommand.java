package client.cli.command;

import client.RequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Seeder;
import model.Video;
import org.eclipse.jetty.client.api.ContentResponse;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * List the seeders available (all and searched)
 */
public class ListSeedersCommand extends ListCommand {
    private static final String ADDRESS = "/seeder/list";

    public ListSeedersCommand(){
        super(ADDRESS);
    }
    public ListSeedersCommand(List<String> args){
        super(args,ADDRESS);
    }

    @Override
    public void run(RequestManager manager) throws UnsupportedEncodingException {
        ContentResponse response = manager.sendRequest(address + query);
        if (response.getStatus() == 200) {
            String json = response.getContentAsString();
            Type listType = new TypeToken<List<Seeder>>(){}.getType();
            List<Seeder> seeders = new Gson().fromJson(json,listType);
            System.out.println(HelpCommand.ANSI_BOLD_TEXT + "SEEDERS" + HelpCommand.ANSI_PLAIN_TEXT);
            if (seeders.isEmpty()){
                System.out.println("\tempty");
            }
            for (Seeder seeder : seeders){
                Video video = seeder.getVideo();
                System.out.println("\t" + video.getName());
            }
        } else {
            System.out.println(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                            "\tThe list of seeders cannot be obtained. Try later.\n"
            );
        }
    }
}
