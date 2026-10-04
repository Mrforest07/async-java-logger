import java.io.IOException;

public class TestLoggerFactory {

    public static void main(String[] args)
            throws IOException {

        Logger logger1 =
                LoggerFactory.getLogger();

        Logger logger2 =
                LoggerFactory.getLogger();

        System.out.println(
                "Same instance: "
                        + (logger1 == logger2)
        );

        logger1.info("Message from logger1");
        logger2.info("Message from logger2");

        logger1.close();
    }
}
