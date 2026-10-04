public class TestLogLevel {

    public static void main(String[] args) {

        System.out.println(LogLevel.DEBUG.getLevel());
        System.out.println(LogLevel.INFO.getName());

        System.out.println(
                LogLevel.WARN.isGreaterOrEqual(LogLevel.INFO)
        );

        System.out.println(
                LogLevel.DEBUG.isGreaterOrEqual(LogLevel.INFO)
        );
    }
}
