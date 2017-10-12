package client.cli.command;


import client.cli.Command;

/**
 * Created by satyan on 10/8/17.
 */
public class HelpCommand extends Command {
    private static final String ANSI_PLAIN_TEXT = "\033[0;0m";
    private static final String ANSI_BOLD_TEXT = "\033[0;1m";
    @Override
    public void run() {
        printHelp(false);
    }

    public static void printHelp(boolean shortHelp) {
        System.out.println(ANSI_BOLD_TEXT + "Edge Netflix" + ANSI_PLAIN_TEXT);
        System.out.println("FCUP - Distributed Systems 2017/2018");
        System.out.println();
        System.out.println(
                "Edge Netflix is a mesh between Bittorrent and Netflix. \n" +
                "The idea is to decentralized the streaming service.\n" +
                "Instead of only using cloud servers, " +
                "clients communicate\nbetween them and share data blocks for a specific file."
        );
        System.out.println();
        if (shortHelp) {
            System.out.println("Type 'help' to print the help menu.");
            System.out.println("Type 'exit' to close the application.");
        } else {
           System.out.println(ANSI_BOLD_TEXT + "COMMANDS" + ANSI_PLAIN_TEXT);
           System.out.println("\tseeder list\n" +
                   "\t\tList all available seeders");
           System.out.println("\tseeder search KEYWORDS\n" +
                   "\t\tList all available seeders that match KEYWORDS");
           System.out.println("\tdownload FILE\n" +
                   "\t\tDownload FILE");
           System.out.println("\tlist files\n" +
                   "\t\tList all downloaded and downloading files");
           System.out.println("\tinfo FILE\n" +
                   "\t\tPrint all information about FILE:\n" +
                   "\t\t- if FILE is downloaded, full path and size\n" +
                   "\t\t- if FILE is downloading, file size and neighbor list");
           System.out.println("\tplay NAME\n" +
                   "\t\tPlay NAME video using ffplay");
           System.out.println("\tsubscribe\n" +
                   "\t\tSubscribe to the portal to receive notifications of new and deleted streams");
           System.out.println("\thelp\n" +
                   "\t\tPrint the application's help");
           System.out.println("\texit\n" +
                   "\t\tExit the application");
        }
    }

}
