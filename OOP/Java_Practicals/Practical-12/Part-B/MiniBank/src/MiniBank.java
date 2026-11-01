
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import model.*;
import util.AnnotationValidator;
import util.StatementFormatter;
import util.BankResource;
import util.StatePersister;
import util.TransactionLog;
import util.ReportGenerator;
import service.CommandParser;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

import static util.Validator.isValidMobile;

public class MiniBank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankInfo bank = new BankInfo("MiniBank", "Ahmedabad");

        System.out.println("================================");
        System.out.println("       " + bank.name());
        System.out.println("       Branch: " + bank.branch());
        System.out.println("================================");

        // Create customers
        Customer c1 = new Customer(
                "Amit", "amit@gmail.com", "9876543210",
                new Customer.Address(
                        "ABC Road", "Ahmedabad", "380001"));

        Customer c2 = new Customer(
                "Neha", "neha@gmail.com", "9876543211",
                new Customer.Address(
                        "XYZ Road", "Surat", "395001"));

        Customer c3 = new Customer(
                "Rahul", "rahul@gmail.com", "9876543212",
                new Customer.Address(
                        "Main Road", "Vadodara", "390001"));

        System.out.println("\n===== Customers =====");
        System.out.println(c1.getCustomerId() + " " + c1.getName());
        System.out.println(c2.getCustomerId() + " " + c2.getName());
        System.out.println(c3.getCustomerId() + " " + c3.getName());

        // Create different account types
        Account[] accounts = {
            new SavingsAccount(c1.getName(), 1000, 500),
            new CurrentAccount(c2.getName(), 0, 2000),
            new FixedDepositAccount(c3.getName(), 2000)
        };

        
        // ===== Collections Framework: Milestone 2 =====

        System.out.println("\n===== Account Store =====");

        AccountStore store = new AccountStore();

        // Add accounts to HashMap-based store
        for (Account account : accounts) {
            store.add(account);
        }

        System.out.println("Total accounts: " + store.size());

        // Search for an account
        Account found = store.get(accounts[0].getAccountNumber());

        if (found != null) {
            System.out.println("Found account: " + found);
        }

        // Store transaction history
        store.addTransaction("DEPOSIT 500");
        store.addTransaction("WITHDRAW 200");
        store.addTransaction("TRANSFER 100");

        System.out.println("\nTransaction History:");
        for (String transaction : store.getTransactionHistory()) {
            System.out.println(transaction);
        }

        // Sort accounts by balance
        System.out.println("\n===== Accounts Sorted by Balance =====");

        for (Account account : store.sortedByBalance()) {
            System.out.println(
                account.getAccountNumber()
                + " | " + account.getOwnerName()
                + " | Balance: " + account.getBalance()
            );
        }

        // Demonstrate Comparable: sort by account number
        System.out.println("\n===== Accounts Sorted by Account Number =====");

        java.util.ArrayList<Account> byNumber = store.getAll();
        java.util.Collections.sort(byNumber);

        for (Account account : byNumber) {
            System.out.println(
                account.getAccountNumber() + " | " + account.getOwnerName()
            );
        }

        // Demonstrate ConcurrentHashMap in TransactionProcessor
        System.out.println("\n===== Thread-Safe Account Map =====");

        TransactionProcessor processor = new TransactionProcessor();

        for (Account account : accounts) {
            processor.addAccount(account);
        }

        System.out.println(
            "Accounts in concurrent map: " + processor.getAccounts().size()
        );

        System.out.println(
            "Retrieved account: "
            + processor.getAccount(accounts[0].getAccountNumber())
        );

        processor.stop();


        // Demonstrate deposit and withdrawal
        try {
            accounts[0].deposit(500);
            accounts[0].withdraw(200);

            accounts[1].deposit(1000);
            accounts[1].withdraw(300);

        } catch (BankException e) {
            System.out.println(
                    "Bank operation error: " + e.getMessage());
        } finally {
            System.out.println("Initial account operations completed.");
        }

        // Display accounts and interest
        System.out.println("\n===== Account Details =====");

        for (Account account : accounts) {
            System.out.println(account);
            System.out.println("Interest Rate: "
                    + account.interestRate() + "%");
            System.out.println("Yearly Interest: Rs. "
                    + String.format("%.2f", account.yearlyInterest()));
            System.out.println("----------------------------");
        }

        // WithdrawRule using an anonymous class
        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return account.canWithdraw(amount);
            }
        };

        // WithdrawRule using a lambda expression
        WithdrawRule lambdaRule =
                (account, amount) -> account.canWithdraw(amount);

        System.out.println("\n===== Withdraw Rules =====");

        System.out.println("Anonymous class allows withdrawal: "
                + anonymousRule.allow(accounts[0], 500));

        System.out.println("Lambda allows withdrawal: "
                + lambdaRule.allow(accounts[0], 500));

        System.out.println("Fixed deposit withdrawal allowed: "
                + lambdaRule.allow(accounts[2], 100));

        // Demonstrate marker interface
        System.out.println("\n===== Premium Check =====");

        if (accounts[0] instanceof Premium) {
            System.out.println("Savings account is marked Premium.");
        }

        // Demonstrate static import
        System.out.println("\n===== Mobile Validation =====");
        System.out.println("Mobile valid: "
                + isValidMobile("9876543210"));

        // Display statement
        System.out.println("\n===== Account Statement =====");
        System.out.println(StatementFormatter.buildStatement(accounts[0]));

        // Parse a command
        System.out.println("\n===== Command Parser =====");

        try {
            Command command =
                    CommandParser.parse("DEPOSIT AC0001 500");

            System.out.println("Type: " + command.type());
            System.out.println("Account: " + command.accountNumber());
            System.out.println("Amount: " + command.amount());

        } catch (IllegalArgumentException e) {
            System.out.println("Command error: " + e.getMessage());
        }

        // Annotation validation
        System.out.println("\n--- Annotation Validation Test ---");

        SavingsAccount testAccount =
                new SavingsAccount("Test User", -500, 100);

        String[] errors = AnnotationValidator.validate(testAccount);

        if (errors.length == 0) {
            System.out.println("Account is valid.");
        } else {
            for (String error : errors) {
                System.out.println("Validation Error: " + error);
            }
        }

        // Custom exception handling: insufficient funds
        System.out.println("\n===== Exception Handling =====");

        try {
            accounts[0].withdraw(5000);

        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
            System.out.println("Shortfall: Rs. " + e.getShortfall());

        } catch (InvalidAmountException e) {
            System.out.println("Invalid withdrawal: " + e.getMessage());

        } finally {
            System.out.println("Withdrawal attempt completed.");
        }

        // Invalid deposit test
        try {
            accounts[0].deposit(-100);

        } catch (InvalidAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());

        } finally {
            System.out.println("Deposit attempt completed.");
        }

        // Transfer test
        try {
            accounts[0].transfer(accounts[1], 200);

        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());

        } finally {
            System.out.println("Transfer attempt completed.");
        }

        // Try-with-resources demonstration
        System.out.println("\n===== Try-With-Resources =====");

        try (BankResource resource = new BankResource()) {
            resource.performTask();

        } catch (Exception e) {
            System.out.println("Resource error: " + e.getMessage());
        }

        // ==================================================
        // FILE PERSISTENCE, TRANSACTION LOGGING AND REPORTING
        // ==================================================

        System.out.println("\n===== File Persistence =====");

        Path stateFile = Path.of("data", "accounts.dat");
        Path logsFolder = Path.of("logs");
        Path logFile = logsFolder.resolve("transactions.log");
        Path reportFile = Path.of("reports", "end_of_day.txt");

        try {
            // 1. Save account array
            StatePersister.save(accounts, stateFile);
            System.out.println("Accounts saved successfully.");

            // 2. Load accounts into a new array
            Account[] loadedAccounts = StatePersister.load(stateFile);

            System.out.println("\n===== Reloaded Accounts =====");
            for (Account account : loadedAccounts) {
                System.out.println(account);
            }

            // 3. Create sample logs only if the log file is new.
            // In actual transactions, append only after successful operations.
            Files.createDirectories(logsFolder);

            if (!Files.exists(logFile)) {
                TransactionLog.append(logFile, "DEPOSIT 500");
                TransactionLog.append(logFile, "WITHDRAW 100");
                TransactionLog.append(logFile, "DEPOSIT 250");
            }

            // 4. Generate the end-of-day report
            System.out.println("\n===== End-of-Day Report =====");
            ReportGenerator.generate(logsFolder, reportFile);

            System.out.println("Report saved to: "
                    + reportFile.toAbsolutePath());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "File operation failed: " + e.getMessage());
        }

        // Menu shell
        int choice = 0;

        do {
            System.out.println("\n===== MiniBank Menu =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a number.");
                sc.next();
                continue;
            }

            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice! Please try again.");
                continue;
            }

            MenuOption option = MenuOption.values()[choice - 1];

            String message = switch (option) {
                case OPEN_ACCOUNT ->
                    "Open Account - to be implemented in a later lab";
                case DEPOSIT ->
                    "Deposit - to be implemented in a later lab";
                case WITHDRAW ->
                    "Withdraw - to be implemented in a later lab";
                case TRANSFER ->
                    "Transfer - to be implemented in a later lab";
                case EXIT ->
                    "Thank you for using MiniBank!";
            };

            System.out.println(message);

        } while (choice != 5);

        sc.close();
    }

    enum MenuOption {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
    }
}
