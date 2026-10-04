import java.io.IOException;

public class Logger {

    private LogLevel currentLevel;
    private FileWriter fileWriter;
    private String logFormat;

    private int messageCount = 0;

    private final Object lock = new Object();
    public Logger(LogLevel level, String filename, String format)
            throws IOException {

        this.currentLevel = level;
        this.fileWriter = new FileWriter(filename);
        this.logFormat = format;

        System.out.println(
                "✓ Logger initialized: level="
                        + level.getName()
                        + ", format="
                        + format
        );
    }

    private void log(LogLevel level,
                     String className,
                     int lineNumber,
                     String message) {

        // Check log level
        if (!level.isGreaterOrEqual(currentLevel)) {
            return;
        }

        String threadName = Thread.currentThread().getName();

        LogEntry entry = new LogEntry(
                level,
                threadName,
                className,
                lineNumber,
                message
        );

        synchronized (lock) {

            messageCount++;

            // Write to console
            System.out.println(entry.formatConsole());

            // Write to file
            try {
                String formatted =
                        LogFormatter.format(entry, logFormat);

                fileWriter.write(formatted);

            } catch (IOException e) {
                System.err.println(
                        "✗ Error writing to file: " + e.getMessage()
                );
            }
        }
    }

    public void debug(String message) {
        StackTraceElement[] stackTrace =
                Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTrace[2];

        log(
                LogLevel.DEBUG,
                caller.getClassName(),
                caller.getLineNumber(),
                message
        );
    }

    public void info(String message) {
        StackTraceElement[] stackTrace =
                Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTrace[2];

        log(
                LogLevel.INFO,
                caller.getClassName(),
                caller.getLineNumber(),
                message
        );
    }

    public void warn(String message) {
        StackTraceElement[] stackTrace =
                Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTrace[2];

        log(
                LogLevel.WARN,
                caller.getClassName(),
                caller.getLineNumber(),
                message
        );
    }

    public void error(String message) {
        StackTraceElement[] stackTrace =
                Thread.currentThread().getStackTrace();

        StackTraceElement caller = stackTrace[2];

        log(
                LogLevel.ERROR,
                caller.getClassName(),
                caller.getLineNumber(),
                message
        );
    }

    public int getMessageCount() {
        synchronized (lock) {
            return messageCount;
        }
    }

    public void flush() throws IOException {
        fileWriter.flush();
    }

    public void close() throws IOException {
        flush();
        fileWriter.close();

        System.out.println(
                "✓ Logger closed. Total messages: "
                        + messageCount
        );
    }
    public void setLogLevel(LogLevel level) {

        this.currentLevel = level;

        System.out.println(
                "✓ Log level changed to: "
                        + level.getName()
        );
    }
    public void setFormat(String format) {

        this.logFormat = format;

        System.out.println(
                "✓ Log format changed to: " + format
        );
    }


}
