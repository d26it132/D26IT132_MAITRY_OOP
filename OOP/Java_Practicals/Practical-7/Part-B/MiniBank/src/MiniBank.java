import java.util.Scanner;
import model.*;
import util.StatementFormatter;
import util.AnnotationValidator;
import service.CommandParser;
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
        // Perform initial account operations
        accounts[0].deposit(500);
        accounts[0].withdraw(200);
        accounts[1].deposit(1000);
        accounts[1].withdraw(300);
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
        System.out.println(
                StatementFormatter.buildStatement(accounts[0]));
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
        // Annotation validation test
        System.out.println("\n--- Annotation Validation Test ---");
        String[] errors = AnnotationValidator.validate(accounts[0]);
        if (errors.length == 0) {
            System.out.println("Account is valid.");
        } else {
            for (String error : errors) {
                System.out.println("Validation error: " + error);
            }
        }
        // Negative balance validation test
        System.out.println("\n--- Negative Balance Validation Test ---");
        try {
            Account invalidAccount =
                    new SavingsAccount("Test User", -100, 500);
            String[] validationErrors =
                    AnnotationValidator.validate(invalidAccount);
            if (validationErrors.length == 0) {
                System.out.println("Account is valid.");
            } else {
                for (String error : validationErrors) {
                    System.out.println("Validation error: " + error);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Account creation rejected: " + e.getMessage());
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