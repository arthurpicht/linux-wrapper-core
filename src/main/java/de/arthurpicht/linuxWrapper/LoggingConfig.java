package de.arthurpicht.linuxWrapper;

import de.arthurpicht.processExecutor.outputHandler.generalOutputHandler.GeneralStandardErrorHandler;
import de.arthurpicht.processExecutor.outputHandler.generalOutputHandler.GeneralStandardOutHandler;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public abstract class LoggingConfig {

    protected final Logger logger;
    protected final Level logLevelStd;
    protected final Level logLevelError;
    protected final boolean outputToConsole;

    public LoggingConfig(Logger logger, Level logLevelStd, Level logLevelError, boolean outputToConsole) {
        this.logger = logger;
        this.logLevelStd = logLevelStd;
        this.logLevelError = logLevelError;
        this.outputToConsole = outputToConsole;
    }

    public boolean hasLogger() {
        return this.logger != null;
    }

    public Logger getLogger() {
        if (this.logger == null) throw new IllegalStateException("Logger is null. Check before requesting.");
        return this.logger;
    }

    public Level getLogLevelStd() {
        return this.logLevelStd;
    }

    public Level getLogLevelError() {
        return logLevelError;
    }

    public boolean isOutputToConsole() {
        return this.outputToConsole;
    }

    public GeneralStandardOutHandler getGeneralStandardOutHandler() {
        GeneralStandardOutHandler.Builder builder = new GeneralStandardOutHandler.Builder();
        if (hasLogger()) {
            builder.withLogger(this.logger);
            builder.withLogLevel(this.logLevelStd);
        }
        if (outputToConsole) {
            builder.withConsoleOutput();
        }
        return builder.build();
    }

    public GeneralStandardErrorHandler getGeneralStandardErrorHandler() {
        GeneralStandardErrorHandler.Builder builder = new GeneralStandardErrorHandler.Builder();
        if (hasLogger()) {
            builder.withLogger(this.logger);
            builder.withLogLevel(this.logLevelError);
        }
        if (outputToConsole) {
            builder.withConsoleOutput();
        }
        return builder.build();
    }

}
