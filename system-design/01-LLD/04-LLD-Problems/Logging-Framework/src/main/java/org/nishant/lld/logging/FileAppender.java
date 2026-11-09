package org.nishant.lld.logging;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/** Minimal, synchronous file appender. Each append writes one UTF-8 line. */
public final class FileAppender implements LogAppender {
    private final Path path;
    private final Object writeLock = new Object();

    public FileAppender(Path path) {
        this.path = Objects.requireNonNull(path).toAbsolutePath();
    }

    @Override
    public void append(LogEvent event, LogFormatter formatter) {
        // One lock per appender instance; a production version may use buffered I/O.
        synchronized (writeLock) {
            try {
                Path parent = path.getParent();
                if (parent != null) Files.createDirectories(parent);
                Files.writeString(path, formatter.format(event) + System.lineSeparator(),
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ex) {
                throw new UncheckedIOException("Could not write log to " + path, ex);
            }
        }
    }
}
