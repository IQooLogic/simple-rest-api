package rs.devlabs.api;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            try {
                Request request = new Request.Builder()
                        .url("http://blocklist.greensnow.co/greensnow.txt")
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();
                if (response.code() == 200) {
                    String strIPs = response.body().string();
                    List<ThreatIndicator> indicators = new ArrayList<>();
                    strIPs.lines().forEach(ip -> {
                        long now = System.currentTimeMillis();
                        ThreatIndicator ti = new ThreatIndicator(ip, now, now);
                        indicators.add(ti);
                    });
                    tiRepo.saveAll(indicators);
                    logger.info("Saved {} threat indicators", indicators.size());
                }
            } catch (RuntimeException | IOException ex) {
                logger.error("Error processing", ex);
            }
        });
    }
}
