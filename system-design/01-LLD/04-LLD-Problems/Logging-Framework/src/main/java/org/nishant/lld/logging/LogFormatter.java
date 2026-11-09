package org.nishant.lld.logging;

/** Strategy: determines how a LogEvent becomes output text. */
@FunctionalInterface
public interface LogFormatter {
    String format(LogEvent event);
}
