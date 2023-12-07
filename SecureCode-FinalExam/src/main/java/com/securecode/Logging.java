package com.securecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {

    public enum Level {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(securecode.class);

    public static void Log(Level level, String message) {
        Log(level, message, null);
    }

    public static void Log(Level level, String message, Object object) {

        switch(level) {
            case TRACE: 
                trace(message);
                break;
            case DEBUG: 
                debug(message);
                break;
            case INFO: 
                info(message);
                break;
            case WARN: 
                warn(message);
                break;
            case ERROR: 
                error(message, object);
                break;
            default: 
                error(message, object);
        }
    }
    
    private static void trace(String message) {
        LOGGER.trace(message);
    }

    private static void debug(String message) {

        LOGGER.debug(message);
    }

    private static void info(String message) {
        LOGGER.info(message);
    }

    private static void warn( String message) {
        LOGGER.warn(message);
    }

    private static void error(String message, Object object) {
        if (object == null) {
            LOGGER.error(message);
        } else {
            LOGGER.error(message, object);
        }
    }






}
