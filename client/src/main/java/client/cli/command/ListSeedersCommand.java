package client.cli.command;

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
}
