package portal.seeder;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import route.ListQuery;
import route.Seeder;
import route.SeederFactoryGrpc;
import route.Video;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/25/17.
 *
 */
public class SeederFactoryClient {
    private static final Logger logger = Logger.getLogger(SeederFactoryClient.class.getName());
    private final SeederFactoryGrpc.SeederFactoryBlockingStub blockingStub;

    public static SeederFactoryClient getInstance(){
        return new SeederFactoryClient("localhost",8980);
    }

    private SeederFactoryClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
    }

    private SeederFactoryClient(ManagedChannelBuilder<?> builder) {
        ManagedChannel channel = builder.build();
        blockingStub = SeederFactoryGrpc.newBlockingStub(channel);
    }

    public Seeder createSeeder(String name){
        Video.Builder video = Video.newBuilder();
        video.setName(name);
        Seeder seeder = null;
        try {
            seeder = blockingStub.createSeeder(video.build());
        } catch (StatusRuntimeException ex){
            logger.log(Level.WARNING,ex.getMessage());
        }

        return seeder;
    }

    public List<Seeder> listSeeders(String[] keywords){
        List<Seeder> seeders = new LinkedList<>();
        ListQuery.Builder builder = ListQuery.newBuilder();

        if (keywords != null) {
            for (String keyword : keywords) {
                builder.addKeyword(keyword);
            }
        }

        try {
            Iterator<Seeder> it = blockingStub.listSeeders(builder.build());
            while (it.hasNext()){
                Seeder seeder = it.next();
                seeders.add(seeder);
            }
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return null;
        }

        return seeders;
    }
}
