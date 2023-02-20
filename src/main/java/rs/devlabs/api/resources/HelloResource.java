package rs.devlabs.api.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 20.02.2023T17:06
 */
@Path("hello")
@Produces(MediaType.TEXT_PLAIN)
public class HelloResource {

    @GET
    public Response hello() {
        return Response.ok("Hello World!").build();
    }
}
