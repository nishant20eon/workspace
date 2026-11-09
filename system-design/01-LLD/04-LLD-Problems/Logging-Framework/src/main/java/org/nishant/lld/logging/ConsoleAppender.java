package org.nishant.lld.logging;

import java.io.PrintStream;
import java.util.Objects;

/** Synchronizes writing to avoid mixing one line with another. */
public final class ConsoleAppender implements LogAppender {
    private final PrintStream output;

    public ConsoleAppender(PrintStream output) {
        this.output = Objects.requireNonNull(output);
    }

    @Override
    public void append(LogEvent event, LogFormatter formatter) {
        synchronized (output) {
            output.println(formatter.format(event));
        }
    }
}
