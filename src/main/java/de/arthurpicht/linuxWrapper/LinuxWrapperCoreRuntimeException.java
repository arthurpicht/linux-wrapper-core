package de.arthurpicht.linuxWrapper;

import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.utils.core.strings.Strings;

import java.util.Arrays;

public class LinuxWrapperCoreRuntimeException extends RuntimeException {

    public LinuxWrapperCoreRuntimeException(String message) {
        super(message);
    }

    public LinuxWrapperCoreRuntimeException(ProcessExecutionException cause) {
        super(cause);
    }

    public LinuxWrapperCoreRuntimeException(String message, ProcessExecutionException cause) {
        super(message, cause);
    }

    public LinuxWrapperCoreRuntimeException(String[] command, ProcessExecutionException cause) {
        super("Error executing command: '" + Strings.listing(Arrays.asList(command), ", " + "': " + cause.getMessage()), cause);
    }

}
