package portal.rest;

import portal.seeder.SeederFactoryClient;
import route.Seeder;
import util.SeederUtil;

import javax.ws.rs.*;
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

        List<Seeder> seeders = factoryClient.listSeeders(strings);
        if (seeders.isEmpty()){
            return "No seeders";
        }
        String listString = "";
        for (Seeder seeder : seeders) {
            listString += "+ " + SeederUtil.printSeeder(seeder) + ";\n";
        }

        return listString;
    }
}
