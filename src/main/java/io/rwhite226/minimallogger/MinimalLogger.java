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

public class MinimalLogger implements Logger {

    static final ConsoleWriter defaultWriter = new ConsoleWriter(false, LogFormatter.DEFAULT);

    private final String name;

    public MinimalLogger(final String name) {
        this.name = name;
    }

    private void log(
            final Level level,
            final Marker marker,
            final String message,
            final Throwable throwable,
            final Object[] argArray
    ) {
        final LogEntry entry = new LogEntry(
                name,
                LocalDateTime.now(),
                Thread.currentThread(),
                MDC.getCopyOfContextMap(),
                level,
                message,
                throwable,
                argArray
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
        if (isWarnEnabled()) log(Level.WARN, null, msg, null, null);
    }

    @Override
    public void warn(final String format, final Object arg) {
        if (isWarnEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.WARN, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void warn(final String format, final Object... arguments) {
        if (isWarnEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.WARN, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        if (isWarnEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.WARN, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void warn(final String msg, final Throwable t) {
        if (isWarnEnabled()) log(Level.WARN, null, msg, t, null);
    }

    @Override
    public void warn(final Marker marker, final String msg) {
        if (isWarnEnabled()) log(Level.WARN, marker, msg, null, null);
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg) {
        if (isWarnEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.WARN, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isWarnEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.WARN, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void warn(final Marker marker, final String format, final Object... arguments) {
        if (isWarnEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.WARN, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void warn(final Marker marker, final String msg, final Throwable t) {
        if (isWarnEnabled()) log(Level.WARN, marker, msg, t, null);
    }

    @Override
    public void debug(final String msg) {
        if (isDebugEnabled()) log(Level.DEBUG, null, msg, null, null);
    }

    @Override
    public void debug(final String format, final Object arg) {
        if (isDebugEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.DEBUG, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void debug(final String format, final Object... arguments) {
        if (isDebugEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.DEBUG, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        if (isDebugEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.DEBUG, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void debug(final String msg, final Throwable t) {
        if (isDebugEnabled()) log(Level.DEBUG, null, msg, t, null);
    }

    @Override
    public void debug(final Marker marker, final String msg) {
        if (isDebugEnabled()) log(Level.DEBUG, marker, msg, null, null);
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg) {
        if (isDebugEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.DEBUG, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isDebugEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.DEBUG, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void debug(final Marker marker, final String format, final Object... arguments) {
        if (isDebugEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.DEBUG, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void debug(final Marker marker, final String msg, final Throwable t) {
        if (isDebugEnabled()) log(Level.DEBUG, marker, msg, t, null);
    }

    @Override
    public void info(final String msg) {
        if (isInfoEnabled()) log(Level.INFO, null, msg, null, null);
    }

    @Override
    public void info(final String format, final Object arg) {
        if (isInfoEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.INFO, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void info(final String format, final Object... arguments) {
        if (isInfoEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.INFO, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        if (isInfoEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.INFO, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void info(final String msg, final Throwable t) {
        if (isInfoEnabled()) log(Level.INFO, null, msg, t, null);
    }

    @Override
    public void info(final Marker marker, final String msg) {
        if (isInfoEnabled()) log(Level.INFO, marker, msg, null, null);
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg) {
        if (isInfoEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.INFO, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isInfoEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.INFO, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void info(final Marker marker, final String format, final Object... arguments) {
        if (isInfoEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.INFO, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void info(final Marker marker, final String msg, final Throwable t) {
        if (isInfoEnabled()) log(Level.INFO, marker, msg, t, null);
    }

    @Override
    public void error(final String msg) {
        if (isErrorEnabled()) log(Level.ERROR, null, msg, null, null);
    }

    @Override
    public void error(final String format, final Object arg) {
        if (isErrorEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.ERROR, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void error(final String format, final Object... arguments) {
        if (isErrorEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.ERROR, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        if (isErrorEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.ERROR, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void error(final String msg, final Throwable t) {
        if (isErrorEnabled()) log(Level.ERROR, null, msg, t, null);
    }

    @Override
    public void error(final Marker marker, final String msg) {
        if (isErrorEnabled()) log(Level.ERROR, marker, msg, null, null);
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg) {
        if (isErrorEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.ERROR, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isErrorEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.ERROR, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void error(final Marker marker, final String format, final Object... arguments) {
        if (isErrorEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.ERROR, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void error(final Marker marker, final String msg, final Throwable t) {
        if (isErrorEnabled()) log(Level.ERROR, marker, msg, t, null);
    }

    @Override
    public void trace(final String msg) {
        if (isTraceEnabled()) log(Level.TRACE, null, msg, null, null);
    }

    @Override
    public void trace(final String format, final Object arg) {
        if (isTraceEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.TRACE, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void trace(final String format, final Object... arguments) {
        if (isTraceEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.TRACE, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        if (isTraceEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.TRACE, null, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void trace(final String msg, final Throwable t) {
        if (isTraceEnabled()) log(Level.TRACE, null, msg, t, null);
    }

    @Override
    public void trace(final Marker marker, final String msg) {
        if (isTraceEnabled()) log(Level.TRACE, marker, msg, null, null);
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg) {
        if (isTraceEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg);
            log(Level.TRACE, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg1, Object arg2) {
        if (isTraceEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arg1, arg2);
            log(Level.TRACE, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }
    }

    @Override
    public void trace(final Marker marker, final String format, final Object... arguments) {
        if (isTraceEnabled()) {
            final FormattingTuple tuple = MessageFormatter.format(format, arguments);
            log(Level.TRACE, marker, tuple.getMessage(), tuple.getThrowable(), tuple.getArgArray());
        }

    }

    @Override
    public void trace(final Marker marker, final String msg, final Throwable t) {
        if (isTraceEnabled()) log(Level.TRACE, marker, msg, t, null);
    }
}
