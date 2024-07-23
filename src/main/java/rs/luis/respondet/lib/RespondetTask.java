package rs.luis.respondet.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Set;

public class RespondetTask implements  Runnable {
    private final Logger logger = LoggerFactory.getLogger(RespondetTask.class);
    private final Respondet respondet;
    private final Set<String> urls;

    public RespondetTask(Respondet respondet, Set<String> urls) {
        this.respondet = respondet;
        this.urls = urls;
    }

    @Override
    public void run() {
        logger.debug("____________________ Current seconds: " + LocalDateTime.now());
        for (String url : urls) {
            logger.debug("Scheduling the call to %s...%n".formatted(url));
            respondet.submit(new HttpTask(url));
        }
    }
}
