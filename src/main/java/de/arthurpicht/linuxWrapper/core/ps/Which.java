package de.arthurpicht.linuxWrapper.core.ps;

import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;

@SuppressWarnings("unused")
public class Which {

    public static ProcessResultCollection execute(String shellCommand) throws ProcessExecutionException {
        String[] command = {"which", shellCommand};
        try {
            return ProcessExecution.execute(command);
        } catch (ProcessExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsShellCommand(String shellCommand) throws ProcessExecutionException {
        ProcessResultCollection processResultCollection = execute(shellCommand);
        return processResultCollection.isSuccess() && !processResultCollection.getStandardOut().isEmpty();
    }

}
