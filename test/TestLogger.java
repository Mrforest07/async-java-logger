import java.io.IOException;

public class TestLogger {

    public static void main(String[] args) throws IOException {

        Logger logger =
                new Logger(
                        LogLevel.INFO,
                        "application.log",
                        "FULL"
                );

        logger.info("Application started");
        logger.warn("This is a warning");
        logger.error("Something went wrong");
        logger.debug("Debug message");

        System.out.println(
                "Total messages: " + logger.getMessageCount()
        );

        System.out.println(
                "Total messages: " + logger.getMessageCount()
        );

        logger.setLogLevel(LogLevel.DEBUG);

        logger.debug("Now DEBUG should appear");
        logger.info("INFO should appear");

        logger.setFormat("SIMPLE");

        logger.info("Simple format message");

        logger.setFormat("FULL");

        logger.info("Full format message");

        logger.close();
    }
}