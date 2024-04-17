package de.arthurpicht.linuxWrapper.ansible.playbook;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.nio.file.Path;

public final class PlaybookExecuteConfig extends AbstractWrapperConfig {

    private final Path playbook;
    private final Path inventory;

    public PlaybookExecuteConfig(
            Path playbook,
            Path inventory,
            Logger logger,
            Level logLevelStd,
            Level logLevelError,
            boolean outputToConsole) {

        super(logger, logLevelStd, logLevelError, outputToConsole);
        this.playbook = playbook;
        this.inventory = inventory;
    }

    public Path getPlaybook() {
        return playbook;
    }

    public boolean hasInventory() {
        return this.inventory != null;
    }

    public Path getInventory() {
        if (this.inventory == null) throw new IllegalStateException("Parameter [inventory] not set.");
        return inventory;
    }

}
