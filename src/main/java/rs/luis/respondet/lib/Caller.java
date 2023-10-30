package rs.luis.respondet.lib;

import org.apache.hc.core5.http.HttpResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
@Scope("singleton")
public class Caller {

    private final ExecutorCompletionService<HttpResponse> callService;

    public Caller() {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        this.callService = new ExecutorCompletionService<>(executor);
    }

    public void submit(Callable<HttpResponse> callable) {
        callService.submit(callable);
    }

    public ExecutorCompletionService<HttpResponse> getCallService() {
        return callService;
    }
}
