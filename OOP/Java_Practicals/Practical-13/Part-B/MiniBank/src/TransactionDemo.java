
import model.Account;
import model.SavingsAccount;
import model.TransactionProcessor;
import exception.BankException;

public class TransactionDemo {

    public static void main(String[] args) {

        Account account =
                new SavingsAccount("Test User", 1000, 0);

        TransactionProcessor processor =
                new TransactionProcessor();

        for (int i = 1; i <= 10; i++) {
            final int transactionId = i;

            processor.submit(() -> {
                try {
                    account.deposit(100);
                    System.out.println(
                        Thread.currentThread().getName()
                        + " - Deposit 100, transaction "
                        + transactionId
                    );
                } catch (BankException e) {
                    System.out.println("Deposit failed: " + e.getMessage());
                }
            });
        }

        for (int i = 1; i <= 10; i++) {
            final int transactionId = i;

            processor.submit(() -> {
                try {
                    account.withdraw(100);
                    System.out.println(
                        Thread.currentThread().getName()
                        + " - Withdrawal 100, transaction "
                        + transactionId
                    );
                } catch (BankException e) {
                    System.out.println("Withdrawal failed: " + e.getMessage());
                }
            });
        }

        processor.stop();

        System.out.println("Final balance: " + account.getBalance());
    }
}
