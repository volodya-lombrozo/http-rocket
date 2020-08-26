package org.rocket.logger;

import java.util.logging.Level;

public interface Logger {

    void info(String message);

    void err(String message);

    class Default implements Logger {

        private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("Rocket Server");

        public void info(String message) {
            logger.info(message);
        }

        public void err(String message) {
            logger.log(Level.ALL, "Error: " + message);
        }
    }
}
