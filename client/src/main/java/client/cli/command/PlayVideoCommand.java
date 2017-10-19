package client.cli.command;

import client.RequestManager;
import client.cli.Command;

import java.util.List;

/**
 * Created by satyan on 10/8/17.
 * Play a video
 */
public class PlayVideoCommand extends Command {
    public PlayVideoCommand(List<String> args){
        super(args);
        if (args == null || args.isEmpty()){
            throw new IllegalArgumentException(
                    "ERROR\n" +
                            "\tName of the video to play must be provided\n" +
                            "USAGE\n" +
                            "\tplay VIDEO"
            );
        }
    }
    @Override
    public void run(RequestManager manager) {
        System.out.println(getClass().getName());
    }

}
