package de.arthurpicht.linuxWrapper;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public abstract class AbstractWrapperConfigBuilder {

    protected Logger logger = null;
    protected Level logLevelStd = Level.INFO;
    protected Level logLevelError = Level.ERROR;
    protected boolean outputToConsole = false;

    /**
     * Default: No logging.
     */
    public AbstractWrapperConfigBuilder withLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    /**
     * Default: INFO
     */
    public AbstractWrapperConfigBuilder withLogLevelStd(Level logLevelStd) {
        this.logLevelStd = logLevelStd;
        return this;
    }

    /**
     * Default: ERROR
     */
    public AbstractWrapperConfigBuilder withLogLevelError(Level logLevelError) {
        this.logLevelError = logLevelError;
        return this;
    }

    /**
     * Default: No output to console.
     */
    public AbstractWrapperConfigBuilder withOutputToConsole(boolean outputToConsole) {
        this.outputToConsole = outputToConsole;
        return this;
    }

}
