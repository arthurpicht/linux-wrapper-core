package de.arthurpicht.linuxWrapper.ssh.keyPairCreate;

import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.nio.file.Path;

@SuppressWarnings({"unused"})
public class SshKeyPairCreateBuilder {

    private SshKeyPairCreateConfig.KeyType keyType = SshKeyPairCreateConfig.KeyType.ED25519;
    private Integer length = null;
    private String comment = null;
    private String password = "";
    private Logger logger = null;
    private Level logLevelStd = Level.INFO;
    private Level logLevelError = Level.ERROR;
    private boolean outputToConsole = false;

    /**
     * Type of SSH key. Default: ED25519.
     */
    public SshKeyPairCreateBuilder withType(SshKeyPairCreateConfig.KeyType keyType) {
        this.keyType = keyType;
        return this;
    }

    /**
     * Key length. There is no need to set the key length for Ed25519, as all Ed25519 keys are 256 bits.
     */
    public SshKeyPairCreateBuilder withLength(int length) {
        this.length = length;
        return this;
    }

    /**
     * Default: No comment.
     */
    public SshKeyPairCreateBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Default: empty.
     */
    public SshKeyPairCreateBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Default: No logging.
     */
    public SshKeyPairCreateBuilder withLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    /**
     * Default: INFO
     */
    public SshKeyPairCreateBuilder withLogLevelStd(Level logLevelStd) {
        this.logLevelStd = logLevelStd;
        return this;
    }

    /**
     * Default: ERROR
     */
    public SshKeyPairCreateBuilder withLogLevelError(Level logLevelError) {
        this.logLevelError = logLevelError;
        return this;
    }

    /**
     * Default: No output to console.
     */
    public SshKeyPairCreateBuilder withOutputToConsole(boolean outputToConsole) {
        this.outputToConsole = outputToConsole;
        return this;
    }

    /**
     * Builds SshKeyParCrate instance by specified key path. Key path must be absolute and must denote the
     * private key. E.g.: /my/path/key will generate /my/path/key and /my/path/key.pub.
     *
     * @param keyPath absolute path of private key to be generated
     * @return SshKeyPairCrate instance
     */
    public SshKeyPairCreate build(Path keyPath) {
        if (!keyPath.isAbsolute())
            throw new IllegalArgumentException(
                    "Specified key path must be absolute: [" + keyPath.toAbsolutePath() + "].");

        SshKeyPairCreateConfig config = new SshKeyPairCreateConfig(
                keyPath,
                this.keyType,
                this.length,
                this.comment,
                this.password,
                this.logger,
                this.logLevelStd,
                this.logLevelError,
                this.outputToConsole
        );

        return new SshKeyPairCreate(config);
    }

}
