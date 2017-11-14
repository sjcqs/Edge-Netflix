package client.cli.command;

import client.Client;
import client.cli.Command;

/**
 * Created by satyan on 10/8/17.
 * Subscribe to portal notifications
 */
public class SubscribeCommand extends Command {
    @Override
    public void run(Client client) {
        System.out.println(getClass().getName());
    }
}
