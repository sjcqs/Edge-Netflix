package portal.seeder;

import info.SeederInfo;
import info.VideoInfo;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import javassist.compiler.ast.Keyword;
import route.*;

import java.util.ArrayList;
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

    public SeederInfo createSeeder(String name){
        // TODO check the database for videos info
        List<String> keywords = new LinkedList<>();
        keywords.add("test0");
        keywords.add("test1");
        keywords.add("test2");
        VideoInfo videoInfo = new VideoInfo(
                name,
                "128x128",
                144,
                keywords
        );
        Seeder seeder = null;
        try {
            seeder = blockingStub.createSeeder(videoInfo.convert());
        } catch (StatusRuntimeException ex){
            logger.log(Level.WARNING,ex.getMessage());
        }

        return new SeederInfo(seeder);
    }

    public List<SeederInfo> listSeeders(String[] keywords){
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

        List<SeederInfo> seederInfos = new LinkedList<>();
        for (Seeder seeder : seeders) {
            seederInfos.add(new SeederInfo(seeder));
        }

        return seederInfos;
    }
}
