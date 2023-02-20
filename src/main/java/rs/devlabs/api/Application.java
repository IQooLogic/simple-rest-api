package rs.devlabs.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 20.02.2023T16:51
 */
@ApplicationPath("/*")
public class Application extends ResourceConfig {
    public Application() {
        packages("rs.devlabs.api");
    }
}
