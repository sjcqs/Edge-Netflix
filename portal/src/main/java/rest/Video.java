package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by satyan on 10/12/17.
 */
@Path("video")
public class Video {
    @GET
    @Path("download")
    @Produces(MediaType.TEXT_PLAIN)
    public String downloadFile(@QueryParam("name") String name) {
        // TODO: return the resources used to download the file (.torrent file/seeder/ whatever)
        return Video.class.getName() + " DOWNLOAD " + name;
    }

    @GET
    @Path("play")
    @Produces(MediaType.TEXT_PLAIN)
    public String play(@QueryParam("name") String name) {
        // TODO: return the resources used to play the file (.torrent file/seeder/ whatever)
        return Video.class.getName() + " PLAY " + name;
    }
}
