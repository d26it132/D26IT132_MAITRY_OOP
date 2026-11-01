
import model.Account;
import model.SavingsAccount;
import model.TransactionBuffer;
import exception.BankException;

public class ProducerConsumerDemo {

    private static final Runnable STOP = () -> {};

    public static void main(String[] args) {

        Account account =
                new SavingsAccount("Producer Consumer", 1000, 0);

        TransactionBuffer buffer = new TransactionBuffer(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    final int id = i;

                    buffer.put(() -> {
                        try {
                            account.deposit(100);
                            System.out.println(
                                "Processed transaction " + id
                            );
                        } catch (BankException e) {
                            System.out.println(e.getMessage());
                        }
                    });

                    System.out.println("Produced transaction " + i);
                }

                buffer.put(STOP);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Runnable task = buffer.take();

                    if (task == STOP) {
                        break;
                    }

                    task.run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final balance: " + account.getBalance());
    }
}
