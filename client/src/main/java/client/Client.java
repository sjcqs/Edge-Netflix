package client;

import client.cli.CommandParser;
import client.cli.command.HelpCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by satyan on 10/7/17.
 * client.Client
 * TODO: Add RESTManager to centralize REST info (ip, port, data, etc)
 */
public class Client {

    public static void main(String[] args){
        // Add hook to get a few signals

        if (args.length != 1){
            System.err.println("You need to specify the address of the portal");
            System.exit(-1);
        }
        //http://35.195.238.86
        String url = args[0];
        RequestManager requestManager = new RequestManager("http://" + url,8080);
        try {
            requestManager.start();
            HelpCommand.printHelp(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            boolean stop = false;

            System.out.print("> ");
            String input;
            while (!stop){
                input = reader.readLine();
                if (input != null) {
                    input = input.trim();
                    String[] arguments = input.split("\\s");
                    try {
                        CommandParser.parse(Arrays.asList(arguments))
                                .run(requestManager);
                    } catch (IllegalArgumentException e) {
                        System.out.println();
                        System.out.println(e.getLocalizedMessage());
                    }
                    System.out.print("> ");
                } else {
                    stop = true;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (requestManager != null){
                try {
                    requestManager.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        exit(requestManager);
    }

    /**
     * Exit the application and clean what's needed to be cleaned
     * @param manager
     */
    public static void exit(RequestManager manager) {
        try {
            manager.stop();
        } catch (Exception ignored) {
        }
        System.exit(0);
    }
}
