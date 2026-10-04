public class DailyFileWriterTest {

    public static void main(String[] args)
            throws Exception {

        DailyFileWriter writer =
                new DailyFileWriter();

        // Today's log
        writer.write("Message before date change");
        writer.write("Another message before date change");

        // Pretend the day changed
        writer.simulateNextDayForTest();

        // This write should trigger rotation
        writer.write("Message after date change");
        writer.write("Another message after date change");

        writer.flush();
        writer.close();

        System.out.println("Done");
    }
}