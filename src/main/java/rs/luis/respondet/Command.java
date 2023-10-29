package rs.luis.respondet;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Map;

@ShellComponent
public class Command {

    @ShellMethod(key = "hello-world")
    public String helloWorld(
            @ShellOption(defaultValue = "spring") String arg
    ) {
        return "Hello world " + arg;
    }

    @ShellMethod(key = "resp")
    public String resp() throws InterruptedException {

        // Setup

        System.out.println("Defining call map...");
        var callMap = Map.of(11, "google.com", 7, "amazon.com", 1, "java.com");
        var intervals = callMap.keySet();

        var startTime = System.currentTimeMillis() / 1000;

        while (true) {
            var currSecond = (System.currentTimeMillis() / 1000) - startTime;
            System.out.println("Seconds passed: " + currSecond);
            Thread.sleep(1000L);
            for (Integer interval : intervals) {
                if (interval <= currSecond && currSecond % interval == 0) {
                    System.out.printf("Make the call to %s!!!%n", callMap.get(interval));
                }
            }
        }

//        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
//
//            var completionService = new ExecutorCompletionService<String>(executor);
//
//            for (String url : callMap.keySet()) {
//                int interval = callMap.get(url);
//
//                completionService.submit(() -> fetchURL(url, interval));
//            }
//
//            while (true) {
//                Future<String> resultFuture = completionService.take();
//                String result = resultFuture.get();
//            }
//
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    private String fetchURL(String url, int interval) {
        try {
            Thread.sleep(interval * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println(url);
        return url;
    }
}