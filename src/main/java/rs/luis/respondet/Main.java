package rs.luis.respondet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import rs.luis.respondet.lib.Handler;

@EnableScheduling
@SpringBootApplication
public class Main implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(Main.class);

    private final Handler handler;

    @Autowired
    public Main(Handler handler) {
        this.handler = handler;
    }

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args)  {
        logger.info("Starting ResultHandler...");
        handler.start();
    }
}
