package org.nishant.lld.logging;

/** Ordered severities: a threshold includes this level and all more severe levels. */
public enum LogLevel {
    TRACE, DEBUG, INFO, WARN, ERROR;

    public boolean isEnabledFor(LogLevel minimum) {
        return this.ordinal() >= minimum.ordinal();
    }
}
