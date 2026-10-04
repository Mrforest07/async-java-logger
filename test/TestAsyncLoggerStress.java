import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestAsyncLoggerStress {

    public static void main(String[] args) throws Exception {

        // Today's log file
        Path logPath = Path.of(
                "application-" +
                        LocalDate.now() +
                        ".log"
        );

        // Delete old file so this run starts fresh
        Files.deleteIfExists(logPath);

        // Queue
        BlockingQueue<LogEntry> queue =
                new LinkedBlockingQueue<>();

        // Daily writer
        DailyFileWriter fileWriter =
                new DailyFileWriter();

        // Async writer
        AsyncLogWriter writer =
                new AsyncLogWriter(
                        queue,
                        fileWriter,
                        "FULL"
                );

        // Background writer thread
        Thread writerThread =
                new Thread(
                        writer,
                        "Log-Writer"
                );

        writerThread.start();

        // 10 producer threads
        ExecutorService executor =
                Executors.newFixedThreadPool(10);

        int numberOfThreads = 10;
        int messagesPerThread = 1000;

        // Produce logs
        for (int i = 1; i <= numberOfThreads; i++) {

            int threadNumber = i;

            executor.submit(() -> {

                try {

                    for (int j = 1;
                         j <= messagesPerThread;
                         j++) {

                        LogEntry entry =
                                new LogEntry(
                                        LogLevel.INFO,
                                        Thread.currentThread().getName(),
                                        "TestAsyncLoggerStress",
                                        j,
                                        "Thread-" +
                                                threadNumber +
                                                " Message-" +
                                                j
                                );

                        queue.put(entry);
                    }

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                }
            });
        }

        // Stop accepting new tasks
        executor.shutdown();

        // Wait for all producers
        boolean finished =
                executor.awaitTermination(
                        30,
                        TimeUnit.SECONDS
                );

        System.out.println(
                "Producers finished: " + finished
        );

        int expectedMessages =
                numberOfThreads *
                        messagesPerThread;

        System.out.println(
                "Expected messages: " +
                        expectedMessages
        );

        System.out.println(
                "Queue before shutdown: " +
                        queue.size()
        );

        // Shutdown async writer
        writer.shutdown();

        // Wait until queue is completely drained
        writerThread.join();

        // Close file
        fileWriter.close();

        // Count lines in today's log
        long writtenMessages =
                Files.lines(logPath).count();

        long dataLoss =
                expectedMessages -
                        writtenMessages;

        System.out.println(
                "Written messages: " +
                        writtenMessages
        );

        System.out.println(
                "Data loss: " +
                        dataLoss
        );

        if (writtenMessages == expectedMessages) {

            System.out.println(
                    "✓ ZERO DATA LOSS"
            );

        } else {

            System.out.println(
                    "✗ DATA LOSS DETECTED"
            );
        }
    }
}