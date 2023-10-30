package rs.luis.respondet.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Callable;

public class HttpTask implements Callable<String> {

    public static final long TEN_SECONDS = 10_000L;
    private final Logger logger = LoggerFactory.getLogger(Respondet.class);

    private final String url;

    public HttpTask(String url) {
        this.url = url;
    }

    @Override
    public String call() throws InterruptedException {

        long sleepTime = new Random().nextLong(TEN_SECONDS);

        logger.debug("........Sleeping for %d .......%n".formatted(sleepTime));
        Thread.sleep(sleepTime);
        logger.debug("........Sleeping for %d ....... -- DONE -- %n".formatted(sleepTime));

        return url;
    }
}
