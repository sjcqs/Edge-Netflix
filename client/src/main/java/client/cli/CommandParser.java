package client.cli;

import client.cli.command.*;

import java.util.List;

/**
 * Created by satyan on 10/7/17.
 * Command line interpreter for the client
 */
public class CommandParser {
    private CommandParser(){}
    public static Command parse(List<String> args) throws IllegalArgumentException {
        String command = args.get(0);
        List<String> params = null;
        if (args.size() > 1) {
            params = args.subList(1, args.size());
        }
        if (command.equals("seeder")) {
            return parseSeeder(params);
        } else if (command.equals("video")){
            return parseVideo(params);
        } else if (command.equals("download")) {
            return new DownloadFileCommand(params);
        } else if (args.size() > 1 && command.equals("list") && args.get(1).equals("files")) {
            return new ListFilesCommand();
        } else if (command.equals("info")) {
            return new FileInformationCommand(params);
        } else if (command.equals("play")) {
            return new PlayVideoCommand(params);
        } else if (command.equals("subscribe")) {
            return new SubscribeCommand();
        } else if (command.equals("help")){
            return new HelpCommand();
        } else if (command.equals("exit")){
            return new ExitCommand();
        } else {
            throw new IllegalArgumentException(
                    HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                    "\tThis command doesn't exist");
        }
    }

    private static ListVideosCommand parseVideo(List<String> args) throws IllegalArgumentException{
        IllegalArgumentException ex = new IllegalArgumentException(
                HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                        "\tUnknown video command\n" +
                        HelpCommand.ANSI_BOLD_TEXT + "USAGE" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                        "\tvideo list\n" +
                        "\t\tPrint all available videos\n" +
                        "\tvideo search KEYWORDS\n" +
                        "\t\t Print all available videos matching KEYWORDS"
        );
        if (args == null || args.size() < 1){
            throw ex;
        }
        if (args.get(0).equals("list")){
            return new ListVideosCommand();
        } else if (args.get(0).equals("search")){
            return new ListVideosCommand(args.subList(1,args.size()));
        } else {
            throw ex;
        }
    }

    private static ListSeedersCommand parseSeeder(List<String> args) throws IllegalArgumentException{
        IllegalArgumentException ex = new IllegalArgumentException(
                HelpCommand.ANSI_BOLD_TEXT + "ERROR" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                        "\tUnknown seeder command\n" +
                        HelpCommand.ANSI_BOLD_TEXT + "USAGE" + HelpCommand.ANSI_PLAIN_TEXT + "\n" +
                        "\tseeder list\n" +
                        "\t\tPrint all available seeders\n" +
                        "\tseeder search KEYWORDS\n" +
                        "\t\t Print all available seeders matching KEYWORDS"
        );
        if (args == null || args.size() < 1){
            throw ex;
        }
        if (args.get(0).equals("list")){
            return new ListSeedersCommand();
        } else if (args.get(0).equals("search")){
            return new ListSeedersCommand(args.subList(1,args.size()));
        } else {
            throw ex;
        }
    }
}
