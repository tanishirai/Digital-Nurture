package com.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {

    // Logger is created once per class, static and final
    // LoggerFactory.getLogger() binds this logger to the class name,
    // so the output shows exactly which class produced each log line
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {

        // ERROR - something went seriously wrong, needs immediate attention
        // e.g. database is down, payment failed, NullPointerException caught
        logger.error("This is an error message");

        // WARN - something unexpected happened but the app can still continue
        // e.g. a config value is missing so we're using a default, response was slow
        logger.warn("This is a warning message");

        // INFO - normal application events worth recording
        // e.g. server started, user logged in, transaction completed
        logger.info("This is an info message");

        // DEBUG - detailed info useful during development/debugging
        // e.g. method entered, variable value, step completed
        // Not shown in production by default (controlled by logback.xml)
        logger.debug("This is a debug message");

        // TRACE - even more detailed than DEBUG, usually for tracing method calls
        // Lowest priority level, typically turned off unless actively debugging
        logger.trace("This is a trace message");

        logger.info("--- Logging levels from highest to lowest: ERROR > WARN > INFO > DEBUG > TRACE ---");
    }
}
