package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class RemoteExecutionConfig extends AbstractWrapperConfig {

    private final String user;
    private final String host;
    private final int port;
    private final List<String> commands;

    public RemoteExecutionConfig(
            String user,
            String host,
            int port,
            List<String> commands,
            Logger logger,
            Level logLevelStd,
            Level logLevelError,
            boolean outputToConsole
    ) {
        super(logger, logLevelStd, logLevelError, outputToConsole);
        this.user = user;
        this.host = host;
        this.port = port;
        this.commands = Collections.unmodifiableList(commands);
    }

    public String getUser() {
        return this.user;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public List<String> getCommands() {
        return this.commands;
    }

}
