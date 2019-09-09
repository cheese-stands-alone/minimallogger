package io.rwhite226.minimallogger.formatter;

import io.rwhite226.minimallogger.LogEntry;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

public interface LogFormatter {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    LogFormatter DEFAULT = new LogFormatter() {
    };

    default String format(final LogEntry logEntry) {
        StringBuilder sb = new StringBuilder();
        sb.append(logEntry.getLevel() != null ? logEntry.getLevel().name() : null);
        sb.append(" ");
        sb.append(logEntry.getTimestamp() != null ? dateFormatter.format(logEntry.getTimestamp()) : null);
        sb.append(" [");
        sb.append(logEntry.getThread() != null ? logEntry.getThread().getName() : null);
        sb.append("] ");
        sb.append(logEntry.getLoggerName());
        if (logEntry.getMdc() != null) {
            final Iterator<Map.Entry<String, String>> iterator = logEntry.getMdc().entrySet().iterator();
            if (iterator.hasNext()) {
                sb.append(" {");
                while (iterator.hasNext()) {
                    final Map.Entry<String, String> entry = iterator.next();
                    sb.append(entry.getKey());
                    sb.append(" = ");
                    sb.append(entry.getValue());
                    if (iterator.hasNext()) sb.append(", ");
                }
                sb.append("}");
            }
        }
        sb.append(" ");
        sb.append(logEntry.getMessage());
        sb.append("\n");
        if (logEntry.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            logEntry.getThrowable().printStackTrace(new PrintWriter(sw));
            sb.append(sw.getBuffer());
            sb.append("\n");
        }
        return sb.toString();
    }

}
