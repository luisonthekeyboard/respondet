package rs.luis.respondet.lib;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Scope("singleton")
public class Caller {

    private final ExecutorCompletionService<String> callService;

    public Caller() {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        this.callService = new ExecutorCompletionService<>(executor);
    }

    public Future<String> submit(Callable<String> callable) {
        return callService.submit(callable);
    }

    public ExecutorCompletionService<String> getCallService() {
        return callService;
    }
}
