package rs.luis.respondet.lib;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Respondet {

    private static final long ONE_SECOND = 1000L;

    private final Caller caller;
    private final MonitoringManifest monitoringManifest;
    private final Handler handler;


    public Respondet(MonitoringManifest monitoringManifest, Caller caller, Handler handler) {
        this.monitoringManifest = monitoringManifest;
        this.caller = caller;
        this.handler = handler;
    }

    public void start() throws InterruptedException {
        // Start result handler
        System.out.println("Starting ResultHandler...");
        handler.start();

        // Setup of work to do
        System.out.println("Preparing call map...");
        var intervals = monitoringManifest.getCallMap().keySet();

        // Timings
        System.out.println("Start the timers...");
        var startTime = System.currentTimeMillis() / 1000;

        while (true) {
            var currSecond = (System.currentTimeMillis() / 1000) - startTime;
            System.out.println("\n\n\n__________Seconds passed: " + currSecond);

            for (Integer interval : intervals) {
                if (interval <= currSecond && currSecond % interval == 0) {
                    String url = monitoringManifest.getCallMap().get(interval);
                    System.out.printf("Scheduling the call to %s...%n", url);

                    caller.submit(() -> fetchURL(url));
                }
            }

            Thread.sleep(ONE_SECOND);
        }
    }

    private String fetchURL(String url) {
        try {
            long sleepTime = new Random().nextLong(10_000L);
            System.out.printf("........Sleeping for %d .......%n", sleepTime);
            Thread.sleep(sleepTime);
            System.out.printf("........Sleeping for %d ....... -- DONE -- %n", sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return url;
    }

}
