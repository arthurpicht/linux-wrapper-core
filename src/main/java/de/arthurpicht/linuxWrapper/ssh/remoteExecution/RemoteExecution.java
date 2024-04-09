package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import de.arthurpicht.linuxWrapper.ExecutionHelper;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.core.strings.Strings;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RemoteExecution {

    public static ProcessResultCollection execute(RemoteExecutionConfig remoteExecutionConfig) {
        String remoteCommands = Strings.listing(remoteExecutionConfig.getCommands(), "\n");
        InputStream stringInputStream = new ByteArrayInputStream(remoteCommands.getBytes());
        String[] command = new String[]{
                "ssh",
                "-T",
                remoteExecutionConfig.getUser() + "@" + remoteExecutionConfig.getHost(),
                "-p",
                remoteExecutionConfig.getPort() + ""
        };

        ExecutionHelper.commandLogging(command, remoteExecutionConfig);
        remoteCommandLogging(remoteExecutionConfig, remoteExecutionConfig);
        return ExecutionHelper.execute(stringInputStream, command, remoteExecutionConfig, true);
    }

    private static void remoteCommandLogging(
            RemoteExecutionConfig remoteExecutionConfig, AbstractWrapperConfig abstractWrapperConfig) {

        if (abstractWrapperConfig.hasLogger()) {
            abstractWrapperConfig.getLogger().atLevel(abstractWrapperConfig.getLogLevelStd()).log("Remote commands:");
            remoteExecutionConfig.getCommands().forEach(s ->
                    abstractWrapperConfig.getLogger().atLevel(abstractWrapperConfig.getLogLevelStd()).log(s)
            );
        }
        if (abstractWrapperConfig.isOutputToConsole()) {
            System.out.println("Remote commands:");
            remoteExecutionConfig.getCommands().forEach(System.out::println);
        }
    }

}
