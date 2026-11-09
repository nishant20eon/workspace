package org.nishant.lld.logging;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/** First chain step: discard events below the configured severity. */
public final class LevelFilterProcessor extends LogProcessor {
    private final AtomicReference<LogLevel> minimum;

    public LevelFilterProcessor(LogLevel initialLevel) {
        this.minimum = new AtomicReference<>(Objects.requireNonNull(initialLevel));
    }

    public void setMinimum(LogLevel level) {
        minimum.set(Objects.requireNonNull(level));
    }

    public LogLevel minimum() { return minimum.get(); }

    @Override
    public void process(LogEvent event) {
        if (event.level().isEnabledFor(minimum.get())) forward(event);
        // Otherwise stop the chain: the event is filtered out.
    }
}
