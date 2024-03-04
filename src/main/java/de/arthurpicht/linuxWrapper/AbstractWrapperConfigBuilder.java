package de.arthurpicht.linuxWrapper;

import org.slf4j.Logger;
import org.slf4j.event.Level;

public class AbstractWrapperConfigBuilder<T extends AbstractWrapperConfigBuilder<T>> {

    protected Logger logger = null;
    protected Level logLevelStd = Level.INFO;
    protected Level logLevelError = Level.ERROR;
    protected boolean outputToConsole = false;

    /**
     * Default: No logging.
     */
    public T withLogger(Logger logger) {
        this.logger = logger;
        return getThis();
    }

    /**
     * Default: INFO
     */
    public T withLogLevelStd(Level logLevelStd) {
        this.logLevelStd = logLevelStd;
        return getThis();
    }

    /**
     * Default: ERROR
     */
    public T withLogLevelError(Level logLevelError) {
        this.logLevelError = logLevelError;
        return getThis();
    }

    /**
     * Default: No output to console.
     */
    public T withOutputToConsole(boolean outputToConsole) {
        this.outputToConsole = outputToConsole;
        return getThis();
    }

    @SuppressWarnings("unchecked")
    public T getThis() {
        return (T) this;
    }

}
