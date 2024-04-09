package de.arthurpicht.linuxWrapper.core.ps;

import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;

@SuppressWarnings("unused")
public class Ps {

    public static ProcessResultCollection execute(int processId) {
        String[] command = {"ps", "w", "--no-headers", Integer.toString(processId)};
        try {
            return ProcessExecution.execute(command);
        } catch (ProcessExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean noProcessForPidFound(ProcessResultCollection processResultCollection) {
        return processResultCollection.getExitCode() == 1
                && processResultCollection.getStandardOut().isEmpty()
                && processResultCollection.getErrorOut().isEmpty();
    }

}
