package cli.command;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 */
public class FileInformationCommand extends Command {
    public FileInformationCommand(List<String> args){
        super(args);
    }
    @Override
    public void run() {
        System.out.println(getClass().getName());
    }

}
