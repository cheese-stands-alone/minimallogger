package io.rwhite226.minimallogger;

import io.rwhite226.minimallogger.writer.Writer;
import org.slf4j.Marker;
import org.slf4j.event.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LoggingConfiguration {
    static final CopyOnWriteArrayList<Writer> writers = new CopyOnWriteArrayList<>();
    private final static AtomicInteger baseLevel = new AtomicInteger(Level.INFO.toInt());

    public static List<Writer> getWriters() {
        return Collections.unmodifiableList(writers);
    }

    public static void addWriters(final List<Writer> newWriters) {
        if(!newWriters.isEmpty()) {
            int level = Level.WARN.toInt();
            for (final Writer writer : newWriters) {
                int writerLevel = writer.getLevelValue();
                if (writerLevel < level) {
                    level = writerLevel;
                }
            }
            baseLevel.set(level);
            writers.addAll(newWriters);
        }
    }

    public static void clearWriters() {
        writers.clear();
    }

    public static boolean isLevelEnabled(final Level level, final Marker marker) {
        return baseLevel.get() <= level.toInt();
    }

    public static void setLevel(final Level level) {
        baseLevel.set(level.toInt());
    }

    public static int getLevelValue() {
        return baseLevel.get();
    }

    public static void shutdown() {
        List<Writer> copy = new ArrayList<>(writers);
        clearWriters();
        copy.forEach(Writer::close);
        MinimalLogger.defaultWriter.close();
    }
}
