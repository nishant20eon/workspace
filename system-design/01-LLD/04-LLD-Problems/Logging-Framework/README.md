# Logging Framework — Low Level Design (Java + Maven)

## Interview problem statement
Design an extensible logging library for backend services. A caller should log events at different severity levels without knowing how messages are formatted or where they are stored.

### Functional requirements (implemented)
1. Support TRACE, DEBUG, INFO, WARN and ERROR severities.
2. Expose a simple client API: `Logger.info()`, `warn()`, `error()`, `debug()`, and `log()`.
3. Filter messages below a configurable minimum severity. Changing the minimum at runtime is allowed.
4. Format every accepted log message with UTC timestamp, level, thread, logger name, and text.
5. Deliver messages to one or more configured outputs: Console and UTF-8 file.
6. Allow adding or removing appenders at runtime.
7. Keep log events immutable and avoid interleaving line output within a single appender.
8. Demonstrate working code and provide automated unit tests.

### Non-functional requirements / assumptions
- Single-JVM, in-process and synchronous; not a replacement for SLF4J, Logback or Log4j.
- File writes occur under a per-appender lock and open the file for each line. This prioritizes simplicity over throughput.
- Concurrent writes are safe *within one FileAppender instance*, not across processes or separate appender instances pointing to the same file.
- If an appender throws, the exception propagates; production systems need an explicit failure policy and metrics.
- Do not log passwords, access tokens, secrets, card numbers, or sensitive personal data.
- No log rotation, batching, async queue, JSON formatter, MDC, distributed aggregation, or cross-process locking in this version.

## Design and class responsibilities
| Class | Responsibility |
| --- | --- |
| `Logger` | Public facade and event construction |
| `LogEvent` | Immutable log record |
| `LogLevel` | Ordered severity enum |
| `LogProcessor` | Chain of Responsibility base class |
| `LevelFilterProcessor` | Rejects messages under threshold |
| `AppenderProcessor` | Sends accepted events to configured appenders |
| `LogAppender` | Output strategy contract |
| `ConsoleAppender` / `FileAppender` | Output implementations |
| `LogFormatter` | Formatting strategy contract |
| `PlainTextFormatter` | Readable text formatting |

### Data flow
`Application -> Logger -> LevelFilterProcessor -> AppenderProcessor -> ConsoleAppender / FileAppender`

When `logger.debug(...)` is called at `INFO` threshold, the event is skipped. A `WARN` event goes through the chain, is formatted, and is written to every appender.

## Design patterns used
- **Chain of Responsibility**: processor pipeline; filter can stop propagation.
- **Strategy**: interchangeable output destinations (`LogAppender`) and text formatting (`LogFormatter`).
- **Facade**: `Logger` offers a small API over internal processors.

## Run
From the folder containing **this** `pom.xml`:

```bash
mvn clean test
mvn compile exec:java
```

The demo writes console output and `logs/application.log`. `logs/` is excluded from Git.

## Interview follow-up questions
1. How would you make logging asynchronous without losing messages on shutdown?
2. What happens when the disk is full? Should logging fail the business request?
3. How would you implement log rotation by time or size?
4. How would you add JSON formatting, MDC, request IDs, and distributed tracing?
5. How do you guarantee ordering for concurrent writers?
6. How would you minimize allocation costs when DEBUG is disabled?
7. How would you test message delivery under concurrency?
8. When would you choose SLF4J + Logback instead of a custom framework?
