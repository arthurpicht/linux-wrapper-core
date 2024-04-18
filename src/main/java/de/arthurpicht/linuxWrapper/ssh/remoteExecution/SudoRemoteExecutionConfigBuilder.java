package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfigBuilder;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SudoRemoteExecutionConfigBuilder extends AbstractWrapperConfigBuilder<SudoRemoteExecutionConfigBuilder> {

    private String user = null;
    private String host = null;
    private int port = 22;
    private String command = "";
    private String sudoPassword = "";

    public SudoRemoteExecutionConfigBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public SudoRemoteExecutionConfigBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public SudoRemoteExecutionConfigBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public SudoRemoteExecutionConfigBuilder withCommand(String command) {
        this.command = command;
        return this;
    }

    public SudoRemoteExecutionConfigBuilder withSudoPassword(String sudoPassword) {
        this.sudoPassword = sudoPassword;
        return this;
    }

    public SudoRemoteExecutionConfig build() {
        if (Strings.isUnspecified(this.user))
            throw new IllegalStateException("Mandatory parameter [user] is missing.");
        if (Strings.isUnspecified(this.host))
            throw new IllegalStateException("Mandatory parameter [host] is missing.");
        if (Strings.isUnspecified(this.command))
            throw new IllegalStateException("Mandatory parameter [command] is missing.");
        if (Strings.isUnspecified(this.sudoPassword))
            throw new IllegalStateException("Mandatory parameter [sudoPassword] is missing.");

        return new SudoRemoteExecutionConfig(
                this.user,
                this.host,
                this.port,
                this.command,
                this.sudoPassword,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );
    }

}