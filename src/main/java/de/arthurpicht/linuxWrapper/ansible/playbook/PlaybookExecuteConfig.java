package de.arthurpicht.linuxWrapper.ansible.playbook;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.nio.file.Path;

public final class PlaybookExecuteConfig extends AbstractWrapperConfig {

    private final Path playbook;

    public PlaybookExecuteConfig(
            Path playbook,
            Logger logger,
            Level logLevelStd,
            Level logLevelError,
            boolean outputToConsole) {

        super(logger, logLevelStd, logLevelError, outputToConsole);
        this.playbook = playbook;
    }

    public Path getPlaybook() {
        return playbook;
    }

}
