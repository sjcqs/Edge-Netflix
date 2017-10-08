import cli.command.CommandParser;
import cli.command.HelpCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by satyan on 10/7/17.
 * Client
 * TODO: Add RESTManager to centralize REST infos (ip, port, data, etc)
 */
public class Client {

    public static void main(String[] argv){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            boolean stop = false;
            HelpCommand.printHelp(true);

            System.out.print("> ");
            String input;
            while (!stop){
                input = reader.readLine();
                if (input != null) {
                    String[] args = input.split("\\s");
                    try {
                        CommandParser.parse(Arrays.asList(args))
                                .run();
                    } catch (IllegalArgumentException e) {
                        System.err.println();
                        System.err.println("Command not found.");
                    }
                    System.out.print("> ");
                } else {
                    stop = true;
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.exit(1);
        } catch (IOException e) {
            System.exit(2);
        }
    }
}
