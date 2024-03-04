package de.arthurpicht.linuxWrapper;

import de.arthurpicht.processExecutor.*;
import de.arthurpicht.utils.core.collection.Lists;
import de.arthurpicht.utils.core.strings.Strings;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Helper {

    public static void commandLogging(String[] command, AbstractWrapperConfig abstractWrapperConfig) {
        commandLogging("", command, abstractWrapperConfig);
    }

    public static void commandLogging(String workingDir, String[] command, AbstractWrapperConfig abstractWrapperConfig) {
        List<String> commandList = Arrays.asList(command);
        String commandString = workingDir + "> " + Strings.listing(commandList, " ");
        if (abstractWrapperConfig.hasLogger()) {
            abstractWrapperConfig.logger.atLevel(abstractWrapperConfig.getLogLevelStd()).log(commandString);
        }
        if (abstractWrapperConfig.outputToConsole) {
            System.out.println(commandString);
        }
    }

    public static ProcessResultCollection execute(String[] command, AbstractWrapperConfig abstractWrapperConfig, boolean assertSuccess) {
        StandardOutHandler standardOutHandler = abstractWrapperConfig.getGeneralStandardOutHandler();
        StandardErrorHandler standardErrorHandler = abstractWrapperConfig.getGeneralStandardErrorHandler();
        try {
            ProcessResultCollection result = ProcessExecution.execute(standardOutHandler, standardErrorHandler, command);
            if (result.isSuccess()) {
                return result;
            } else {
                if (assertSuccess) {
                    throw Helper.createException(command, result);
                } else {
                    return result;
                }
            }
        } catch (ProcessExecutionException e) {
            String commandString = Strings.listing(Lists.newArrayList(command), " ");
            throw new LinuxWrapperCoreRuntimeException("Process execution of '" + commandString + "' failed: " + e.getMessage(), e);
        }
    }

    public static ProcessResultCollection execute(Path workingDir, String[] command, AbstractWrapperConfig abstractWrapperConfig, boolean assertSuccess) {
        StandardOutHandler standardOutHandler = abstractWrapperConfig.getGeneralStandardOutHandler();
        StandardErrorHandler standardErrorHandler = abstractWrapperConfig.getGeneralStandardErrorHandler();
        try {
            ProcessExecutor processExecutor = new ProcessExecutorBuilder()
                    .withWorkingDirectory(workingDir)
                    .withCommands(command)
                    .withStandardOutHandler(standardOutHandler)
                    .withStandardErrorHandler(standardErrorHandler)
                    .build();
            processExecutor.execute();
            ProcessResultCollection result = new ProcessResultCollection(processExecutor, standardOutHandler, standardErrorHandler);
            if (result.isSuccess()) {
                return result;
            } else {
                if (assertSuccess) {
                    throw Helper.createException(command, result);
                } else {
                    return result;
                }
            }
        } catch (ProcessExecutionException e) {
            String commandString = Strings.listing(Lists.newArrayList(command), " ");
            throw new LinuxWrapperCoreRuntimeException("Process execution of '" + commandString + "' failed: " + e.getMessage(), e);
        }
    }

    public static LinuxWrapperCoreRuntimeException createException(String[] command, ProcessResultCollection result) {
        if (result.isSuccess()) throw new IllegalStateException("Process execution not failed.");
        String commandString = Strings.listing(Lists.newArrayList(command), " ");
        if (!result.getErrorOut().isEmpty()) {
            return new LinuxWrapperCoreRuntimeException(
                    "Command failed with exit code " + result.getExitCode() + ": [" + commandString + "]. "
                    + result.getErrorOut().get(0));
        } else {
            return new LinuxWrapperCoreRuntimeException(
                    "Command failed with exit code " + result.getExitCode() + ": [" + commandString + "].");
        }
    }

}
