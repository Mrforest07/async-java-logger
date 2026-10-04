import java.io.IOException;

public class TestLogLevels {

    public static void main(String[] args)
            throws IOException {

        Logger logger = new Logger(
                LogLevel.WARN,
                "levels.log",
                "FULL"
        );

        logger.debug("DEBUG message");
        logger.info("INFO message");
        logger.warn("WARN message");
        logger.error("ERROR message");

        System.out.println(
                "Total messages: "
                        + logger.getMessageCount()
        );

        logger.close();
    }
}