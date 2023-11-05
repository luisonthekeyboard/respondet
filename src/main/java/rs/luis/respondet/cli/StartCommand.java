package rs.luis.respondet.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import rs.luis.respondet.lib.MonitoringManifest;
import rs.luis.respondet.lib.Respondet;

import java.io.IOException;

@ShellComponent
public class StartCommand {

    private final Respondet respondet;
    private final MonitoringManifest monitoringManifest;

    @Autowired
    public StartCommand(Respondet respondet, MonitoringManifest monitoringManifest) {
        this.respondet = respondet;
        this.monitoringManifest = monitoringManifest;
    }

    @ShellMethod(key = "start")
    public void start(@Option(required = true) String manifestFile) throws InterruptedException, IOException {
        this.monitoringManifest.readFromCSV(manifestFile);
        respondet.start();
    }
}

