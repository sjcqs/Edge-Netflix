package portal.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by satyan on 10/12/17.
 * Request related to videos
 */
@Path("video")
public class VideoRequest {
    @GET
    @Path("download")
    @Produces(MediaType.TEXT_PLAIN)
    public String downloadFile(@QueryParam("name") String name) {
        // TODO: return the resources used to download the file (.torrent file/seeder/ whatever)
        return VideoRequest.class.getName() + " DOWNLOAD " + name;
    }

    @GET
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
    public String getVideoList(@QueryParam("keywords") String keywords) {
        if(keywords == null){
            return listVideos();
        }
        else
            return searchVideos(keywords);
    }

    private String searchVideos(String name) {
        return getClass().getName() +", keywords: "+ name;
    }

    private String listVideos() {
        return getClass().getName();
    }
}
