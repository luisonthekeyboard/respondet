package rs.luis.respondet.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Respondet {

    private final Handler handler;

    private final Logger logger = LoggerFactory.getLogger(Respondet.class);


    @Autowired
    public Respondet(Handler handler) {
        this.handler = handler;
    }

    public void start()  {
        logger.info("Starting ResultHandler...");
        handler.start();
    }
}
