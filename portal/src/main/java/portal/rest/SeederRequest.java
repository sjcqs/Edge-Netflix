package portal.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by satyan on 10/7/17.
 * All seeder related requests
 */
@Path("seeder")
public class SeederRequest {

    // putting a param using query
    @GET
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSeederList(@QueryParam("keywords") String keywords) {
        if(keywords == null){
            return listSeeders();
        }
        else
            return searchSeeders(keywords);
    }

    private String searchSeeders(String name) {
        return getClass().getName() + ", keywords: "+ name;
    }

    private String listSeeders() {
        return getClass().getName();
    }

}
