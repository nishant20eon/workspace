package org.nishant.lld.logging;

import java.time.Instant;
import java.util.Objects;

/** Immutable message passed through the logging pipeline. */
public record LogEvent(Instant timestamp, LogLevel level, String loggerName,
                       String threadName, String message) {
    public LogEvent {
        Objects.requireNonNull(timestamp, "timestamp");
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(loggerName, "loggerName");
        Objects.requireNonNull(threadName, "threadName");
        Objects.requireNonNull(message, "message");
    }
}
