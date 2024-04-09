package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfigBuilder;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RemoteExecutionConfigBuilder extends AbstractWrapperConfigBuilder<RemoteExecutionConfigBuilder> {

    private String user = null;
    private String host = null;
    private int port = 22;
    private List<String> commands = new ArrayList<>();

    public RemoteExecutionConfigBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public RemoteExecutionConfigBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public RemoteExecutionConfigBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public RemoteExecutionConfigBuilder withCommands(List<String> commands) {
        this.commands = commands;
        return this;
    }

    public RemoteExecutionConfigBuilder addCommand(String command) {
        this.commands.add(command);
        return this;
    }

    public RemoteExecutionConfig build() {
        if (Strings.isUnspecified(this.user))
            throw new IllegalStateException("Mandatory parameter [user] is missing.");
        if (Strings.isUnspecified(this.host))
            throw new IllegalStateException("Mandatory parameter [host] is missing.");
        if (this.commands == null || this.commands.isEmpty())
            throw new IllegalStateException("Mandatory parameter [commands] is missing or empty.");

        return new RemoteExecutionConfig(
                this.user,
                this.host,
                this.port,
                this.commands,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );
    }

}