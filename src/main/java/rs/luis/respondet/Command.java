package rs.luis.respondet;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Map;
import java.util.concurrent.*;

@ShellComponent
public class Command {

    private final ExecutorService executor;
    private final ExecutorCompletionService<String> completionService;
    private final ExecutorService threadyExecutor;

    public Command() {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.completionService = new ExecutorCompletionService<String>(executor);
        this.threadyExecutor = Executors.newFixedThreadPool(7);
    }

    @ShellMethod(key = "hello-world")
    public String helloWorld(
            @ShellOption(defaultValue = "spring") String arg
    ) {
        return "Hello world " + arg;
    }

    @ShellMethod(key = "resp")
    public void resp() throws InterruptedException {

        // Start a thready
        System.out.println("Threadyyyyyyy");
        threadyExecutor.execute(new Thready(this.completionService));

        // Setup
        System.out.println("Defining call map...");
        var callMap = Map.of(11, "google.com", 7, "amazon.com", 1, "java.com");
        var intervals = callMap.keySet();

        // Timings
        System.out.println("Start the timers!");
        var startTime = System.currentTimeMillis() / 1000;

        while (true) {
            var currSecond = (System.currentTimeMillis() / 1000) - startTime;
            System.out.println("Seconds passed: " + currSecond);

            for (Integer interval : intervals) {
                if (interval <= currSecond && currSecond % interval == 0) {
                    String url = callMap.get(interval);
                    System.out.printf("Make the call to %s!!!%n", url);
                    completionService.submit(() -> fetchURL(url, interval));
                }
            }

            Thread.sleep(1000L);
        }
    }

    private String fetchURL(String url, int interval) {
        try {
            Thread.sleep(interval * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return url;
    }
}

