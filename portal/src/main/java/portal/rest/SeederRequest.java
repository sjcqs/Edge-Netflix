package portal.rest;

import com.google.gson.Gson;
import model.Seeder;
import portal.Portal;
import portal.seeder.SeederFactoryClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by satyan on 10/7/17.
 * All seeder related requests
 */
@Path("seeder")
public class SeederRequest {
    // putting a param using query
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSeederList(@QueryParam("keywords") String keywords) throws InterruptedException {
        if(keywords == null){
            keywords = "";
        }

        String[] strings = null;
        if (!keywords.isEmpty()){
            strings = keywords.split("\\s");
        }

        SeederFactoryClient factoryClient = Portal.getInstance().getFactoryClient();

        List<Seeder> seeders = factoryClient.listSeeders(strings);

        factoryClient.shutdown();

        return new Gson().toJson(seeders);
    }
}
