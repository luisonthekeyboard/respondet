package rs.luis.respondet.lib;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;

public class ResultHandler implements Runnable {

    private final ExecutorCompletionService<String> completionService;

    public ResultHandler(ExecutorCompletionService<String> completionService) {
        this.completionService = completionService;
    }

    public void run() {

        while (true) {
            try {
                String result = completionService.take().get();
                System.out.printf("-------> Result from thread execution: %s%n", result);

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
