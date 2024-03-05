package de.arthurpicht.linuxWrapper.ansible.playbook;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfigBuilder;

import java.nio.file.Path;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;

public class PlaybookExecuteBuilder extends AbstractWrapperConfigBuilder<PlaybookExecuteBuilder> {

    public PlaybookExecute build(Path playbook) {
        assertArgumentNotNull("playbook", playbook);
        PlaybookExecuteConfig playbookExecuteConfig = new PlaybookExecuteConfig(
                playbook,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );
        return new PlaybookExecute(playbookExecuteConfig);
    }

}