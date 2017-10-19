package client.cli;

import client.RequestManager;

import java.io.UnsupportedEncodingException;

/**
 * Created by satyan on 10/19/17.
 * Interface for executable commands
 */
public interface Executable {
    void run(RequestManager manager) throws Exception;
}
