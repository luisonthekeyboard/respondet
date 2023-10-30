package rs.luis.respondet.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Respondet {

    private static final long ONE_SECOND = 1000L;
    private final Caller caller;
    private final MonitoringManifest monitoringManifest;
    private final Handler handler;
    private final Logger logger = LoggerFactory.getLogger(Respondet.class);


    public Respondet(MonitoringManifest monitoringManifest, Caller caller, Handler handler) {
        this.monitoringManifest = monitoringManifest;
        this.caller = caller;
        this.handler = handler;
    }

    public void start() throws InterruptedException {
        // Start result handler
        logger.info("Starting ResultHandler...");
        handler.start();

        // Setup of work to do
        logger.info("Preparing call map...");
        var intervals = monitoringManifest.getCallMap().keySet();

        // Timings
        logger.info("Starting the timers...");
        var startTime = System.currentTimeMillis() / 1000;

        while (true) {
            var currSecond = (System.currentTimeMillis() / 1000) - startTime;
            logger.debug("\n\n\n__________Seconds passed: " + currSecond);

            for (Integer interval : intervals) {
                if (interval <= currSecond && currSecond % interval == 0) {
                    String url = monitoringManifest.getCallMap().get(interval);
                    logger.debug("Scheduling the call to %s...%n".formatted(url));

                    caller.submit(new HttpTask(url));
                }
            }

            Thread.sleep(ONE_SECOND);
        }
    }
}
