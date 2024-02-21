package de.arthurpicht.linuxWrapper.ssh.knownHosts;

import de.arthurpicht.linuxWrapper.Helper;
import de.arthurpicht.linuxWrapper.LinuxWrapperCoreRuntimeException;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.io.file.TextFileUtils;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("JavadocDeclaration")
public class KnownHosts {

    /**
     * Checks if known_hosts file contains host.
     */
    public static boolean containsHost(KnownHostsConfig knownHostsConfig) {
        if (!FileUtils.isExistingRegularFile(knownHostsConfig.getKnownHostsFile()))
            throw new IllegalArgumentException("Specified known_hosts file not existing: " +
                                               "[" + knownHostsConfig.getKnownHostsFile() + "].");
        String[] command = {
                "ssh-keygen",
                "-f", knownHostsConfig.getKnownHostsFile().toString(),
                "-F", knownHostsConfig.getHost()
        };
        Helper.commandLogging(command, knownHostsConfig);
        ProcessResultCollection result = Helper.execute(command, knownHostsConfig, false);
        return result.isSuccess() && !result.getStandardOut().isEmpty();
    }

    /**
     * Removes host from known_host file.
     * Caution: If called for a known_hosts file that does not contain host, then a LinuxWrapperCoreRuntimeException
     * is thrown.
     *
     * @param knownHostsConfig
     * @throws LinuxWrapperCoreRuntimeException
     */
    public static void removeHost(KnownHostsConfig knownHostsConfig) throws LinuxWrapperCoreRuntimeException {
        if (!FileUtils.isExistingRegularFile(knownHostsConfig.getKnownHostsFile()))
            throw new IllegalArgumentException("Specified known_hosts file not existing: " +
                                               "[" + knownHostsConfig.getKnownHostsFile() + "].");
        String[] command = {
                "ssh-keygen",
                "-f", knownHostsConfig.getKnownHostsFile().toString(),
                "-R", knownHostsConfig.getHost()
        };
        Helper.commandLogging(command, knownHostsConfig);
        Helper.execute(command, knownHostsConfig, true);
    }

    /**
     * Adds host to known_hosts file. known_hosts file will be created if not yet existing. Hosts must be up and
     * reachable as its public key file is obtained by ssh-keyscan.
     *
     * @param knownHostsConfig
     * @throws LinuxWrapperCoreRuntimeException
     */
    public static void addHost(KnownHostsConfig knownHostsConfig) throws LinuxWrapperCoreRuntimeException {
        String[] command = {
                "ssh-keyscan",
                "-H", knownHostsConfig.getHost(),
        };
        Helper.commandLogging(command, knownHostsConfig);
        ProcessResultCollection result = Helper.execute(command, knownHostsConfig, true);
        List<String> linesToAdd = result.getStandardOut();
        try {
            TextFileUtils.appendLines(knownHostsConfig.getKnownHostsFile(), linesToAdd);
        } catch (IOException e) {
            throw new LinuxWrapperCoreRuntimeException(
                    "Error on appending lines to [" + knownHostsConfig.getKnownHostsFile().toAbsolutePath() + "]: "
                    + e.getMessage(), e);
        }

    }

}
