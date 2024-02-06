package de.arthurpicht.linuxWrapper.core.ps;

import de.arthurpicht.utils.core.strings.Strings;

public class ProcessHelper {

    public static Process parsePsString(String psString) {
        psString = psString.trim();

        String[] splitResult = Strings.splitAtDelimiter(psString, " ");
        int pid = Integer.parseInt(splitResult[0].trim());
        psString = splitResult[1].trim();

        splitResult = Strings.splitAtDelimiter(psString, " ");
        String tty = splitResult[0].trim();
        psString = splitResult[1].trim();

        splitResult = Strings.splitAtDelimiter(psString, " ");
        String stat = splitResult[0].trim();
        psString = splitResult[1].trim();

        splitResult = Strings.splitAtDelimiter(psString, " ");
        String time = splitResult[0].trim();
        String command = splitResult[1].trim();

        return new Process(pid, tty, stat, time, command);
    }

}
