package de.arthurpicht.linuxWrapper.ssh.shell.interactive;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.nio.file.Path;

public final class ShellInteractiveConfig extends AbstractWrapperConfig {

    private final String user;
    private final String host;
    private final int port;

    public ShellInteractiveConfig(
            String user,
            String host,
            int port,
            Logger logger,
            Level logLevelStd,
            Level logLevelError,
            boolean outputToConsole) {

        super(logger, logLevelStd, logLevelError, outputToConsole);
        this.user = user;
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }

    public String getUser() {
        return this.user;
    }

    public int getPort() {
        return this.port;
    }

}
