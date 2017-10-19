package client.cli.command;

import client.RequestManager;
import client.cli.Command;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * Get the file info
 */
public class FileInformationCommand extends Command {
    public FileInformationCommand(List<String> args){
        super(args);
    }
    @Override
    public void run(RequestManager manager) {
        System.out.println(getClass().getName());
    }

}
