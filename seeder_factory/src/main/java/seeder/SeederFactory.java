package seeder;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import route.Seeder;
import route.SeederFactoryGrpc.SeederFactoryImplBase;
import route.Video;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/25/17.
 * The SeederFactory
 */
public class SeederFactory {
    private static final Logger logger = Logger.getLogger(SeederFactory.class.getName());

    private final int port;
    private final Server server;


    /** Create a RouteGuide server listening on {@code port} using {@code featureFile} database. */
    public SeederFactory(int port) throws IOException {
        this(ServerBuilder.forPort(port), port);
    }

    /** Create a RouteGuide server using serverBuilder as a base and features as data. */
    public SeederFactory(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        server = serverBuilder.addService(new SeederFactoryService()).build();
    }

    /** Start serving requests. */
    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may has been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            SeederFactory.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    private static class SeederFactoryService extends SeederFactoryImplBase{
        private final List<Seeder> seeders = new LinkedList<>();

        @Override
        public void createSeeder(Video request, StreamObserver<Seeder> responseObserver) {
            Seeder.Builder builder = Seeder.newBuilder();
            builder.setName(request.getName());
            Seeder seeder = builder.build();
            seeders.add(seeder);
            responseObserver.onNext(seeder);
            responseObserver.onCompleted();
        }

        @Override
        public void listSeeders(route.ListQuery request, StreamObserver<Seeder> responseObserver) {
            String keywords = request.getKeywords();
            for(Seeder seeder : seeders) {
                if (keywords.isEmpty()) {
                    responseObserver.onNext(seeder);
                } else {
                    if (keywords.contains(seeder.getName())) {
                        responseObserver.onNext(seeder);
                    }
                }
            }
            responseObserver.onCompleted();
        }
    }
}
