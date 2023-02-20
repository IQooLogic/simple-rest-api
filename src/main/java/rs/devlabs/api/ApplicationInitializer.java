package rs.devlabs.api;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 20.02.2023T16:56
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ComponentScan(basePackages = "rs.devlabs.api")
public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.setInitParameter("contextConfigLocation", "rs.devlabs.api");
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("simple_api",
                new ServletContainer(new Application()));
        dispatcher.addMapping("/v1/*");
        dispatcher.setLoadOnStartup(1);
    }
}
