package seeder;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import model.Seeder;
import model.Video;
import model.util.VideoUtil;
import pseudo_torrent.SeederServer;
import route.KeywordsMessage;
import route.SeederFactoryGrpc.SeederFactoryImplBase;
import route.SeederMessage;
import route.VideoMessage;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created by satyan on 10/25/17.
 * The SeederFactory
 */
class SeederFactory {
    private static final Logger logger = Logger.getLogger(SeederFactory.class.getName());

    private final int port;
    private final Server server;


    /** Create a RouteGuide server listening on {@code uploadPort} using {@code featureFile} database. */
    SeederFactory(String address, int port) throws IOException {
        this(ServerBuilder.forPort(port), address, port);
    }

    /** Create a RouteGuide server using serverBuilder as a base and features as data. */
    private SeederFactory(ServerBuilder<?> serverBuilder, String address, int port) {
        this.port = port;
        server = serverBuilder.addService(new SeederFactoryService(address)).build();
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
        private final List<Seeder> seeders = new LinkedList<>();
        private final ExecutorService threadPool = Executors.newCachedThreadPool();
        private final String address;

        public SeederFactoryService(String address) {
            this.address = address;
        }

        @Override
        public void createSeeder(KeywordsMessage request, StreamObserver<SeederMessage> responseObserver) {
            if (request.getKeywordCount() > 0) {
                List<String> keywords = request.getKeywordList();
                String keyword = "";
                for (String str : keywords) {
                    keyword += str + " ";
                }
                keyword = keyword.trim();
                Video video = VideoUtil.getVideo(keyword, true);
                if (video != null) {
                    logger.info("Video wanted: " + video.getName());
                    // Search if the seeder is already running
                    boolean found = false;
                    for (Seeder seeder : seeders) {
                        if (seeder.getVideo().equals(video)){
                            logger.info("Get a seeder on: " + seeder.getPort());
                            responseObserver.onNext(seeder.convert());
                            found = true;
                        }
                    }

                    if (!found) {
                        SeederServer seederServer = null;
                        try {
                            seederServer = new SeederServer(address, video);
                            threadPool.submit(seederServer);
                        } catch (SocketException | UnknownHostException e) {
                            logger.warning("Couldn't create the seeder.");
                        }

                        if (seederServer != null) {
                            Seeder seeder = seederServer.getSeeder();
                            SeederMessage seederMessage = seeder.convert();
                            logger.info("Create a seeder on " + seeder.getPort());
                            seeders.add(seeder);
                            responseObserver.onNext(seederMessage);
                        }
                    }
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
                for(Seeder seeder : seeders) {
                    responseObserver.onNext(seeder.convert());
                }
            } else {
                // Check if a keyword or the video name is matching
                for(Seeder seeder : seeders) {
                    SeederMessage seederMessage = seeder.convert();
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
                List<Video> videos = VideoUtil.listVideos();
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
