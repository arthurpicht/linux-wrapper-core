package de.arthurpicht.linuxWrapper;

import org.slf4j.Logger;

public interface Loggable {

    boolean hasLogger();

    Logger getLogger();

    boolean isOutputToConsole();

}
