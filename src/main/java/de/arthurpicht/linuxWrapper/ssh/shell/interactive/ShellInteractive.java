package de.arthurpicht.linuxWrapper.ssh.shell.interactive;

import de.arthurpicht.linuxWrapper.ExecutionHelper;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

public class ShellInteractive {

    private final ShellInteractiveConfig shellInteractiveConfig;

    public ShellInteractive(ShellInteractiveConfig shellInteractiveConfig) {
        this.shellInteractiveConfig = shellInteractiveConfig;
    }

    public void open() {
        String[] command = buildCommand();
        ExecutionHelper.commandLogging(command, this.shellInteractiveConfig);
        ExecutionHelper.executeInteractively(command, false);
    }

    private String[] buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("ssh");
        command.add(this.shellInteractiveConfig.getUser() + "@" + this.shellInteractiveConfig.getHost());
        if (this.shellInteractiveConfig.getPort() != 22) {
            command.add("-p");
            command.add(String.valueOf(this.shellInteractiveConfig.getPort()));
        }
        return Strings.toArray(command);
    }

}
