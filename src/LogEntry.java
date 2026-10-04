import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String timestamp;
    private final LogLevel level;
    private final String threadName;
    private final String className;
    private final int lineNumber;
    private final String message;

    public LogEntry(LogLevel level,
                    String threadName,
                    String className,
                    int lineNumber,
                    String message) {

        this.timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss.SSS"));

        this.level = level;
        this.threadName = threadName;
        this.className = className;
        this.lineNumber = lineNumber;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getClassName() {
        return className;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getMessage() {
        return message;
    }

    public String formatConsole() {
        return String.format(
                "[%s] %-5s [%-15s] %s",
                timestamp,
                level.getName(),
                threadName,
                message
        );
    }

    public String formatFile() {
        return String.format(
                "[%s] %-5s [%-15s] %s:%d - %s",
                timestamp,
                level.getName(),
                threadName,
                className,
                lineNumber,
                message
        );
    }

    @Override
    public String toString() {
        return formatConsole();
    }
}
