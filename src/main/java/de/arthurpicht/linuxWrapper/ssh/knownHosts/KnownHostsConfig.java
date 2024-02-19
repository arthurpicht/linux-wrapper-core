package de.arthurpicht.linuxWrapper.ssh.knownHosts;

import de.arthurpicht.linuxWrapper.LoggingConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.nio.file.Path;

public class KnownHostsConfig extends LoggingConfig {

    private final String host;
    private final Path knownHostsFile;

    public KnownHostsConfig(String host, Path knownHostsFile, Logger logger, Level logLevelStd, Level logLevelError, boolean outputToConsole) {
        super(logger, logLevelStd, logLevelError, outputToConsole);
        this.host = host;
        this.knownHostsFile = knownHostsFile;
    }

    public String getHost() {
        return host;
    }

    public Path getKnownHostsFile() {
        return knownHostsFile;
    }

}
