package cli.command;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 */
public class PlayVideoCommand extends Command {
    public PlayVideoCommand(List<String> args){
        super(args);
    }
    @Override
    public void run() {
        System.out.println(getClass().getName());
    }

}
