package impl;

import io.rwhite226.minimallogger.LoggingConfiguration;
import io.rwhite226.minimallogger.MinimalLogger;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class StaticLoggerBinder implements LoggerFactoryBinder {

    // This field must not be final to avoid constant folding by the compiler.
    // @checkstyle off: StaticVariableName|VisibilityModifier
    public static String REQUESTED_API_VERSION = "1.7";
    // @checkstyle on: StaticVariableName|VisibilityModifier

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
    private static final ConcurrentMap<String, MinimalLogger> loggers = new ConcurrentHashMap<>();

    private static final ILoggerFactory factory = name -> {
        MinimalLogger logger = loggers.get(name);
        if (logger == null) {
            MinimalLogger newLogger = new MinimalLogger(name);
            MinimalLogger existingLogger = loggers.putIfAbsent(name, newLogger);
            return existingLogger == null ? newLogger : existingLogger;
        } else {
            return logger;
        }
    };

    public static StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return factory;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return factory.getClass().getName();
    }

    static {
        final Thread shutdown = new Thread(LoggingConfiguration::shutdown);
        shutdown.setDaemon(false);
        Runtime.getRuntime().addShutdownHook(shutdown);
    }

}
