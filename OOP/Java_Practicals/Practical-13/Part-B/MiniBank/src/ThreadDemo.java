
import model.Account;
import model.AccountWorker;
import model.SavingsAccount;

public class ThreadDemo {

    public static void main(String[] args) {

        Account account = new SavingsAccount("Thread Test", 0, 0);

        int numberOfThreads = 10;
        int times = 100000;
        long amount = 1;

        Thread[] threads = new Thread[numberOfThreads];

        System.out.println("Starting deposit threads...");

        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(
                new AccountWorker(account, times, amount),
                "Thread-" + (i + 1)
            );

            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Main thread interrupted.");
                return;
            }
        }

        long expectedBalance = (long) numberOfThreads * times * amount;

        System.out.println("\nExpected balance: " + expectedBalance);
        System.out.println("Actual balance: " + account.getBalance());
        System.out.println("All threads completed.");
    }
}
