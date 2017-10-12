package client.cli.command;

import client.cli.Command;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 */
public class DownloadFileCommand extends Command {

    public DownloadFileCommand(List<String> args){
        super(args);
        if (args == null || args.isEmpty()){
            throw new IllegalArgumentException(
                    "ERROR\n" +
                            "\tName of the video to download must be provided\n" +
                    "USAGE\n" +
                            "\tdownload FILE"
            );
        }
    }

    @Override
    public void run() {
        System.out.println(getClass().getName());
    }
}
