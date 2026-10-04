public class TestLogFormatting {

    public static void main(String[] args) {

        LogEntry entry =
                new LogEntry(
                        LogLevel.INFO,
                        "main",
                        "TestLogFormatting",
                        25,
                        "User logged in"
                );

        System.out.println("SIMPLE:");
        System.out.println(
                LogFormatter.format(entry, "SIMPLE")
        );

        System.out.println();

        System.out.println("FULL:");
        System.out.println(
                LogFormatter.format(entry, "FULL")
        );

        System.out.println();

        System.out.println("JSON:");
        System.out.println(
                LogFormatter.format(entry, "JSON")
        );
    }
}