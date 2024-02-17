package de.arthurpicht.linuxWrapper.ssh;

import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.provisioning.ExecutionContext;
import org.mentalizr.provisioning.FileHelper;
import org.mentalizr.provisioning.ProvisioningConst;
import org.mentalizr.provisioning.commandExecutors.ProcessExecutionHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SshKeyPair {

    private static final Path sshDir = FileUtils.getHomeDir().resolve(".ssh");

    public static void delete() {
        deleteKey(getPrivateKey());
        deleteKey(getPublicKey());
    }

    public static boolean exists() {
        return FileUtils.isExistingRegularFile(getPrivateKey()) && FileUtils.isExistingRegularFile(getPublicKey());
    }

    public static boolean notExists() {
        return !exists();
    }

    private static void deleteKey(Path keyPath) {
        try {
            if (FileUtils.isExistingRegularFile(keyPath))
                Files.delete(keyPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete [" + keyPath.toAbsolutePath() + "].");
        }
    }

    private static Path getPrivateKey() {
        return getSshDir().resolve(ProvisioningConst.KEY_NAME);
    }

    public static Path getPublicKey() {
        return getSshDir().resolve(ProvisioningConst.KEY_NAME + ".pub");
    }

    private static Path getSshDir() {
        return FileHelper.getHomeDir().resolve(".ssh");
    }

}
