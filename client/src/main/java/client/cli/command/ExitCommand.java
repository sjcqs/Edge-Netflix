package client.cli.command;

import client.cli.Command;

/**
 * Created by satyan on 10/8/17.
 */
public class ExitCommand extends Command {

    @Override
    public void run() {
        System.exit(0);
    }

}
