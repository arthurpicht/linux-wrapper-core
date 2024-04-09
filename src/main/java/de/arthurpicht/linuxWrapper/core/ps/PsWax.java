package de.arthurpicht.linuxWrapper.core.ps;

import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PsWax {

    public static List<Process> execute() {
        ProcessResultCollection processResultCollection = executePsWax();

        List<String> stdOut = processResultCollection.getStandardOut();
        List<Process> processes = new ArrayList<>();
        for (String stdOutLine : stdOut) {
            Process process = ProcessHelper.parsePsString(stdOutLine);
            processes.add(process);
        }

        return processes;
    }

    private static ProcessResultCollection executePsWax() {
        String[] command = {"ps", "wax", "--no-headers"};
        ProcessResultCollection processResultCollection;
        try {
            processResultCollection = ProcessExecution.execute(command);
        } catch (ProcessExecutionException e) {
            throw new RuntimeException(e);
        }
        if (processResultCollection.isFail()) throw new RuntimeException("Executing 'ps wax' failed.");
        return processResultCollection;
    }

}
