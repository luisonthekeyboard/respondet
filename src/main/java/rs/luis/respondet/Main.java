package rs.luis.respondet;

import org.apache.hc.core5.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import rs.luis.respondet.lib.Caller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@EnableScheduling
@SpringBootApplication
public class Main implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(Main.class);

    private final Caller caller;


    @Autowired
    public Main(Caller caller) {
        this.caller = caller;
    }

    public static void main(String[] args) {
        Thread printingHook = new Thread(() -> System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH........................"));
        Runtime.getRuntime().addShutdownHook(printingHook);
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException, ExecutionException {
        logger.info("Starting ResultHandler...");

        Future<HttpResponse> f;
        while ((f = caller.getCallService().take()) != null) {

            HttpResponse httpResponse = f.get();
            logger.debug("<<< %d >>>%n".formatted(httpResponse.getCode()));
        }
    }
}
