package rs.devlabs.api;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import rs.devlabs.api.db.model.ThreatIndicator;
import rs.devlabs.api.db.model.ThreatSource;
import rs.devlabs.api.db.repos.ThreatIndicatorRepository;
import rs.devlabs.api.db.repos.ThreatSourceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Miloš Stojković <milos@ast.co.rs>
 * 24.02.2023T16:35
 */
@Configuration
@EnableScheduling
public class ThreatSourceProcessor implements SchedulingConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(ThreatSourceProcessor.class);

    @Autowired
    private ThreatSourceRepository repo;
    @Autowired
    private ThreatIndicatorRepository tiRepo;

    public ThreatSourceProcessor() {
        logger.info("{} started", getClass().getSimpleName());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.addCronTask(() -> logger.info("fetchFromThreatSources()"), "0 0 12 * * ?");
        taskRegistrar.addCronTask(this::process, "0 * * ? * *");
    }

    private void process() {
        logger.info("processing ...");
        Iterable<ThreatSource> sources = repo.findAll();
        sources.forEach(source -> {
            logger.info("Processing '{}' source ...", source.getId());
            try (Client client = ClientBuilder.newClient()) {
                WebTarget target = client.target(source.getId());
                Invocation.Builder request = target.request(MediaType.TEXT_PLAIN);
                Response response = request.get();
                if (response.getStatus() == 200) {
                    String strIPs = response.readEntity(String.class);
                    List<ThreatIndicator> indicators = new ArrayList<>();
                    strIPs.lines().forEach(ip -> {
                        long now = System.currentTimeMillis();
                        indicators.add(new ThreatIndicator(ip, now, now));
                    });
                    tiRepo.saveAll(indicators);
                }
            } catch (RuntimeException ex) {
                logger.error("Error processing", ex);
            }
        });
    }
}
