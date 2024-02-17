package de.arthurpicht.linuxWrapper.ssh;

import de.arthurpicht.linuxWrapper.Helper;
import de.arthurpicht.linuxWrapper.Loggable;
import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.core.strings.Strings;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SshKeyPairCreate implements Loggable {

    public enum Type { ED25519, RSA}

    private final Path keyPath;
    private final Type type;
    private final Integer length;
    private final String comment;
    private final String password;
    private final Logger logger;
    private final boolean outputToConsole;

    private static final Path sshDir = FileUtils.getHomeDir().resolve(".ssh");

    public static class Builder {

        private Type type = Type.ED25519;
        private Integer length = null;
        private String comment = null;
        private String password = "";
        private Logger logger = null;
        private boolean outputToConsole = false;

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        /**
         * Key length. There is no need to set the key length for Ed25519, as all Ed25519 keys are 256 bits.
         */
        public Builder withLength(int length) {
            this.length = length;
            return this;
        }

        public Builder withComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withLogger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public Builder withOutputToConsole() {
            this.outputToConsole = true;
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

            return new SshKeyPairCreate(
                    keyPath,
                    this.type,
                    this.length,
                    this.comment,
                    this.password,
                    this.logger,
                    this.outputToConsole
            );
        }
    }

    public SshKeyPairCreate(
            Path keyPath,
            Type type,
            Integer length,
            String comment,
            String password,
            Logger logger,
            boolean outputToConsole
    ) {
        this.keyPath = keyPath;
        this.type = type;
        this.length = length;
        this.comment = comment;
        this.password = password;
        this.logger = logger;
        this.outputToConsole = outputToConsole;

        if (this.type == Type.ED25519 && this.length != null)
            throw new IllegalArgumentException("No specification of key length allowed for keys of type " + Type.ED25519 + ".");
    }

    @Override
    public boolean hasLogger() {
        return this.logger != null;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public boolean isOutputToConsole() {
        return this.outputToConsole;
    }

    public void create() {
        assureSshDir();
        String[] command = buildCommand();
        Helper.commandLogging(Optional.of(this.logger), this.outputToConsole, command);
        Helper.execute(command, this);
    }

    private String[] buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("ssh-keygen");
        command.add("-t");
        command.add(this.type.name());
        if (this.length != null) {
            command.add("-b");
            command.add(this.length.toString());
        }
        if (this.comment != null) {
            command.add("-C");
            command.add("\"" + this.comment + "\"");
        }
        command.add("-N");
        command.add(password);
        command.add("-f");
        command.add(this.keyPath.toString());
        return Strings.toArray(command);
    }

    private void assureSshDir() {
        if (!FileUtils.isExistingDirectory(sshDir)) {
            try {
                Files.createDirectory(sshDir);
            } catch (IOException e) {
                throw new RuntimeException("Error on creating dir [" + sshDir + "]. " + e.getMessage(), e);
            }
        }
    }

}
