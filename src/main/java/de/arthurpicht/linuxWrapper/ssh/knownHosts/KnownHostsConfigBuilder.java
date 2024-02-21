package de.arthurpicht.linuxWrapper.ssh.knownHosts;

import de.arthurpicht.utils.io.nio2.FileUtils;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.nio.file.Path;

public class KnownHostsConfigBuilder {

    private Path knownHostsFile = FileUtils.getHomeDir().resolve(".ssh/known_hosts").toAbsolutePath();
    private Logger logger = null;
    private Level logLevelStd = Level.INFO;
    private Level logLevelError = Level.ERROR;
    private boolean outputToConsole = false;

    public KnownHostsConfigBuilder withKnownHostsFile(Path knownHostsFile) {
        this.knownHostsFile = knownHostsFile;
        return this;
    }

    public KnownHostsConfigBuilder withLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public KnownHostsConfigBuilder withLogLevelStd(Level logLevelStd) {
        this.logLevelStd = logLevelStd;
        return this;
    }

    public KnownHostsConfigBuilder withLogLevelError(Level logLevelError) {
        this.logLevelError = logLevelError;
        return this;
    }

    public KnownHostsConfigBuilder withOutputToConsole(boolean outputToConsole) {
        this.outputToConsole = outputToConsole;
        return this;
    }

    public KnownHostsConfig build(String host) {
        return new KnownHostsConfig(host, knownHostsFile, logger, logLevelStd, logLevelError, outputToConsole);
    }

}