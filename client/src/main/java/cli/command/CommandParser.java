package cli.command;

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
        } else if (command.equals("download")) {
            return new DownloadFileCommand(params);
        } else if (command.equals("list") && args.get(1).equals("files")) {
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
            throw new IllegalArgumentException("This command doesn't exist");
        }
    }

    private static ListSeedersCommand parseSeeder(List<String> args) throws IllegalArgumentException{
        IllegalArgumentException ex = new IllegalArgumentException(
                "ERROR\n" +
                        "\tUnknown seeder command\n" +
                        "USAGE\n" +
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
