package portal.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by satyan on 10/7/17.
 */
@Path("seeder")
public class Seeder {

    // putting a param using query
    @GET
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSeederList(@QueryParam("keywords") String keywords) {
        if(keywords == null){
            return seederList();
        }
        else
            return seederListSearch(keywords);
    }

    private String seederListSearch(String name) {
        return "SEEDERLIST " + name;
    }

    private String seederList() {
        return "SEEDERLIST";
    }

}
