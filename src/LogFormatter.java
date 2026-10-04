public class LogFormatter {

    public static String format(LogEntry entry, String format) {

        switch (format.toUpperCase()) {

            case "FULL":
                return entry.formatFile();

            case "SIMPLE":
                return entry.formatConsole();

            case "JSON":
                return formatJson(entry);

            default:
                return entry.formatConsole();
        }
    }

    private static String formatJson(LogEntry entry) {

        return "{"
                + "\"timestamp\":\"" + entry.getTimestamp() + "\","
                + "\"level\":\"" + entry.getLevel().getName() + "\","
                + "\"thread\":\"" + entry.getThreadName() + "\","
                + "\"class\":\"" + entry.getClassName() + "\","
                + "\"line\":" + entry.getLineNumber() + ","
                + "\"message\":\"" + entry.getMessage() + "\""
                + "}";
    }
}