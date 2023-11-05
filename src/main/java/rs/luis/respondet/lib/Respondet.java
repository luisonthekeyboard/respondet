package rs.luis.respondet.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Respondet {

    private final Caller caller;
    private final Handler handler;
    private final MonitoringManifest monitoringManifest;

    private final Logger logger = LoggerFactory.getLogger(Respondet.class);


    public Respondet(MonitoringManifest monitoringManifest, Caller caller, Handler handler) {
        this.monitoringManifest = monitoringManifest;
        this.caller = caller;
        this.handler = handler;
    }

    public void start() throws InterruptedException {
        logger.info("Starting ResultHandler...");
        handler.start();

        // Setup of work to do
        logger.info("Preparing call map...");
        var intervals = monitoringManifest.getCallMap().keySet();
        long max = intervals.stream().max(Integer::compareTo).orElse(1) * 1000L;


        // Timings
        try (ScheduledExecutorService executor = Executors.newScheduledThreadPool(monitoringManifest.getCallMap().size())) {

            logger.info("Starting the timers...");
            for (Integer interval : intervals) {
                executor.scheduleAtFixedRate(
                        new RespondetTask(caller, monitoringManifest.getCallMap().get(interval)),
                        interval,
                        interval,
                        TimeUnit.SECONDS);
            }

            while (!executor.isTerminated()) {
                Thread.sleep(max);
            }
        }
    }
}
