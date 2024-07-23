package rs.luis.respondet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import rs.luis.respondet.lib.Respondet;
import rs.luis.respondet.lib.MonitoringManifest;
import rs.luis.respondet.lib.RespondetTask;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.Executors;

@Configuration
public class CallExecutorConfig implements SchedulingConfigurer {
    private final Logger logger = LoggerFactory.getLogger(CallExecutorConfig.class);
    private final MonitoringManifest monitoringManifest;
    private final Respondet respondet;

    @Autowired
    public CallExecutorConfig(MonitoringManifest monitoringManifest, Respondet respondet) {
        this.monitoringManifest = monitoringManifest;
        this.respondet = respondet;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(
                Executors.newScheduledThreadPool(monitoringManifest.getCallMap().size()));
        var intervals = monitoringManifest.getCallMap().keySet();
        logger.info("Starting the timers...");
        for (Integer interval : intervals) {
            taskRegistrar.addTriggerTask(
                    new RespondetTask(respondet, monitoringManifest.getCallMap().get(interval)),
                    new ExecutionInterval(interval));
        }
    }

    static class ExecutionInterval implements Trigger {

        private final Integer interval;

        public ExecutionInterval(Integer interval) {
            this.interval = interval;
        }

        @Override
        public Instant nextExecution(TriggerContext triggerContext) {
            return Objects.requireNonNullElse(
                            triggerContext.lastActualExecution(),
                            Instant.now())
                    .plusMillis(interval * 1000L);
        }
    }
}
