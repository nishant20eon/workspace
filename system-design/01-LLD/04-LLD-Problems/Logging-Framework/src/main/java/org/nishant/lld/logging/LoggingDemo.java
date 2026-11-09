package org.nishant.lld.logging;

import java.nio.file.Path;
import java.time.Clock;

/** Run with: mvn compile exec:java */
public final class LoggingDemo {
    public static void main(String[] args) {
        try (Logger logger = new Logger("PaymentService", LogLevel.INFO,
                                      Clock.systemUTC(), new PlainTextFormatter())) {
            logger.addAppender(new ConsoleAppender(System.out));
            logger.addAppender(new FileAppender(Path.of("logs", "application.log")));

            logger.debug("Debug message is ignored at INFO threshold");
            logger.info("Payment request received");
            logger.warn("Downstream response was slow");
            logger.error("Payment gateway unavailable");

            // Change configuration at runtime to allow DEBUG events.
            logger.setLevel(LogLevel.DEBUG);
            logger.debug("Diagnostic details enabled");
        }
        System.out.println("Also wrote logs/application.log");
    }
}
