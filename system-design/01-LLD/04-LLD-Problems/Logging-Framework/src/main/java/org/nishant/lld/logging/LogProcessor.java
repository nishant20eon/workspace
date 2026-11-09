package org.nishant.lld.logging;

import java.util.Objects;

/** Chain of Responsibility: each processor delegates only if processing should continue. */
public abstract class LogProcessor {
    private LogProcessor next;

    public final LogProcessor linkWith(LogProcessor next) {
        this.next = Objects.requireNonNull(next);
        return next; // Allows first.linkWith(second).linkWith(third)
    }

    protected final void forward(LogEvent event) {
        if (next != null) next.process(event);
    }

    public abstract void process(LogEvent event);
}
