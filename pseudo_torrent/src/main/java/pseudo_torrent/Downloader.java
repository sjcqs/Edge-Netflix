package pseudo_torrent;

import java.util.logging.Logger;

/**
 * Created by satyan on 11/1/17.
 * The part that downloads the chunks
 */
public class Downloader implements Runnable{
    private final static Logger LOGGER = Logger.getLogger("Downloader");
    private final Peer peer;

    public Downloader(Peer peer) {
        this.peer = peer;
    }

    @Override
    public void run() {
        LOGGER.info("Downloader running");
    }
}
