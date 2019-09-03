package io.rwhite226.minimallogger.writer;

import io.rwhite226.minimallogger.LogEntry;
import io.rwhite226.minimallogger.LoggingConfiguration;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public interface Writer {

    void write(final LogEntry logEntry);

    void close();

    default int getLevelValue() {
        return LoggingConfiguration.getLevelValue();
    }

    default Thread consumeAllAsync(final BlockingQueue<LogEntry> queue, final Consumer<LogEntry> consumer) {
        final Thread thread = new Thread(() -> {
            while (true) {
                try {
                    final LogEntry entry = queue.take();
                    if (entry == LogEntry.SHUTDOWN_EVENT) {
                        while (queue.peek() != null) {
                            consumer.accept(queue.poll());
                        }
                        break;
                    }
                    consumer.accept(entry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setName(this.getClass().getSimpleName());
        thread.setDaemon(false);
        thread.start();
        return thread;
    }
}
