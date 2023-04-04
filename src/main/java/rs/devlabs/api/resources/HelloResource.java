package rs.devlabs.api.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 20.02.2023T17:06
 */
@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloResource {

    private static final Logger logger = LoggerFactory.getLogger(HelloResource.class);

    @GET
    public Response hello() {
        logger.info("printing hello");

        return Response.ok("Hello World!!!").build();
    }

    @Path("/say")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response say(@QueryParam("what") String what) {
        return Response.ok(what).build();
    }
}
