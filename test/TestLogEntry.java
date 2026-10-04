public class TestLogEntry {

    public static void main(String[] args) {

        LogEntry entry = new LogEntry(
                LogLevel.INFO,
                Thread.currentThread().getName(),
                "TestLogEntry",
                10,
                "Application started"
        );

        System.out.println(entry.formatConsole());
        System.out.println(entry.formatFile());
    }
}