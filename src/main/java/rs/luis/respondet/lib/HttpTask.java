package rs.luis.respondet.lib;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;

public class HttpTask implements Callable<HttpResponse> {

    private final Logger logger = LoggerFactory.getLogger(Respondet.class);

    private final String url;

    public HttpTask(String url) {
        this.url = url;
    }

    @Override
    public HttpResponse call() throws IOException {
        logger.debug("Calling %s ...".formatted(url));
        HttpResponse httpResponse = Request.get(url).execute().returnResponse();
        logger.debug("Calling %s ... DONE.".formatted(url));

        return httpResponse;
    }
}
