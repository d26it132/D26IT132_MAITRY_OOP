
import model.Account;
import model.SavingsAccount;
import exception.BankException;

public class DeadlockPreventionDemo {

    public static void main(String[] args) {

        Account a = new SavingsAccount("Customer A", 1000, 0);
        Account b = new SavingsAccount("Customer B", 1000, 0);

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    a.transferSafelyTo(b, 10);
                }
                System.out.println("A to B completed.");
            } catch (BankException e) {
                System.out.println(e.getMessage());
            }
        }, "Transfer-A-B");

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    b.transferSafelyTo(a, 10);
                }
                System.out.println("B to A completed.");
            } catch (BankException e) {
                System.out.println(e.getMessage());
            }
        }, "Transfer-B-A");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Account A: " + a.getBalance());
        System.out.println("Account B: " + b.getBalance());
    }
}
