package de.arthurpicht.linuxWrapper.ansible.playbook;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfigBuilder;

import java.nio.file.Path;

public class PlaybookExecuteConfigBuilder extends AbstractWrapperConfigBuilder {

    private Path playbook = null;

    private PlaybookExecuteConfigBuilder withPlaybook(Path playbook) {
        this.playbook = playbook;
        return this;
    }

    public PlaybookExecuteConfig build(String host) {
        return new PlaybookExecuteConfig(
                this.playbook,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );
    }

}