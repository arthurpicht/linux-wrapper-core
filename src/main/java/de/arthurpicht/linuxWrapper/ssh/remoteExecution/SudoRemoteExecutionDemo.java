package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.core.strings.Strings;

public class SudoRemoteExecutionDemo {

    public static void main(String[] args) {

        SudoRemoteExecutionConfig sudoRemoteExecutionConfig = new SudoRemoteExecutionConfigBuilder()
                .withUser("username")
                .withHost("192.168.99.10")
                .withCommand("echo \"test1\"; echo \"test2\"")
                .withSudoPassword("secret")
                .withOutputToConsole(true)
                .build();

        ProcessResultCollection processResultCollection;
        try {
            processResultCollection = SudoRemoteExecution.execute(sudoRemoteExecutionConfig);

            System.out.println("success: " + processResultCollection.isSuccess());
            System.out.println("ExitCode: " + processResultCollection.getExitCode());
            System.out.println("StdOut: " + Strings.listing(processResultCollection.getStandardOut(), "\n"));
            System.out.println("ErrorOut: " + Strings.listing(processResultCollection.getErrorOut(), "\n"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
