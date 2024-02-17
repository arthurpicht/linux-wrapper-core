package de.arthurpicht.linuxWrapper;

import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.core.collection.Lists;
import de.arthurpicht.utils.core.strings.Strings;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Helper {

    @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "OptionalIsPresent"})
    public static void commandLogging(Optional<Logger> loggerOptional, boolean toConsole, String... command) {
        List<String> commandList = Arrays.asList(command);
        String commandString = "> " + Strings.listing(commandList, " ");
        if (loggerOptional.isPresent()) {
            loggerOptional.get().info(commandString);
        }
        if (toConsole) {
            System.out.println(commandString);
        }
    }

    public static void execute(String[] command, Loggable loggable) {
        ProcessResultCollection result;
        try {
            result = ProcessExecution.execute(
                    loggable.getLogger(),
                    loggable.isOutputToConsole(),
                    command);
        } catch (ProcessExecutionException e) {
            throw new RuntimeException("Process execution failed: " + e.getMessage(), e);
        }
        if (result.isFail()) throw createException(command, result);
    }

    public static LinuxWrapperCoreRuntimeException createException(String[] command, ProcessResultCollection result) {
        if (result.isSuccess()) throw new IllegalStateException("Process execution not failed.");
        String commandString = Strings.listing(Lists.newArrayList(command), ", ");
        if (!result.getErrorOut().isEmpty()) {
            return new LinuxWrapperCoreRuntimeException("Command exited with exit code: '" + commandString + "'. " + result.getErrorOut().get(0));
        } else {
            return new LinuxWrapperCoreRuntimeException("Command exited with exit code: '" + commandString + "'.");
        }
    }

}
