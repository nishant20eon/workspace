package org.nishant.lld.logging;

/** Strategy: decides where formatted log lines go. */
public interface LogAppender extends AutoCloseable {
    void append(LogEvent event, LogFormatter formatter);

    @Override
    default void close() { /* Most appenders do not own long-lived resources. */ }
}
