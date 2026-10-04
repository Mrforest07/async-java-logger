import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestStress {

    public static void main(String[] args)
            throws IOException, InterruptedException {

        Logger logger = new Logger(
                LogLevel.INFO,
                "stress.log",
                "FULL"
        );

        ExecutorService executor =
                Executors.newFixedThreadPool(5);

        long startTime = System.currentTimeMillis();

        for (int threadId = 1; threadId <= 5; threadId++) {

            final int id = threadId;

            executor.submit(() -> {

                long endTime =
                        System.currentTimeMillis() + 10000;

                int count = 0;

                while (System.currentTimeMillis() < endTime) {

                    logger.info(
                            "Stress test message #"
                                    + (++count)
                                    + " from thread "
                                    + id
                    );
                }

                System.out.println(
                        "Thread " + id
                                + " logged "
                                + count
                                + " messages"
                );
            });
        }

        executor.shutdown();

        boolean completed =
                executor.awaitTermination(
                        15,
                        TimeUnit.SECONDS
                );

        long duration =
                System.currentTimeMillis() - startTime;

        int total =
                logger.getMessageCount();

        System.out.println("\nStress test completed: "
                + completed);

        System.out.println(
                "Total messages: " + total
        );

        System.out.println(
                "Duration: " + duration + " ms"
        );

        System.out.println(
                "Throughput: "
                        + (total * 1000L / duration)
                        + " msg/sec"
        );

        logger.close();
    }
}
