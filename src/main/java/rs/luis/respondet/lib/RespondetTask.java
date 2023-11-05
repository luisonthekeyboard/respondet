package rs.luis.respondet.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TimerTask;

public class RespondetTask extends TimerTask {
    private final Logger logger = LoggerFactory.getLogger(RespondetTask.class);
    private Caller caller;
    private Set<String> urls;

    public RespondetTask(Caller caller, Set<String> urls) {
        this.caller = caller;
        this.urls = urls;
    }

    @Override
    public void run() {

        logger.debug("____________________ Current seconds: " + LocalDateTime.now());

        for (String url : urls) {
            logger.debug("Scheduling the call to %s...%n".formatted(url));
            caller.submit(new HttpTask(url));
        }
    }


}
