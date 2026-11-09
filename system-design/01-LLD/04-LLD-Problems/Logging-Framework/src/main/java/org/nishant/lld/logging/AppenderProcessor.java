package org.nishant.lld.logging;

import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/** Last chain step: fans an accepted event out to all configured destinations. */
public final class AppenderProcessor extends LogProcessor implements AutoCloseable {
    private final CopyOnWriteArrayList<LogAppender> appenders = new CopyOnWriteArrayList<>();
    private final LogFormatter formatter;

    public AppenderProcessor(LogFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter);
    }

    public void addAppender(LogAppender appender) {
        appenders.addIfAbsent(Objects.requireNonNull(appender));
    }

    public void removeAppender(LogAppender appender) { appenders.remove(appender); }

    @Override
    public void process(LogEvent event) {
        for (LogAppender appender : appenders) appender.append(event, formatter);
        forward(event);
    }

    @Override
    public void close() {
        for (LogAppender appender : appenders) appender.close();
    }
}
