package de.arthurpicht.linuxWrapper.ssh;

import de.arthurpicht.linuxWrapper.LinuxWrapperCoreRuntimeException;
import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;

/**
 * Some usual operations on known_hosts file.
 */
@SuppressWarnings("unused")
public class KnownHosts {

    private static final Path knownHostsFileDefault
            = FileUtils.getHomeDir().resolve(".ssh/known_hosts").toAbsolutePath();

    /**
     * Checks if default known_hosts file contains specified host.
     *
     * @param host host to be checked
     * @return if known_hosts contains host
     * @throws LinuxWrapperCoreRuntimeException if processExecution fails
     */
    public static boolean containsHost(String host) {
        if (!FileUtils.isExistingRegularFile(knownHostsFileDefault)) return false;
        return containsHostProcess(knownHostsFileDefault, host);
    }

    /**
     * Checks if specified known_hosts files contains specified host.
     *
     * @param host host to be checked
     * @return if known_hosts contains host
     * @throws LinuxWrapperCoreRuntimeException if processExecution fails
     */
    public static boolean containsHost(Path knownHostsFile, String host) {
        if (!FileUtils.isExistingRegularFile(knownHostsFile))
            throw new IllegalArgumentException("Specified known_hosts file not existing: " +
                                               "[" + knownHostsFile.toAbsolutePath() + "].");
        return containsHostProcess(knownHostsFile, host);
    }

    private static boolean containsHostProcess(Path knownHostsFile, String host) {
        String[] command = {"ssh-keygen", "-f", "\"" + knownHostsFile + "\"", "-F", host};
        try {
            ProcessResultCollection result = ProcessExecution.execute(command);
            return result.isSuccess() && !result.getStandardOut().isEmpty();
        } catch (ProcessExecutionException e) {
            throw new LinuxWrapperCoreRuntimeException(command, e);
        }
    }

    /**
     * Remove specified host from default known_hosts file.
     *
     * @param host host to be removed
     * @throws LinuxWrapperCoreRuntimeException if processExecution fails
     */
    public static void remove(String host) throws LinuxWrapperCoreRuntimeException {
        if (!FileUtils.isExistingRegularFile(knownHostsFileDefault)) return;
        removeProcess(knownHostsFileDefault, host);
    }

    /**
     * Remove specified host from specified known_hosts file.
     *
     * @param host host to be removed
     * @throws LinuxWrapperCoreRuntimeException if processExecution fails
     */
    public static void remove(Path knownHostsFile, String host) throws LinuxWrapperCoreRuntimeException {
        if (!FileUtils.isExistingRegularFile(knownHostsFile))
            throw new IllegalArgumentException("Specified known_hosts file not existing: " +
                                               "[" + knownHostsFile.toAbsolutePath() + "].");
        removeProcess(knownHostsFile, host);
    }

    private static void removeProcess(Path knownHostsFile, String host) throws LinuxWrapperCoreRuntimeException {
        String[] command = {"ssh-keygen", "-f", "\"" + knownHostsFile + "\"", "-R", "\"" + host + "\""};
        try {
            ProcessExecution.execute(command);
        } catch (ProcessExecutionException e) {
            throw new LinuxWrapperCoreRuntimeException(command, e);
        }
    }

    /**
     * Add specified host to default known_hosts file. Host must be up and reachable as its public key file is obtained
     * by ssh-keyscan.
     *
     * @param host host to be added
     * @throws LinuxWrapperCoreRuntimeException if processExecution fails
     */
    public void add(String host) throws LinuxWrapperCoreRuntimeException {
        addProcess(knownHostsFileDefault, host);
    }

    /**
     * Add specified host to specified known_hosts file. Host must be up and reachable as its public key file is
     * obtained by ssh-keyscan.
     *
     * @param host host to be added
     * @throws LinuxWrapperCoreRuntimeException if processExecution fails
     */
    public void add(Path knownHostsFile, String host) throws LinuxWrapperCoreRuntimeException {
        addProcess(knownHostsFile, host);
    }

    private static void addProcess(Path knownHostsFile, String host) throws LinuxWrapperCoreRuntimeException {
        String[] command = {"ssh-keyscan", "-H", "\"" + host + "\"", ">>", knownHostsFile.toString()};
        try {
            ProcessExecution.execute(command);
        } catch (ProcessExecutionException e) {
            throw new LinuxWrapperCoreRuntimeException(command, e);
        }
    }

}
