package de.arthurpicht.linuxWrapper.ssh.shell.interactive;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfigBuilder;
import de.arthurpicht.utils.core.strings.Strings;

@SuppressWarnings("unused")
public class ShellInteractiveBuilder extends AbstractWrapperConfigBuilder<ShellInteractiveBuilder> {

    private String user = null;
    private String host = null;
    private int port = 22;

    public ShellInteractiveBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public ShellInteractiveBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public ShellInteractiveBuilder withPost(int port) {
        this.port = port;
        return this;
    }

    public ShellInteractive build() {
        if (Strings.isNullOrEmpty(this.user))
            throw new IllegalArgumentException("Parameter [user] not specified.");
        if (Strings.isNullOrEmpty(this.host))
            throw new IllegalArgumentException("Parameter [host] not specified.");

        ShellInteractiveConfig shellInteractiveConfig = new ShellInteractiveConfig(
                this.user,
                this.host,
                this.port,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );
        return new ShellInteractive(shellInteractiveConfig);
    }

}