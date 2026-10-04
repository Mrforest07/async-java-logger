import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DailyFileWriter {

    private BufferedWriter writer;
    private LocalDate currentDate;

    public DailyFileWriter() throws IOException {
        openWriter();
    }

    private void openWriter() throws IOException {

        currentDate = LocalDate.now();

        String filename =
                "application-" +
                        currentDate +
                        ".log";

        writer = Files.newBufferedWriter(
                Path.of(filename),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

        System.out.println(
                "Opened log file: " + filename
        );
    }

    public synchronized void write(String text)
            throws IOException {

        LocalDate today = LocalDate.now();

        if (!today.equals(currentDate)) {
            rotate();
        }

        writer.write(text);
        writer.newLine();
    }

    private void rotate() throws IOException {

        System.out.println("Rotating log file...");

        writer.flush();
        writer.close();

        openWriter();
    }

    public synchronized void flush()
            throws IOException {

        writer.flush();
    }

    public synchronized void close()
            throws IOException {

        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    // Testing method
    public void simulateNextDayForTest() {

        currentDate =
                currentDate.minusDays(1);

        System.out.println(
                "Simulated date change. Current file date is now: "
                        + currentDate
        );
    }
}