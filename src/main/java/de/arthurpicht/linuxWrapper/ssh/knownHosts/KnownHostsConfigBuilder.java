package de.arthurpicht.linuxWrapper.ssh.knownHosts;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfigBuilder;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;

public class KnownHostsConfigBuilder extends AbstractWrapperConfigBuilder {

    private Path knownHostsFile = FileUtils.getHomeDir().resolve(".ssh/known_hosts").toAbsolutePath();

    public KnownHostsConfigBuilder withKnownHostsFile(Path knownHostsFile) {
        this.knownHostsFile = knownHostsFile;
        return this;
    }

    public KnownHostsConfig build(String host) {
        return new KnownHostsConfig(
                host,
                this.knownHostsFile,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );
    }

}