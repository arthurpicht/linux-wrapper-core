package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import de.arthurpicht.linuxWrapper.ExecutionHelper;
import de.arthurpicht.processExecutor.ProcessResultCollection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Executes a command via ssh on a remote machine as sudo authenticated by password.
 */
public class SudoRemoteExecution {

    public static ProcessResultCollection execute(SudoRemoteExecutionConfig sudoRemoteExecutionConfig) {
        String remoteCommand = sudoRemoteExecutionConfig.getCommand();

        InputStream stringInputStream = new ByteArrayInputStream(sudoRemoteExecutionConfig.getSudoPassword().getBytes());
        String[] command = new String[]{
                "ssh",
                "-T",
                sudoRemoteExecutionConfig.getUser() + "@" + sudoRemoteExecutionConfig.getHost(),
                "-p",
                sudoRemoteExecutionConfig.getPort() + "",
                "sudo",
                "-S",
                "--prompt=\"\"",
                "--",
                remoteCommand
        };

        ExecutionHelper.commandLogging(command, sudoRemoteExecutionConfig);
        remoteCommandLogging(sudoRemoteExecutionConfig, sudoRemoteExecutionConfig);
        return ExecutionHelper.execute(stringInputStream, command, sudoRemoteExecutionConfig, true);
    }

    private static void remoteCommandLogging(
            SudoRemoteExecutionConfig sudoRemoteExecutionConfig,
            AbstractWrapperConfig abstractWrapperConfig
    ) {

        if (abstractWrapperConfig.hasLogger()) {
            abstractWrapperConfig
                    .getLogger()
                    .atLevel(abstractWrapperConfig.getLogLevelStd())
                    .log("Remote command: " + sudoRemoteExecutionConfig.getCommand());
        }
        if (abstractWrapperConfig.isOutputToConsole()) {
            System.out.println("Remote commands: " + sudoRemoteExecutionConfig.getCommand());
        }
    }

}
