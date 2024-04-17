package de.arthurpicht.linuxWrapper.ansible.playbook;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfigBuilder;

import java.nio.file.Path;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;

@SuppressWarnings("unused")
public class PlaybookExecuteBuilder extends AbstractWrapperConfigBuilder<PlaybookExecuteBuilder> {

    private Path inventory = null;

    public PlaybookExecuteBuilder withInventoryPath(Path inventory) {
        this.inventory = inventory;
        return this;
    }

    public PlaybookExecute build(Path playbook) {
        assertArgumentNotNull("playbook", playbook);
        PlaybookExecuteConfig playbookExecuteConfig = new PlaybookExecuteConfig(
                playbook,
                this.inventory,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );
        return new PlaybookExecute(playbookExecuteConfig);
    }

}