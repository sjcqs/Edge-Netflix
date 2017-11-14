package client.cli.command;

import client.Client;
import client.DownloadManager;
import client.cli.Command;

/**
 * Created by satyan on 11/13/17.
 */
public class StopCommand extends Command {
    @Override
    public void run(Client client) throws Exception {
        DownloadManager manager = client.getDownloadManager();
        manager.stop();
        System.out.println(
                HelpCommand.ANSI_BOLD_TEXT + "STOPPING DOWNLOADS" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                        "\t Downloads stopped."
        );
    }
}
