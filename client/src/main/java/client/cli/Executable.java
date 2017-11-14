package client.cli;

import client.Client;

/**
 * Created by satyan on 10/19/17.
 * Interface for executable commands
 */
public interface Executable {
    void run(Client client) throws Exception;
}
