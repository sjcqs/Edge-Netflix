import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by satyan on 10/7/17.
 */
@Path("home")
public class Resource {
    // A basic resource accessible at http://localhost:2222/home/hello.
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(){
        return "Hello world !";
    }

    // putting a param using query
    @GET
    @Path("param")
    @Produces(MediaType.TEXT_PLAIN)
    public String paramMethod(@QueryParam("name") String name) {
        return "Hello, " + name;
    }

    // putting a param using path
    @GET
    @Path("param/{var}")
    @Produces(MediaType.TEXT_PLAIN)
    public String pathMethod(@PathParam("var") String name) {
        return "Hello, " + name;
    }

    // post method
    @POST
    @Path("post")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String postMethod(@FormParam("name") String name) {
        return "<h2>Hello, " + name + "</h2>";
    }
}
