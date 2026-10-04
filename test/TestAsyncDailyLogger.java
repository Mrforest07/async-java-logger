import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;

public class TestAsyncDailyLogger {

    public static void main(String[] args) throws Exception {

        BlockingQueue<LogEntry> queue =
                new LinkedBlockingQueue<>();

        // IMPORTANT: DailyFileWriter, NOT FileWriter
        DailyFileWriter fileWriter =
                new DailyFileWriter();

        AsyncLogWriter writer =
                new AsyncLogWriter(
                        queue,
                        fileWriter,
                        "FULL"
                );

        Thread writerThread =
                new Thread(
                        writer,
                        "Log-Writer"
                );

        writerThread.start();

        // Add 5 log messages
        for (int i = 1; i <= 5; i++) {

            LogEntry entry =
                    new LogEntry(
                            LogLevel.INFO,
                            Thread.currentThread().getName(),
                            "TestAsyncDailyLogger",
                            i,
                            "Message " + i
                    );

            queue.put(entry);
        }

        System.out.println(
                "Messages added: " + queue.size()
        );

        // Shutdown
        writer.shutdown();

        // Wait for writer to finish remaining logs
        writerThread.join();

        // Close daily file
        fileWriter.close();

        System.out.println("Done");
    }
}