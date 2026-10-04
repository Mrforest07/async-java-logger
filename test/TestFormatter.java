public class TestFormatter {

    public static void main(String[] args) {

        LogEntry entry = new LogEntry(
                LogLevel.WARN,
                "Worker-1",
                "TestFormatter",
                20,
                "Cache unavailable"
        );

        System.out.println("FULL:");
        System.out.println(
                LogFormatter.format(entry, "FULL")
        );

        System.out.println("\nSIMPLE:");
        System.out.println(
                LogFormatter.format(entry, "SIMPLE")
        );
    }
}