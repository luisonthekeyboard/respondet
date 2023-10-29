package rs.luis.respondet;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

@ShellComponent
public class Command {

    public static final long ONE_SECOND = 1000L;
    public static final Map<Integer, String> CALL_MAP = Map.of(
            11, "oracle.com",
            7, "aws.amazon.com",
            3, "github.com",
            5, "lobste.rs",
            27, "dev.java",
            17, "java.com");
    private final ExecutorCompletionService<String> callService;
    private final ExecutorService resultHandlerExecutor;

    public Command() {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        this.callService = new ExecutorCompletionService<>(executor);
        this.resultHandlerExecutor = Executors.newCachedThreadPool();
    }

    @ShellMethod(key = "start")
    public void resp() throws InterruptedException {

        // Start the result handler
        System.out.println("Starting ResultHandler...");
        resultHandlerExecutor.execute(new ResultHandler(this.callService));

        // Setup of work to do
        System.out.println("Preparing call map...");
        var intervals = CALL_MAP.keySet();

        // Timings
        System.out.println("Start the timers...");
        var startTime = System.currentTimeMillis() / 1000;

        while (true) {
            var currSecond = (System.currentTimeMillis() / 1000) - startTime;
            System.out.println("\n\n\n__________Seconds passed: " + currSecond);

            for (Integer interval : intervals) {
                if (interval <= currSecond && currSecond % interval == 0) {
                    String url = CALL_MAP.get(interval);
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

