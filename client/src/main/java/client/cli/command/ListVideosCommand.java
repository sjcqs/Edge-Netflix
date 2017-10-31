package client.cli.command;

import client.RequestManager;

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
    public void run(RequestManager manager) throws Exception {

    }
}
