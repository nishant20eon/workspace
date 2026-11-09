package org.nishant.lld.logging;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/** ISO-8601 UTC timestamp with severity, logger, thread and message. */
public final class PlainTextFormatter implements LogFormatter {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ISO_INSTANT;

    @Override
    public String format(LogEvent e) {
        return "%s [%s] [%s] [%s] %s".formatted(
                TIME.format(e.timestamp()), e.level(), e.threadName(),
                e.loggerName(), e.message());
    }
}
