package io.rwhite226.minimallogger.writer;

import io.rwhite226.minimallogger.LogEntry;
import io.rwhite226.minimallogger.formatter.LogFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileWriter implements Writer {

    private BlockingQueue<LogEntry> queue;
    private final LogFormatter formatter;
    private final Thread writingThread;
    private final BufferedWriter writer;

    public FileWriter(final boolean async, final LogFormatter formatter, final BufferedWriter writer) {
        this.formatter = formatter;
        this.writer = writer;
        if (async) {
            queue = new LinkedBlockingQueue<>();
            writingThread = consumeAllAsync(queue, this::write1, () -> {
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        } else {
            queue = null;
            writingThread = null;
        }
    }

    public FileWriter(final boolean async, final LogFormatter formatter, final File file) throws IOException {
        this(async, formatter, file.toPath());
    }

    public FileWriter(final boolean async, final LogFormatter formatter, final Path path) throws IOException {
        this(async, formatter, Files.newBufferedWriter(path, StandardOpenOption.APPEND));
    }

    protected void write1(final LogEntry logEntry) {
        final String log = formatter.format(logEntry);
        try {
            writer.append(log);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
