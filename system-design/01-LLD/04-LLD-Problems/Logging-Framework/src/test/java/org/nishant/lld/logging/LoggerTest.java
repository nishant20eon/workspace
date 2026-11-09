package org.nishant.lld.logging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    private static final Clock FIXED = Clock.fixed(
            Instant.parse("2026-11-09T10:00:00Z"), ZoneOffset.UTC);

    @Test
    void filtersBelowThresholdAndAllowsHigherSeverity() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (Logger logger = new Logger("Test", LogLevel.WARN, FIXED, new PlainTextFormatter())) {
            logger.addAppender(new ConsoleAppender(new PrintStream(bytes, true, StandardCharsets.UTF_8)));
            logger.info("hidden");
            logger.warn("shown");
        }
        String output = bytes.toString(StandardCharsets.UTF_8);
        assertFalse(output.contains("hidden"));
        assertTrue(output.contains("[WARN]"));
        assertTrue(output.contains("shown"));
    }

    @Test
    void changingThresholdAllowsDebugMessages() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (Logger logger = new Logger("Test", LogLevel.INFO, FIXED, new PlainTextFormatter())) {
            logger.addAppender(new ConsoleAppender(new PrintStream(bytes, true, StandardCharsets.UTF_8)));
            logger.debug("before");
            logger.setLevel(LogLevel.DEBUG);
            logger.debug("after");
        }
        String output = bytes.toString(StandardCharsets.UTF_8);
        assertFalse(output.contains("before"));
        assertTrue(output.contains("after"));
    }

    @Test
    void appendsToFile(@TempDir Path directory) throws Exception {
        Path file = directory.resolve("logs/events.log");
        try (Logger logger = new Logger("FileTest", LogLevel.INFO, FIXED, new PlainTextFormatter())) {
            logger.addAppender(new FileAppender(file));
            logger.error("disk test");
            logger.error("second line");
        }
        var lines = Files.readAllLines(file);
        assertEquals(2, lines.size());
        assertTrue(lines.get(0).contains("disk test"));
        assertTrue(lines.get(1).contains("second line"));
    }

    @Test
    void sendsOneMessageToMultipleAppenders() {
        ByteArrayOutputStream a = new ByteArrayOutputStream();
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try (Logger logger = new Logger("FanOut", LogLevel.INFO, FIXED, new PlainTextFormatter())) {
            logger.addAppender(new ConsoleAppender(new PrintStream(a, true, StandardCharsets.UTF_8)));
            logger.addAppender(new ConsoleAppender(new PrintStream(b, true, StandardCharsets.UTF_8)));
            logger.info("shared event");
        }
        assertTrue(a.toString(StandardCharsets.UTF_8).contains("shared event"));
        assertTrue(b.toString(StandardCharsets.UTF_8).contains("shared event"));
    }
}
