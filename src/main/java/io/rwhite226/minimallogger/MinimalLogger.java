package io.rwhite226.minimallogger;

import io.rwhite226.minimallogger.formatter.LogFormatter;
import io.rwhite226.minimallogger.writer.ConsoleWriter;
import io.rwhite226.minimallogger.writer.Writer;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class MinimalLogger implements Logger {

    static final ConsoleWriter defaultWriter = new ConsoleWriter(false, LogFormatter.DEFAULT);

    private final String name;

    public MinimalLogger(final String name) {
        this.name = name;
    }

    private void log(
            final Level level,
            final Marker marker,
            final Supplier<FormattingTuple> tuple
    ) {
        final LogEntry entry = new LogEntry(
                name,
                LocalDateTime.now(),
                Thread.currentThread(),
                MDC.getCopyOfContextMap(),
                level,
                tuple
        );

        if (LoggingConfiguration.writers.isEmpty()) {
            if (defaultWriter.getLevelValue() <= level.toInt()) {
                defaultWriter.write(entry);
            }
        } else {
            for (final Writer writer : LoggingConfiguration.writers) {
                if (writer.getLevelValue() <= level.toInt()) {
                    writer.write(entry);
                }
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return LoggingConfiguration.isLevelEnabled(Level.TRACE, null);
    }

    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return LoggingConfiguration.isLevelEnabled(Level.TRACE, marker);
    }

    @Override
    public boolean isDebugEnabled() {
        return LoggingConfiguration.isLevelEnabled(Level.DEBUG, null);
    }

    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return LoggingConfiguration.isLevelEnabled(Level.DEBUG, marker);
    }

    @Override
    public boolean isInfoEnabled() {
        return LoggingConfiguration.isLevelEnabled(Level.INFO, null);
    }

    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return LoggingConfiguration.isLevelEnabled(Level.INFO, marker);
    }

    @Override
    public boolean isWarnEnabled() {
        return LoggingConfiguration.isLevelEnabled(Level.WARN, null);
    }

    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return LoggingConfiguration.isLevelEnabled(Level.WARN, marker);
    }

    @Override
    public boolean isErrorEnabled() {
        return LoggingConfiguration.isLevelEnabled(Level.ERROR, null);
    }

    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return LoggingConfiguration.isLevelEnabled(Level.ERROR, marker);
    }

    @Override
    public void warn(final String msg) {
        if (isWarnEnabled()) log(Level.WARN, null, () -> new FormattingTuple(msg));
    }

    @Override
    public void warn(final String format, final Object arg) {
        if (isWarnEnabled()) log(Level.WARN, null, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void warn(final String format, final Object... arguments) {
        if (isWarnEnabled()) log(Level.WARN, null, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        if (isWarnEnabled()) log(Level.WARN, null, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void warn(final String msg, final Throwable t) {
        if (isWarnEnabled()) log(Level.WARN, null, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void warn(final Marker marker, final String msg) {
        if (isWarnEnabled()) log(Level.WARN, marker, () -> new FormattingTuple(msg));
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg) {
        if (isWarnEnabled()) log(Level.WARN, marker, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isWarnEnabled()) log(Level.WARN, marker, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void warn(final Marker marker, final String format, final Object... arguments) {
        if (isWarnEnabled()) log(Level.WARN, marker, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void warn(final Marker marker, final String msg, final Throwable t) {
        if (isWarnEnabled()) log(Level.WARN, marker, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void debug(final String msg) {
        if (isDebugEnabled()) log(Level.DEBUG, null, () -> new FormattingTuple(msg));
    }

    @Override
    public void debug(final String format, final Object arg) {
        if (isDebugEnabled()) log(Level.DEBUG, null, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void debug(final String format, final Object... arguments) {
        if (isDebugEnabled()) log(Level.DEBUG, null, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        if (isDebugEnabled()) log(Level.DEBUG, null, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void debug(final String msg, Throwable t) {
        if (isDebugEnabled()) log(Level.DEBUG, null, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void debug(final Marker marker, final String msg) {
        if (isDebugEnabled()) log(Level.DEBUG, marker, () -> new FormattingTuple(msg));
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg) {
        if (isDebugEnabled()) log(Level.DEBUG, marker, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isDebugEnabled()) log(Level.DEBUG, marker, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void debug(final Marker marker, final String format, final Object... arguments) {
        if (isDebugEnabled()) log(Level.DEBUG, marker, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void debug(final Marker marker, final String msg, final Throwable t) {
        if (isDebugEnabled()) log(Level.DEBUG, marker, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void info(final String msg) {
        if (isInfoEnabled()) log(Level.INFO, null, () -> new FormattingTuple(msg));
    }

    @Override
    public void info(final String format, final Object arg) {
        if (isInfoEnabled()) log(Level.INFO, null, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void info(final String format, final Object... arguments) {
        if (isInfoEnabled()) log(Level.INFO, null, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        if (isInfoEnabled()) log(Level.INFO, null, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void info(final String msg, Throwable t) {
        if (isInfoEnabled()) log(Level.INFO, null, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void info(final Marker marker, final String msg) {
        if (isInfoEnabled()) log(Level.INFO, marker, () -> new FormattingTuple(msg));
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg) {
        if (isInfoEnabled()) log(Level.INFO, marker, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isInfoEnabled()) log(Level.INFO, marker, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void info(final Marker marker, final String format, final Object... arguments) {
        if (isInfoEnabled()) log(Level.INFO, marker, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void info(final Marker marker, final String msg, final Throwable t) {
        if (isInfoEnabled()) log(Level.INFO, marker, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void error(final String msg) {
        if (isErrorEnabled()) log(Level.ERROR, null, () -> new FormattingTuple(msg));
    }

    @Override
    public void error(final String format, final Object arg) {
        if (isErrorEnabled()) log(Level.ERROR, null, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void error(final String format, final Object... arguments) {
        if (isErrorEnabled()) log(Level.ERROR, null, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        if (isErrorEnabled()) log(Level.ERROR, null, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void error(final String msg, Throwable t) {
        if (isErrorEnabled()) log(Level.ERROR, null, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void error(final Marker marker, final String msg) {
        if (isErrorEnabled()) log(Level.ERROR, marker, () -> new FormattingTuple(msg));
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg) {
        if (isErrorEnabled()) log(Level.ERROR, marker, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isErrorEnabled()) log(Level.ERROR, marker, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void error(final Marker marker, final String format, final Object... arguments) {
        if (isErrorEnabled()) log(Level.ERROR, marker, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void error(final Marker marker, final String msg, final Throwable t) {
        if (isErrorEnabled()) log(Level.ERROR, marker, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void trace(final String msg) {
        if (isTraceEnabled()) log(Level.TRACE, null, () -> new FormattingTuple(msg));
    }

    @Override
    public void trace(final String format, final Object arg) {
        if (isTraceEnabled()) log(Level.TRACE, null, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void trace(final String format, final Object... arguments) {
        if (isTraceEnabled()) log(Level.TRACE, null, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        if (isTraceEnabled()) log(Level.TRACE, null, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void trace(final String msg, Throwable t) {
        if (isTraceEnabled()) log(Level.TRACE, null, () -> new FormattingTuple(msg, null, t));
    }

    @Override
    public void trace(final Marker marker, final String msg) {
        if (isTraceEnabled()) log(Level.TRACE, marker, () -> new FormattingTuple(msg));
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg) {
        if (isTraceEnabled()) log(Level.TRACE, marker, () -> MessageFormatter.format(format, arg));
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isTraceEnabled()) log(Level.TRACE, marker, () -> MessageFormatter.format(format, arg1, arg2));
    }

    @Override
    public void trace(final Marker marker, final String format, final Object... arguments) {
        if (isTraceEnabled()) log(Level.TRACE, marker, () -> MessageFormatter.format(format, arguments));
    }

    @Override
    public void trace(final Marker marker, final String msg, final Throwable t) {
        if (isTraceEnabled()) log(Level.TRACE, marker, () -> new FormattingTuple(msg, null, t));
    }
}
