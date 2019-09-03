package io.rwhite226.minimallogger;

import org.slf4j.event.Level;
import org.slf4j.helpers.FormattingTuple;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class LogEntry {

    public static final LogEntry SHUTDOWN_EVENT = new LogEntry(null, null, null, null, null, null);

    private final String loggerName;
    private final LocalDateTime timestamp;
    private final Thread thread;
    private final Map<String, String> mdc;
    private final Level level;
    private final Supplier<FormattingTuple> tupleBuilder;

    public LogEntry(
            String loggerName,
            LocalDateTime timestamp,
            Thread thread,
            Map<String, String> mdc,
            Level level,
            Supplier<FormattingTuple> tupleBuilder
    ) {
        this.loggerName = loggerName;
        this.timestamp = timestamp;
        this.thread = thread;
        this.mdc = mdc;
        this.level = level;
        this.tupleBuilder = tupleBuilder;
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

    public Supplier<FormattingTuple> getTupleBuilder() {
        return tupleBuilder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry logEntry = (LogEntry) o;
        return Objects.equals(loggerName, logEntry.loggerName) &&
                Objects.equals(timestamp, logEntry.timestamp) &&
                Objects.equals(thread, logEntry.thread) &&
                Objects.equals(mdc, logEntry.mdc) &&
                level == logEntry.level &&
                Objects.equals(tupleBuilder, logEntry.tupleBuilder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggerName, timestamp, thread, mdc, level, tupleBuilder);
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "loggerName='" + loggerName + '\'' +
                ", timestamp=" + timestamp +
                ", thread=" + thread +
                ", mdc=" + mdc +
                ", level=" + level +
                ", tupleBuilder=" + tupleBuilder +
                '}';
    }
}
