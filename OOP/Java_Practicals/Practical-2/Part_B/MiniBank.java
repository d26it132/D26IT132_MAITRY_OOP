import java.util.Scanner;
public class MiniBank {
    // Record to store bank information
    record BankInfo(String name, String branch) {}
    // Enum for menu options
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Display application header
        BankInfo bank = new BankInfo("MiniBank", "Ahmedabad");
        System.out.println("================================");
        System.out.println("       " + bank.name());
        System.out.println("       Branch: " + bank.branch());
        System.out.println("================================");
        // Create Customer objects
        Customer c1 = new Customer("Amit", "amit@gmail.com", "9876543210");
        Customer c2 = new Customer("Neha", "neha@gmail.com", "9876543211");
        Customer c3 = new Customer("Rahul", "rahul@gmail.com", "9876543212");
        System.out.println("\n===== Customer Details =====");
        System.out.println(c1.getCustomerId() + " " + c1.getName());
        System.out.println(c2.getCustomerId() + " " + c2.getName());
        System.out.println(c3.getCustomerId() + " " + c3.getName());
        // Create three Account objects inside an array
        Account[] accounts = {
            new Account(c1.getName(), 1000),
            new Account(c2.getName()),
            new Account(c3.getName(), 2000)
        };
        // Perform deposits and withdrawals
        accounts[0].deposit(500);
        accounts[0].withdraw(200);
        accounts[1].deposit(1000);
        accounts[1].withdraw(300);
        accounts[2].deposit(500);
        accounts[2].withdraw(3000); // Insufficient balance
        // Display account balances
        System.out.println("\n===== Account Details =====");
        for (Account account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Owner: " + account.getOwnerName());
            System.out.println("Balance: Rs. " + account.getBalance());
            System.out.println("Active: " + account.isActive());
            System.out.println("----------------------------");
        }
        int choice;
        // Display menu until the user selects EXIT
        do {
            System.out.println("\n===== MiniBank Menu =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
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
}
// Customer class
class Customer {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private static long customerCounter = 100;
    // Generate unique customer ID
    private static String generateCustomerId() {
        return "CUST" + (++customerCounter);
    }
    // Constructor
    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }
    // Getter methods
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getMobile() {
        return mobile;
    }
    public String getCustomerId() {
        return customerId;
    }
}
// Account class
class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    private static long accountCounter = 0;
    // Generate unique account number
    private static String generateAccountNumber() {
        return String.format("AC%04d", ++accountCounter);
    }
    // Constructor with opening balance
    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = Math.max(0, openingBalance);
        this.active = true;
    }
    // Constructor with default balance of zero
    public Account(String ownerName) {
        this(ownerName, 0);
    }
    // Deposit money
    public void deposit(long amount) {
        if (amount > 0 && active) {
            balance += amount;
        } else {
            System.out.println("Invalid deposit amount or inactive account.");
        }
    }
    // Withdraw money
    public boolean withdraw(long amount) {
        if (amount > 0 && active && balance >= amount) {
            balance -= amount;
            return true;
        }
        System.out.println("Withdrawal failed: insufficient balance or invalid amount.");
        return false;
    }
    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public long getBalance() {
        return balance;
    }
    public boolean isActive() {
        return active;
    }
}