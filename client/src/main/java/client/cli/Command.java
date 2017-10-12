package client.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyan on 10/7/17.
 * Command abstract class
 */
public abstract class Command implements Runnable{
    protected List<String> arguments;

    public Command(List<String> arguments){
        this.arguments = arguments;
    }

    public Command(){
        this(new ArrayList<String>());
    }
}
