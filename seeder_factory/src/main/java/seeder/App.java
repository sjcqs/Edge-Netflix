package seeder;

/**
 * Created by satyan on 10/25/17.
 *
 */
public class App {
    /**
     * Main method.  This comment makes the linter happy.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            System.err.println("The address need to be precised");
            System.exit(-1);
        }
        SeederFactory server = new SeederFactory(args[0], 8980);
        server.start();
        server.blockUntilShutdown();
    }
}