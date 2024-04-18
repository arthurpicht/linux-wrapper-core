package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class SudoRemoteExecutionConfig extends AbstractWrapperConfig {

    private final String user;
    private final String host;
    private final int port;
    private final String command;
    private final String sudoPassword;

    public SudoRemoteExecutionConfig(
            String user,
            String host,
            int port,
            String command,
            String sudoPassword,
            Logger logger,
            Level logLevelStd,
            Level logLevelError,
            boolean outputToConsole
    ) {
        super(logger, logLevelStd, logLevelError, outputToConsole);
        this.user = user;
        this.host = host;
        this.port = port;
        this.command = command;
        this.sudoPassword = sudoPassword;
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

    public String getCommand() {
        return this.command;
    }

    public String getSudoPassword() {
        return this.sudoPassword;
    }

}
