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
        SeederFactory server = new SeederFactory(8980);
        server.start();
        server.blockUntilShutdown();
    }
}