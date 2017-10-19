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

    public static void main(String[] argv){
        RequestManager requestManager = null;
        try {
            requestManager = new RequestManager("http://35.195.238.86",8080);
            requestManager.start();
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
            System.exit(1);
        } catch (IOException e) {
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
    }
}
