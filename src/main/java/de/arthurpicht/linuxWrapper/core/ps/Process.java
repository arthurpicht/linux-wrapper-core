package de.arthurpicht.linuxWrapper.core.ps;

public record Process(int pid, String tty, String stat, String time, String command) {
}
