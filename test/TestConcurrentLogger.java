import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestConcurrentLogger {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        Logger logger =
                new Logger(
                        LogLevel.INFO,
                        "concurrent.log",
                        "FULL"
                );

        ExecutorService executor =
                Executors.newFixedThreadPool(5);

        int messagesPerThread = 50;

        // Create 5 concurrent tasks
        for (int threadId = 1; threadId <= 5; threadId++) {

            final int id = threadId;

            executor.submit(() -> {

                for (int i = 1; i <= messagesPerThread; i++) {

                    logger.info(
                            "Thread-" + id +
                                    ": Message " + i
                    );
                }
            });
        }

        // Stop accepting new tasks
        executor.shutdown();

        // Wait for all threads
        boolean completed =
                executor.awaitTermination(
                        2,
                        TimeUnit.MINUTES
                );

        System.out.println(
                "All threads completed: " + completed
        );

        System.out.println(
                "Total messages: " +
                        logger.getMessageCount()
        );

        System.out.println(
                "Expected messages: " +
                        (5 * messagesPerThread)
        );

        System.out.println(
                "Data loss: " +
                        (5 * messagesPerThread
                                - logger.getMessageCount())
        );

        logger.close();
    }
}