package de.arthurpicht.linuxWrapper.ansible.playbook;

import de.arthurpicht.linuxWrapper.ExecutionHelper;
import de.arthurpicht.linuxWrapper.LinuxWrapperCoreRuntimeException;
import de.arthurpicht.utils.core.strings.Strings;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PlaybookExecute {

    private final PlaybookExecuteConfig playbookExecuteConfig;

    public PlaybookExecute(PlaybookExecuteConfig playbookExecuteConfig) {
        this.playbookExecuteConfig = playbookExecuteConfig;
    }

    public void execute() {
        assertPlaybook();
        Path workingDir = this.playbookExecuteConfig.getPlaybook().toAbsolutePath().getParent();
        String playbookFileName = this.playbookExecuteConfig.getPlaybook().getFileName().toString();
        String[] command = buildCommand(playbookFileName);
        ExecutionHelper.commandLogging(workingDir.toString(), command, this.playbookExecuteConfig);
        ExecutionHelper.execute(workingDir, command, this.playbookExecuteConfig, true);
    }

    private String[] buildCommand(String playbookFileName) {
        List<String> command = new ArrayList<>();
        command.add("ansible-playbook");
        if (this.playbookExecuteConfig.hasInventory()) {
            command.add("-i");
            command.add(this.playbookExecuteConfig.getInventory().toAbsolutePath().toString());
        }
        command.add(playbookFileName);
        return Strings.toArray(command);
    }

    private void assertPlaybook() {
        if (!FileUtils.isExistingRegularFile(this.playbookExecuteConfig.getPlaybook()))
            throw new LinuxWrapperCoreRuntimeException(
                    "Ansible playbook not found: [" + this.playbookExecuteConfig.getPlaybook() + "].");
    }

}
