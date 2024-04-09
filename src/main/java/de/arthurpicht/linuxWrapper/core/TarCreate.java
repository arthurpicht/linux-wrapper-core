package de.arthurpicht.linuxWrapper.core;

import de.arthurpicht.processExecutor.*;
import de.arthurpicht.processExecutor.outputHandler.StandardErrorCollectionHandler;
import de.arthurpicht.processExecutor.outputHandler.StandardOutCollectionHandler;
import de.arthurpicht.utils.core.strings.Strings;
import de.arthurpicht.utils.io.nio2.FileUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("ALL")
public class TarCreate {

    private final boolean gzip;
    private final String source;
    private final String destination;
    private final String directory;
    private final Integer mode;
    private final String owner;
    private final String group;
    private final Path workingDir;
    private final StandardOutHandler standardOutHandler;
    private final StandardErrorHandler standardErrorHandler;
    private final List<String> command;

    public static class TarCreateBuilder {

        private boolean gzip = false;
        private String source = null;
        private String destination = null;
        private String directory = null;
        private Integer mode = null;
        private String owner = null;
        private String group = null;
        private Path workingDir = FileUtils.getWorkingDir();
        private StandardOutHandler standardOutHandler = new StandardOutCollectionHandler();
        private StandardErrorHandler standardErrorHandler = new StandardErrorCollectionHandler();

        /**
         * Use gzip compression. Default is false.
         */
        public TarCreateBuilder withGzip() {
            this.gzip = true;
            return this;
        }

        /**
         * Name of file or directory to be processed.
         */
        public TarCreateBuilder withSource(String source) {
            this.source = source;
            return this;
        }

        /**
         * Name of tar-file including .tar or .tar.gz
         */
        public TarCreateBuilder withDestination(String destination) {
            this.destination = destination;
            return this;
        }

        /**
         * Value of parameter "change to directory", "-C". Default: no such parameter.
         */
        public TarCreateBuilder changeToDirectory(String directory) {
            this.directory = directory;
            return this;
        }

        /**
         * Value of parameter "--mode". Default: no such parameter.
         */
        public TarCreateBuilder withMode(int mode) {
            this.mode = mode;
            return this;
        }

        /**
         * Value of parameter "--owner". Default: no such parameter.
         */
        public TarCreateBuilder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        /**
         * Value of parameter "--group". Default: no such parameter.
         */
        public TarCreateBuilder withGroup(String group) {
            this.group = group;
            return this;
        }

        /**
         * Perform execution in specified directory. Default: current working directory.
         */
        public TarCreateBuilder withWorkingDir(Path workingDir) {
            this.workingDir = workingDir;
            return this;
        }

        /**
         * Handle standard output of tar by specified handler. Default: StandardOutCollectionHandler.
         */
        public TarCreateBuilder withStandardOutHandler(StandardOutHandler standardOutHandler) {
            this.standardOutHandler = standardOutHandler;
            return this;
        }

        /**
         * Handle standard error output of tar by specified handler. Default: StandardErrorCollectionHandler
         */
        public TarCreateBuilder withStandardErrorHandler(StandardErrorHandler standardErrorHandler) {
            this.standardErrorHandler = standardErrorHandler;
            return this;
        }

        public TarCreate build() {
            if (Strings.isNullOrEmpty(this.source))
                throw new RuntimeException("No source specified for executing tar.");
            if (Strings.isNullOrEmpty(this.destination))
                throw new RuntimeException("No destination specified for executing tar.");
            return new TarCreate(
                    this.gzip,
                    this.source,
                    this.destination,
                    this.directory,
                    this.mode,
                    this.owner,
                    this.group,
                    this.workingDir,
                    this.standardOutHandler,
                    this.standardErrorHandler);
        }

    }

    private TarCreate(
            boolean gzip,
            String source,
            String destination,
            String directory,
            Integer mode,
            String owner,
            String group,
            Path workingDir,
            StandardOutHandler standardOutHandler,
            StandardErrorHandler standardErrorHandler) {

        this.gzip = gzip;
        this.source = source;
        this.destination = destination;
        this.directory = directory;
        this.mode = mode;
        this.owner = owner;
        this.group = group;
        this.workingDir = workingDir;
        this.standardOutHandler = standardOutHandler;
        this.standardErrorHandler = standardErrorHandler;

        this.command = buildCommand();
    }

    public ProcessResultCollection execute() throws ProcessExecutionException {

        ProcessExecutor processExecutor = new ProcessExecutorBuilder()
                .withCommands(this.command.toArray(new String[0]))
                .withWorkingDirectory(this.workingDir)
                .withStandardOutHandler(this.standardOutHandler)
                .withStandardErrorHandler(this.standardErrorHandler)
                .build();
        processExecutor.execute();
        return new ProcessResultCollection(processExecutor, this.standardOutHandler, this.standardErrorHandler);
    }

    public List<String> getCommand() {
        return this.command;
    }

    public String getCommandAsString() {
        return Strings.listing(this.command, " ");
    }

    private List<String> buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("tar");
        if (this.gzip) {
            command.add("-czf");
        } else {
            command.add("-cf");
        }
        command.add(this.destination);
        if (this.directory != null) {
            command.add("-C");
            command.add(this.directory);
        }
        if (this.mode != null) {
            command.add("--mode");
            command.add(this.mode.toString());
        }
        if (this.owner != null) {
            command.add("--owner");
            command.add(this.owner);
        }
        if (this.group != null) {
            command.add("--group");
            command.add(this.group);
        }
        command.add(this.source);
        return Collections.unmodifiableList(command);
    }

}
