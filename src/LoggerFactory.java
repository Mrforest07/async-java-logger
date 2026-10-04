import java.io.IOException;

public class LoggerFactory {

    private static Logger instance;

    public static synchronized Logger getLogger()
            throws IOException {

        if (instance == null) {

            instance = new Logger(
                    LogLevel.INFO,
                    "application.log",
                    "FULL"
            );
        }

        return instance;
    }

    public static synchronized Logger getLogger(
            LogLevel level,
            String filename)
            throws IOException {

        if (instance == null) {

            instance = new Logger(
                    level,
                    filename,
                    "FULL"
            );
        }

        return instance;
    }

    public static Logger getInstance() {

        if (instance == null) {
            throw new IllegalStateException(
                    "Logger not initialized. Call getLogger() first."
            );
        }

        return instance;
    }
}