package de.arthurpicht.linuxWrapper.ssh.remoteExecution;

import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.core.strings.Strings;

public class RemoteExecutionDemo {

    public static void main(String[] args) {

        RemoteExecutionConfig remoteExecutionConfig = new RemoteExecutionConfigBuilder()
                .withUser("m7radmin")
                .withHost("192.168.99.10")
                .addCommand("echo \"Hello world!\"")
                .addCommand("echo \"something different\" >> my_file.txt")
                .addCommand("git config --global http.proxy 'http://user:b!oese@192.168.77.77:8080'")
                .build();

        ProcessResultCollection processResultCollection;
        try {
            processResultCollection = RemoteExecution.execute(remoteExecutionConfig);

            System.out.println("success: " + processResultCollection.isSuccess());
            System.out.println("ExitCode: " + processResultCollection.getExitCode());
            System.out.println("StdOut: " + Strings.listing(processResultCollection.getStandardOut(), "\n"));
            System.out.println("ErrorOut: " + Strings.listing(processResultCollection.getErrorOut(), "\n"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
