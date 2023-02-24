package rs.devlabs.api.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import rs.devlabs.api.db.model.ThreatIndicator;
import rs.devlabs.api.db.repos.ThreatIndicatorRepository;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 24.02.2023T16:45
 */
@Path("/threat")
public class ThreatIndicatorResource {

    private static final Logger logger = LoggerFactory.getLogger(ThreatIndicatorResource.class);

    @Autowired
    private ThreatIndicatorRepository repo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        logger.info("returning tis");
        Iterable<ThreatIndicator> tis = repo.findAll();
        return Response.ok(tis).build();
    }
}
