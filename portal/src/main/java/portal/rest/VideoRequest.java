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
    public String downloadFile(@QueryParam("name") String name) {
        SeederFactoryClient factoryClient = Portal.getInstance().getFactoryClient();
        // TODO replace the return value by a SeederMessage json
        Seeder seeder = factoryClient.createSeeder(name);
        return seeder.getJSON();
    }

    @GET
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
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
