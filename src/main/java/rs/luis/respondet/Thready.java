package rs.luis.respondet;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

public class Thready implements Runnable {

    private final ExecutorCompletionService<String> completionService;

    public Thready(ExecutorCompletionService<String> completionService) {
        this.completionService = completionService;
    }

    public void run() {

        while (true) {
            Future<String> resultFuture = null;
            try {
                resultFuture = completionService.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String result = null;
            try {
                result = resultFuture.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("-------> From thready: %s%n", result);
        }
    }
}
