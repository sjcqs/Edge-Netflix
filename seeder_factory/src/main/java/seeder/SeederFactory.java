package seeder;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import model.Video;
import model.util.VideoUtil;
import route.EndpointMessage;
import route.KeywordsMessage;
import route.SeederFactoryGrpc.SeederFactoryImplBase;
import route.SeederMessage;
import route.VideoMessage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/25/17.
 * The SeederFactory
 */
class SeederFactory {
    private static final Logger logger = Logger.getLogger(SeederFactory.class.getName());

    private final int port;
    private final Server server;


    /** Create a RouteGuide server listening on {@code port} using {@code featureFile} database. */
    SeederFactory(int port) throws IOException {
        this(ServerBuilder.forPort(port), port);
    }

    /** Create a RouteGuide server using serverBuilder as a base and features as data. */
    private SeederFactory(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        server = serverBuilder.addService(new SeederFactoryService()).build();
    }

    /** Start serving requests. */
    void start() throws IOException {
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
    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static class SeederFactoryService extends SeederFactoryImplBase{
        private final List<SeederMessage> seederMessages = new LinkedList<>();

        @Override
        public void createSeeder(KeywordsMessage request, StreamObserver<SeederMessage> responseObserver) {
            logger.info("Create a seeder");
            // TODO create a new seederMessage and add the real port, check if a seeder doesn't already exist
            if (request.getKeywordCount() > 0) {
                List<String> keywords = request.getKeywordList();
                String keyword = "";
                for (String str : keywords) {
                    keyword += str + " ";
                }
                keyword = keyword.trim();
                Video video = VideoUtil.getVideo(keyword, true);

                if (video != null) {
                    SeederMessage.Builder builder = SeederMessage.newBuilder();
                    builder.setVideo(video.convert());

                    EndpointMessage.Builder endpointBuilder = EndpointMessage.newBuilder();
                    endpointBuilder.setIp("localhost");
                    endpointBuilder.setPort(1002);
                    endpointBuilder.setTransport("tcp");
                    builder.setEndpoint(endpointBuilder.build());

                    SeederMessage seederMessage = builder.build();
                    seederMessages.add(seederMessage);
                    responseObserver.onNext(seederMessage);
                }
            }
            responseObserver.onCompleted();
        }

        @Override
        public void listSeeders(KeywordsMessage request, StreamObserver<SeederMessage> responseObserver) {
            logger.info("List the seeders");
            List<String> keywords = request.getKeywordList();
            String query = "";
            for (String keyword : keywords) {
                query += keyword + " ";
            }
            query = query.trim();
            if (request.getKeywordCount() == 0){
                for(SeederMessage seederMessage : seederMessages) {
                    responseObserver.onNext(seederMessage);
                }
            } else {
                // Check if a keyword or the video name is matching
                for(SeederMessage seederMessage : seederMessages) {
                    VideoMessage videoMessage = seederMessage.getVideo();
                    final String name = videoMessage.getName();
                    if (name.matches(query)){
                        responseObserver.onNext(seederMessage);
                    } else {
                        boolean send = false;
                        for (String keyword : keywords) {
                            if (name.contains(keyword)) {
                                send = true;
                            } else {
                                for (String str : videoMessage.getKeywordList()) {
                                    if (str.contains(keyword)) {
                                        send = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (send) {
                            responseObserver.onNext(seederMessage);
                        }
                    }
                }
            }
            responseObserver.onCompleted();
        }

        @Override
        public void listVideos(KeywordsMessage request, StreamObserver<VideoMessage> responseObserver) {
            logger.info("Get the list of available videos");

            if (request.getKeywordCount() == 0){
                List<Video> videos = VideoUtil.listVideos(false);
                for (Video video : videos) {
                    responseObserver.onNext(video.convert());
                }
            } else {
                List<String> keywords = request.getKeywordList();
                String keyword = "";
                for (String str : keywords) {
                    keyword += str + " ";
                }
                keyword = keyword.trim();
                List<Video> videos = VideoUtil.getVideos(keyword);

                if (videos != null) {
                    for (Video video : videos) {
                        responseObserver.onNext(video.convert());
                    }
                }
            }
            responseObserver.onCompleted();
        }
    }
}
