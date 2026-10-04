import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AsyncLogWriter implements Runnable {

    private final BlockingQueue<LogEntry> queue;
    private final DailyFileWriter fileWriter;
    private final String format;

    private volatile boolean running = true;

    public AsyncLogWriter(
            BlockingQueue<LogEntry> queue,
            DailyFileWriter fileWriter,
            String format) {

        this.queue = queue;
        this.fileWriter = fileWriter;
        this.format = format;
    }

    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {

        try {

            while (running || !queue.isEmpty()) {

                LogEntry entry =
                        queue.poll(
                                100,
                                TimeUnit.MILLISECONDS
                        );

                if (entry == null) {
                    continue;
                }

                String formatted =
                        LogFormatter.format(
                                entry,
                                format
                        );

                fileWriter.write(formatted);
            }

            fileWriter.flush();

            System.out.println(
                    "Writer finished. Queue is empty."
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "Writer thread interrupted."
            );

        } catch (IOException e) {

            System.err.println(
                    "Error writing log: "
                            + e.getMessage()
            );
        }
    }
}