package org.nishant.lld.logging;

import java.time.Clock;
import java.util.Objects;

/** Client-facing facade. It creates events; the chain handles filtering and output. */
public final class Logger implements AutoCloseable {
    private final String name;
    private final Clock clock;
    private final LevelFilterProcessor filter;
    private final AppenderProcessor dispatcher;

    public Logger(String name, LogLevel minimum, Clock clock, LogFormatter formatter) {
        this.name = Objects.requireNonNull(name);
        this.clock = Objects.requireNonNull(clock);
        filter = new LevelFilterProcessor(minimum);
        dispatcher = new AppenderProcessor(formatter);
        filter.linkWith(dispatcher);
    }

    public void addAppender(LogAppender appender) { dispatcher.addAppender(appender); }
    public void removeAppender(LogAppender appender) { dispatcher.removeAppender(appender); }
    public void setLevel(LogLevel minimum) { filter.setMinimum(minimum); }

    public void log(LogLevel level, String message) {
        Objects.requireNonNull(level);
        Objects.requireNonNull(message);
        // Skip even constructing an event if it is filtered out.
        if (!level.isEnabledFor(filter.minimum())) return;
        filter.process(new LogEvent(clock.instant(), level, name,
                Thread.currentThread().getName(), message));
    }

    public void debug(String message) { log(LogLevel.DEBUG, message); }
    public void info(String message)  { log(LogLevel.INFO, message); }
    public void warn(String message)  { log(LogLevel.WARN, message); }
    public void error(String message) { log(LogLevel.ERROR, message); }

    @Override
    public void close() { dispatcher.close(); }
}
