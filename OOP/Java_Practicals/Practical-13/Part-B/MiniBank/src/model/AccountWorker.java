
package model;

import exception.BankException;

public class AccountWorker implements Runnable {

    private Account account;
    private int times;
    private long amount;

    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName + " started.");

        try {
            for (int i = 0; i < times; i++) {
                account.deposit(amount);
            }
        } catch (BankException e) {
            System.out.println(threadName + " error: " + e.getMessage());
        }

        System.out.println(threadName + " finished.");
    }
}
