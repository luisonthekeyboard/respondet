package rs.luis.respondet.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import rs.luis.respondet.lib.Respondet;

@ShellComponent
public class Command {

    private final Respondet respondet;

    @Autowired
    public Command(Respondet respondet) {
        this.respondet = respondet;
    }

    @ShellMethod(key = "start")
    public void resp() throws InterruptedException {

        respondet.start();
    }
}

