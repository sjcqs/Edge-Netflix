package portal.rest;

import com.google.gson.Gson;
import model.Seeder;
import model.Video;
import portal.Portal;
import portal.seeder.SeederFactoryClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by satyan on 10/12/17.
 * Request related to videos
 */
@Path("video")
public class VideoRequest {
    private SeederFactoryClient factoryClient = Portal.getInstance().getFactoryClient();

    @GET
    @Path("download")
    @Produces(MediaType.TEXT_PLAIN)
    public Response downloadFile(@QueryParam("name") String keywords) {
        if(keywords == null){
            keywords = "";
        }

        String[] strings = null;
        if (!keywords.isEmpty()){
            strings = keywords.split("\\s");
        }
        SeederFactoryClient factoryClient = Portal.getInstance().getFactoryClient();
        Seeder seeder = factoryClient.createSeeder(strings);
        if (seeder == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Video not found").build();
        }
        return Response.ok(seeder.getJSON(),MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVideoList(@QueryParam("keywords") String keywords) {
        if(keywords == null){
            keywords = "";
        }

        String[] strings = null;
        if (!keywords.isEmpty()){
            strings = keywords.split("\\s");
        }

        List<Video> videos = factoryClient.listVideos(strings);

        return new Gson().toJson(videos);
    }
}
