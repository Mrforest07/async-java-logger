import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerDemo {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        // Producer 1
        Thread producer1 = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String message = "Producer-1 Log-" + i;

                    queue.put(message);

                    System.out.println("Produced: " + message);

                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Producer 2
        Thread producer2 = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String message = "Producer-2 Log-" + i;

                    queue.put(message);

                    System.out.println("Produced: " + message);

                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {

                    String message = queue.take();

                    System.out.println("Consumed: " + message);

                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer1.start();
        producer2.start();
        consumer.start();

        producer1.join();
        producer2.join();
        consumer.join();

        System.out.println("Done");
    }
}