public class TestFileWriter {

    public static void main(String[] args)
            throws Exception {

        FileWriter writer =
                new FileWriter("test.log");

        writer.write("First message");
        writer.write("Second message");
        writer.write("Third message");

        writer.flush();

        System.out.println(
                "Entries: " + writer.getEntryCount()
        );

        writer.close();
    }
}
