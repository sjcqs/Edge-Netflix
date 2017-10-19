package client.cli.command;

import client.RequestManager;
import client.cli.Command;

/**
 * Created by satyan on 10/8/17.
 * Exit the program
 */
public class ExitCommand extends Command {

    @Override
    public void run(RequestManager manager) throws Exception {
        manager.stop();
        System.exit(0);
    }

}
