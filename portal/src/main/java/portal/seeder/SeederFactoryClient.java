package portal.seeder;

import model.Seeder;
import model.Video;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import route.*;

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
        // TODO check the database for videos info
        List<String> keywords = new LinkedList<>();
        keywords.add("test0");
        keywords.add("test1");
        keywords.add("test2");
        Video video = new Video(
                name,
                "128x128",
                144,
                keywords
        );
        SeederMessage seederMessage = null;
        try {
            seederMessage = blockingStub.createSeeder(video.convert());
        } catch (StatusRuntimeException ex){
            logger.log(Level.WARNING,ex.getMessage());
        }

        return new Seeder(seederMessage);
    }

    public List<Seeder> listSeeders(String[] keywords){
        List<SeederMessage> seederMessages = new LinkedList<>();
        KeywordsMessage.Builder builder = KeywordsMessage.newBuilder();

        if (keywords != null) {
            for (String keyword : keywords) {
                builder.addKeyword(keyword);
            }
        }

        try {
            Iterator<SeederMessage> it = blockingStub.listSeeders(builder.build());
            while (it.hasNext()){
                SeederMessage seederMessage = it.next();
                seederMessages.add(seederMessage);
            }
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return null;
        }

        List<Seeder> seederInfos = new LinkedList<>();
        for (SeederMessage seederMessage : seederMessages) {
            seederInfos.add(new Seeder(seederMessage));
        }

        return seederInfos;
    }
}
