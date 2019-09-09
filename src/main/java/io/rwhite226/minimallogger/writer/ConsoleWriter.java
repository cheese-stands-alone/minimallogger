package io.rwhite226.minimallogger.writer;

import io.rwhite226.minimallogger.LogEntry;
import io.rwhite226.minimallogger.formatter.LogFormatter;
import org.slf4j.event.Level;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsoleWriter implements Writer {

    private BlockingQueue<LogEntry> queue;
    private final LogFormatter formatter;
    private final Thread writingThread;

    public ConsoleWriter(final boolean async, final LogFormatter formatter) {
        this.formatter = formatter;
        if (async) {
            queue = new LinkedBlockingQueue<>();
            writingThread = consumeAllAsync(queue, this::write1, null);
        } else {
            queue = null;
            writingThread = null;
        }
    }

    protected void write1(final LogEntry logEntry) {
        final String log = formatter.format(logEntry);
        if (logEntry.getLevel() == Level.ERROR) System.err.print(log);
        else System.out.print(log);
    }

    @Override
    public void write(final LogEntry logEntry) {
        if (queue != null) {
            queue.add(logEntry);
        } else {
            write1(logEntry);
        }
    }

    @Override
    public void close() {
        try {
            final BlockingQueue<LogEntry> temp = queue;
            queue = null;
            if (temp != null) {
                temp.add(LogEntry.SHUTDOWN_EVENT);
                if (writingThread != null) {
                    writingThread.join();
                }
            }
            System.err.flush();
            System.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
