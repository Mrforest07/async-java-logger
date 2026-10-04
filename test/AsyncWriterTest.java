import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncWriterTest {

    public static void main(String[] args) throws Exception {

        BlockingQueue<LogEntry> queue =
                new LinkedBlockingQueue<>();

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

        for (int i = 1; i <= 5; i++) {

            LogEntry entry =
                    new LogEntry(
                            LogLevel.INFO,
                            Thread.currentThread().getName(),
                            "AsyncWriterTest",
                            20 + i,
                            "Message " + i
                    );

            queue.put(entry);

            System.out.println(
                    "Main Thread added: Message " + i
            );
        }

        writer.shutdown();

        writerThread.join();

        fileWriter.close();

        System.out.println("Done");
    }
}