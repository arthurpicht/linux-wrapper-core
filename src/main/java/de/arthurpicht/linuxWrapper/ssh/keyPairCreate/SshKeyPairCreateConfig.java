package de.arthurpicht.linuxWrapper.ssh.keyPairCreate;

import de.arthurpicht.linuxWrapper.AbstractWrapperConfig;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.nio.file.Path;

public class SshKeyPairCreateConfig extends AbstractWrapperConfig {

    public enum KeyType { ED25519, RSA}

    private final Path keyPath;
    private final KeyType keyType;
    private final Integer length;
    private final String comment;
    private final String password;

    public SshKeyPairCreateConfig(
            Path keyPath,
            KeyType keyType,
            Integer length,
            String comment,
            String password,
            Logger logger,
            Level logLevelStd,
            Level logLevelError,
            boolean outputToConsole) {

        super(logger, logLevelStd, logLevelError, outputToConsole);
        this.keyPath = keyPath;
        this.keyType = keyType;
        this.length = length;
        this.comment = comment;
        this.password = password;

        if (this.keyType == KeyType.ED25519 && this.length != null)
            throw new IllegalArgumentException("No specification of key length allowed for keys of type "
                                               + KeyType.ED25519 + ".");
    }

    public Path getKeyPath() {
        return keyPath;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public Integer getLength() {
        return length;
    }

    public String getComment() {
        return comment;
    }

    public String getPassword() {
        return password;
    }

}
