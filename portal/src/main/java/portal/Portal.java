package portal;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import portal.seeder.SeederFactoryClient;

/**
 *
 */
public class Portal
{
    private static Portal instance;
    private int port;
    private String address;

    private Portal(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public static void main(String[] args )
    {
        if (args.length != 1){
            System.err.println("You need to specify the address of the seeder factory");
            System.exit(-1);
        }
        instance = new Portal(args[0],8980);

        ResourceConfig config = new ResourceConfig();
        config.packages("portal/rest");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(server,"/*");
        context.addServlet(servlet,"/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }

    public static Portal getInstance() {
        return instance;
    }

    public SeederFactoryClient getFactoryClient() {
        return SeederFactoryClient.getInstance(address, port);
    }
}
