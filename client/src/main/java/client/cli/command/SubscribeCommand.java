package client.cli.command;

import client.RequestManager;
import client.cli.Command;

/**
 * Created by satyan on 10/8/17.
 * Subscribe to portal notifications
 */
public class SubscribeCommand extends Command {
    @Override
    public void run(RequestManager manager) {
        System.out.println(getClass().getName());
    }
}
