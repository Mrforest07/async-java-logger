import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {

    private final String filename;
    private BufferedWriter writer;
    private int entryCount = 0;

    public FileWriter(String filename) throws IOException {
        this.filename = filename;
        createWriter();

        System.out.println(
                "✓ FileWriter initialized: " + filename
        );
    }

    private void createWriter() throws IOException {

        writer = new BufferedWriter(
                new java.io.FileWriter(filename, true),
                8192
        );
    }

    public synchronized void write(String text)
            throws IOException {

        writer.write(text);
        writer.newLine();

        entryCount++;

        // Flush every 100 entries
        if (entryCount % 100 == 0) {
            writer.flush();
        }
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

    public int getEntryCount() {
        return entryCount;
    }
}