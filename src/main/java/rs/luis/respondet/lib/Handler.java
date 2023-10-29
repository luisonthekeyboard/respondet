package rs.luis.respondet.lib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Handler {

    private final ExecutorService resultHandlerExecutor;
    private final ResultHandler resultHandler;

    @Autowired
    public Handler(ResultHandler resultHandler) {
        this.resultHandlerExecutor = Executors.newCachedThreadPool();
        this.resultHandler = resultHandler;
    }

    public void start() {
        resultHandlerExecutor.execute(this.resultHandler);
    }
}
