package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import exception.AccountNotFoundException;
import java.util.Objects;
import model.annotation.Id;
import model.annotation.Positive;
import model.annotation.MaxLength;
import java.io.Serializable;

public abstract class Account
        implements Transactable, InterestBearing, Comparable<Account>, Serializable {

    private static final long serialVersionUID = 1L;
    
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        return String.format("AC%04d", ++accountCounter);
    }

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = Math.max(0, openingBalance);
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }
@Override
public synchronized void deposit(long amount) throws InvalidAmountException {
    if (amount <= 0) {
        throw new InvalidAmountException(
            "Deposit amount must be greater than zero."
        );
    }

    balance += amount;
}

    public abstract boolean canWithdraw(long amount);


@Override
public int compareTo(Account other) {
    return this.getAccountNumber()
            .compareTo(other.getAccountNumber());
}


@Override
public synchronized boolean withdraw(long amount)
        throws InsufficientFundsException, InvalidAmountException {

    if (amount <= 0) {
        throw new InvalidAmountException(
            "Withdrawal amount must be greater than zero."
        );
    }

    if (!canWithdraw(amount)) {
        long shortfall = Math.max(0L, amount - balance);

        throw new InsufficientFundsException(
            "Insufficient funds. Withdrawal cannot be completed.",
            shortfall
        );
    }

    balance -= amount;
    return true;
}

public void transfer(Account to, long amount) throws BankException {
    try {
        if (to == null) {
            throw new AccountNotFoundException(
                "Destination account was not found."
            );
        }

        if (to == this) {
            throw new BankException(
                "Cannot transfer to the same account."
            );
        }

        withdraw(amount);
        to.deposit(amount);

        System.out.println("Transfer successful.");

    } catch (BankException e) {
        throw e;

    } finally {
        System.out.println("Transfer operation completed.");
    }
}

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: Rs. " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Account account)) {
            return false;
        }

        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    
public void transferSafelyTo(Account to, long amount)
        throws BankException {

    if (to == null || to == this) {
        throw new IllegalArgumentException(
            "Destination must be a different account"
        );
    }

    Account first;
    Account second;

    if (this.getAccountNumber().compareTo(to.getAccountNumber()) < 0) {
        first = this;
        second = to;
    } else {
        first = to;
        second = this;
    }

    synchronized (first) {
        synchronized (second) {
            boolean success = this.withdraw(amount);

            if (success) {
                to.deposit(amount);
            }
        }
    }
}

}