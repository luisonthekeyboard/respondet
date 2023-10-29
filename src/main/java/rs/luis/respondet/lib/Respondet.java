package rs.luis.respondet.lib;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Respondet {

    private static final long ONE_SECOND = 1000L;
    private final ExecutorCompletionService<String> callService;
    private final ExecutorService resultHandlerExecutor;

    private final MonitoringManifest monitoringManifest;


    public Respondet(MonitoringManifest monitoringManifest) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        this.callService = new ExecutorCompletionService<>(executor);
        this.resultHandlerExecutor = Executors.newCachedThreadPool();

        this.monitoringManifest = monitoringManifest;
    }

    public void start() throws InterruptedException {
        // Start the result handler
        System.out.println("Starting ResultHandler...");
        resultHandlerExecutor.execute(new ResultHandler(this.callService));

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

                    callService.submit(() -> fetchURL(url));
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
