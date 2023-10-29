package rs.luis.respondet.lib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ResultHandler implements Runnable {

    private final Caller caller;

    @Autowired
    public ResultHandler(Caller caller) {
        this.caller = caller;
    }

    public void run() {

        while (true) {
            try {
                String result = this.caller.getCallService().take().get();
                System.out.printf("-------> Result from thread execution: %s%n", result);

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
