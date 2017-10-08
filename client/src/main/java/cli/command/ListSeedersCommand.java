package cli.command;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 */
public class ListSeedersCommand extends Command {
    public ListSeedersCommand(){
        super();
    }
    public ListSeedersCommand(List<String> args){
        super(args);
    }
    @Override
    public void run() {
        System.out.println(getClass().getName());
    }

}
