package portal.rest;

import info.SeederInfo;
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
    private SeederFactoryClient factoryClient = SeederFactoryClient.getInstance();

    // putting a param using query
    @GET
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSeederList(@QueryParam("keywords") String keywords) {
        if(keywords == null){
            keywords = "";
        }

        String[] strings = null;
        if (!keywords.isEmpty()){
            strings = keywords.split("\\s");
        }

        List<SeederInfo> seeders = factoryClient.listSeeders(strings);
        if (seeders.isEmpty()){
            return "No seeders";
        }
        String listString = "";
        for (SeederInfo seeder : seeders) {
            listString += "+ " + seeder + ";\n";
        }

        return listString;
    }
}
