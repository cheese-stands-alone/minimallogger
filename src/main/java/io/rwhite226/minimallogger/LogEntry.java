package io.rwhite226.minimallogger;

import org.slf4j.event.Level;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class LogEntry {

    public static final LogEntry SHUTDOWN_EVENT = new LogEntry(null, null, null, null, null, null, null, null);

    private final String loggerName;
    private final LocalDateTime timestamp;
    private final Thread thread;
    private final Map<String, String> mdc;
    private final Level level;
    private final String message;
    private final Throwable throwable;
    private final Object[] argArray;

    public LogEntry(
            final String loggerName,
            final LocalDateTime timestamp,
            final Thread thread,
            final Map<String, String> mdc,
            final Level level,
            final String message,
            final Throwable throwable,
            final Object[] argArray
    ) {
        this.loggerName = loggerName;
        this.timestamp = timestamp;
        this.thread = thread;
        this.mdc = mdc;
        this.level = level;
        this.message = message;
        this.throwable = throwable;
        this.argArray = argArray;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Thread getThread() {
        return thread;
    }

    public Map<String, String> getMdc() {
        return mdc;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Object[] getArgArray() {
        return argArray;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry logEntry = (LogEntry) o;
        return Objects.equals(loggerName, logEntry.loggerName) &&
                Objects.equals(timestamp, logEntry.timestamp) &&
                Objects.equals(thread, logEntry.thread) &&
                Objects.equals(mdc, logEntry.mdc) &&
                Objects.equals(level, logEntry.level) &&
                Objects.equals(message, logEntry.message) &&
                Objects.equals(throwable, logEntry.throwable) &&
                Arrays.equals(argArray, logEntry.argArray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggerName, timestamp, thread, mdc, level, message, throwable, Arrays.hashCode(argArray));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder()
                .append("LogEntry(loggerName=")
                .append(loggerName)
                .append(", timestamp=")
                .append(timestamp)
                .append(", thread=")
                .append(thread)
                .append(", mdc=")
                .append(mdc)
                .append(", level=")
                .append(level)
                .append(", message=")
                .append(message)
                .append(", throwable=")
                .append(throwable)
                .append(", argArray=");
        if (argArray == null) {
            sb.append("null");
        } else {
            sb.append("[");
            for (int i = 0; i < argArray.length; i++) {
                sb.append(argArray[i]);
                if (i < (argArray.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append("]");
        }
        return sb.append(')').toString();
    }
}
