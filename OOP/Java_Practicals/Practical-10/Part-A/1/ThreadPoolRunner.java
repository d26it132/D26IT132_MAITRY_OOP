import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolRunner {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {

            int taskId = i;

            executor.submit(() -> {
                System.out.println("Task " + taskId +
                        " running on " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Task " + taskId +
                        " completed by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();

        try {
            if (executor.awaitTermination(30, java.util.concurrent.TimeUnit.SECONDS)) {
                System.out.println("All tasks completed.");
            } else {
                System.out.println("Timeout: Some tasks are still running.");
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }
    }
}