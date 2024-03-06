package de.arthurpicht.linuxWrapper.ssh.keyPairCreate;

import de.arthurpicht.linuxWrapper.ExecutionHelper;
import de.arthurpicht.linuxWrapper.LinuxWrapperCoreRuntimeException;
import de.arthurpicht.utils.core.strings.Strings;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SshKeyPairCreate {

    private final SshKeyPairCreateConfig sshKeyPairCreateConfig;

    public SshKeyPairCreate(SshKeyPairCreateConfig sshKeyPairCreateConfig) {
        this.sshKeyPairCreateConfig = sshKeyPairCreateConfig;
    }

    public void create() {
        assertTargetDir();
        String[] command = buildCommand();
        ExecutionHelper.commandLogging(command, this.sshKeyPairCreateConfig);
        ExecutionHelper.execute(command, this.sshKeyPairCreateConfig, true);
    }

    private String[] buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("ssh-keygen");
        command.add("-t");
        command.add(this.sshKeyPairCreateConfig.getKeyType().name());
        if (this.sshKeyPairCreateConfig.getLength() != null) {
            command.add("-b");
            command.add(this.sshKeyPairCreateConfig.getLength().toString());
        }
        if (this.sshKeyPairCreateConfig.getComment() != null) {
            command.add("-C");
            command.add("\"" + this.sshKeyPairCreateConfig.getComment() + "\"");
        }
        command.add("-N");
        command.add(this.sshKeyPairCreateConfig.getPassword());
        command.add("-f");
        command.add(this.sshKeyPairCreateConfig.getKeyPath().toString());
        return Strings.toArray(command);
    }

    private void assertTargetDir() {
        Path dir = this.sshKeyPairCreateConfig.getKeyPath().getParent();
        if (!FileUtils.isExistingDirectory(dir))
            throw new LinuxWrapperCoreRuntimeException(
                    "Target directory for ssh key pair creation not found: [" + dir + "].");
    }

}
