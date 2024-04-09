package de.arthurpicht.linuxWrapper;

import de.arthurpicht.processExecutor.*;
import de.arthurpicht.utils.core.collection.Lists;
import de.arthurpicht.utils.core.strings.Strings;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ExecutionHelper {

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
                    throw ExecutionHelper.createException(command, result);
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
                    throw ExecutionHelper.createException(command, result);
                } else {
                    return result;
                }
            }
        } catch (ProcessExecutionException e) {
            String commandString = Strings.listing(Lists.newArrayList(command), " ");
            throw new LinuxWrapperCoreRuntimeException("Process execution of '" + commandString + "' failed: " + e.getMessage(), e);
        }
    }

    public static ProcessResultCollection execute(InputStream inputStream, String[] command, AbstractWrapperConfig abstractWrapperConfig, boolean assertSuccess) {
        StandardOutHandler standardOutHandler = abstractWrapperConfig.getGeneralStandardOutHandler();
        StandardErrorHandler standardErrorHandler = abstractWrapperConfig.getGeneralStandardErrorHandler();
        try {
            ProcessResultCollection result = ProcessExecution.execute(inputStream, standardOutHandler, standardErrorHandler, command);
            if (result.isSuccess()) {
                return result;
            } else {
                if (assertSuccess) {
                    throw ExecutionHelper.createException(command, result);
                } else {
                    return result;
                }
            }
        } catch (ProcessExecutionException e) {
            String commandString = Strings.listing(Lists.newArrayList(command), " ");
            throw new LinuxWrapperCoreRuntimeException("Process execution of '" + commandString + "' failed: " + e.getMessage(), e);
        }
    }


    public static void executeInteractively(String[] command, boolean assertSuccess) {
        try {
            ProcessExecutor processExecutor = new ProcessExecutorBuilder()
                    .withCommands(command)
                    .asInteractive()
                    .build();
            processExecutor.execute();
            if (processExecutor.getExitCode() != 0 && assertSuccess)
                throw ExecutionHelper.createException(command, processExecutor.getExitCode());
        } catch (ProcessExecutionException e) {
            String commandString = Strings.listing(Lists.newArrayList(command), " ");
            throw new LinuxWrapperCoreRuntimeException("Process execution of '" + commandString + "' failed: " + e.getMessage(), e);
        }
    }

    private static LinuxWrapperCoreRuntimeException createException(String[] command, ProcessResultCollection result) {
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

    private static LinuxWrapperCoreRuntimeException createException(String[] command, int exitCode) {
        String commandString = Strings.listing(Lists.newArrayList(command), " ");
        return new LinuxWrapperCoreRuntimeException(
                "Command failed with exit code " + exitCode + ": [" + commandString + "].");
    }

}
