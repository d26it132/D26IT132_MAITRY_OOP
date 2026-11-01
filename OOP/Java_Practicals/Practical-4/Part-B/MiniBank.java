import java.util.Scanner;
import java.util.Objects;
import java.util.regex.Pattern;
// Main class
public class MiniBank {
    record BankInfo(String name, String branch) {}
    enum MenuOption {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
    }
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
                new Customer.Address("ABC Road", "Ahmedabad", "380001"));
        Customer c2 = new Customer(
                "Neha", "neha@gmail.com", "9876543211",
                new Customer.Address("XYZ Road", "Surat", "395001"));
        Customer c3 = new Customer(
                "Rahul", "rahul@gmail.com", "9876543212",
                new Customer.Address("Main Road", "Vadodara", "390001"));
        System.out.println("\n===== Customer Details =====");
        System.out.println(c1.getCustomerId() + " " + c1.getName());
        System.out.println(c2.getCustomerId() + " " + c2.getName());
        System.out.println(c3.getCustomerId() + " " + c3.getName());
        // Test nested Address
        Customer.Address address = c1.getAddress();
        System.out.println("Address: " + address.getLine() + ", "
                + address.getCity() + ", " + address.getPincode());
        // Test clone()
        Customer clonedCustomer = c1.clone();
        System.out.println("\nCloned Customer: "
                + clonedCustomer.getCustomerId() + " "
                + clonedCustomer.getName());
        // Create accounts
        Account[] accounts = {
            new Account(c1.getName(), 1000),
            new Account(c2.getName()),
            new Account(c3.getName(), 2000)
        };
        // Perform transactions
        accounts[0].deposit(500);
        accounts[0].withdraw(200);
        accounts[1].deposit(1000);
        accounts[1].withdraw(300);
        accounts[2].deposit(500);
        accounts[2].withdraw(3000);
        // Print accounts
        System.out.println("\n===== Account Details =====");
        for (Account account : accounts) {
            System.out.println(account);
        }
        // Test equals()
        System.out.println("\n===== Account Comparison =====");
        System.out.println("Account 1 equals Account 2: "
                + accounts[0].equals(accounts[1]));
        // Test instanceof
        Object obj = accounts[0];
        System.out.println("Is object an Account? "
                + (obj instanceof Account));
        // Test validators
        System.out.println("\n===== Validator Tests =====");
        System.out.println("Valid Mobile: "
                + Validator.isValidMobile("9876543210"));
        System.out.println("Invalid Mobile: "
                + Validator.isValidMobile("12345"));
        System.out.println("Valid Email: "
                + Validator.isValidEmail("amit@gmail.com"));
        System.out.println("Invalid Email: "
                + Validator.isValidEmail("amit@"));
        System.out.println("Valid PAN: "
                + Validator.isValidPan("ABCDE1234F"));
        System.out.println("Invalid PAN: "
                + Validator.isValidPan("ABC123"));
        System.out.println("Valid IFSC: "
                + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("Invalid IFSC: "
                + Validator.isValidIfsc("SB123"));
        // Test command parser
        System.out.println("\n===== Command Parser =====");
        Command command = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Type: " + command.type());
        System.out.println("Account Number: " + command.accountNumber());
        System.out.println("Amount: " + command.amount());
        // Test statement formatter
        System.out.println("\n===== Account Statement =====");
        System.out.println(
                StatementFormatter.buildStatement(accounts[0]));
        // Original menu
        int choice;
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
class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address;
    private static long customerCounter = 100;
    public static class Address {
        private String line;
        private String city;
        private String pincode;
        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }
        public String getLine() {
            return line;
        }
        public String getCity() {
            return city;
        }
        public String getPincode() {
            return pincode;
        }
    }
    private static String generateCustomerId() {
        return "CUST" + (++customerCounter);
    }
    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, null);
    }
    public Customer(String name, String email, String mobile,
                    Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.customerId = generateCustomerId();
    }
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
    public Address getAddress() {
        return address;
    }
    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
// Account class
class Account {
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
    public void deposit(long amount) {
        if (amount > 0 && active) {
            balance += amount;
        } else {
            System.out.println(
                    "Invalid deposit amount or inactive account.");
        }
    }
    public boolean withdraw(long amount) {
        if (amount > 0 && active && balance >= amount) {
            balance -= amount;
            return true;
        }
        System.out.println(
                "Withdrawal failed: insufficient balance or invalid amount.");
        return false;
    }
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
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
// Validator class
class Validator {
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("[6-9][0-9]{9}");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PAN_PATTERN =
            Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]");
    private static final Pattern IFSC_PATTERN =
            Pattern.compile("[A-Z]{4}0[A-Z0-9]{6}");
    public static boolean isValidMobile(String mobile) {
        return mobile != null
                && MOBILE_PATTERN.matcher(mobile).matches();
    }
    public static boolean isValidEmail(String email) {
        return email != null
                && EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidPan(String pan) {
        return pan != null
                && PAN_PATTERN.matcher(pan).matches();
    }
    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null
                && IFSC_PATTERN.matcher(ifsc).matches();
    }
}
// Transaction types
enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}
// Command record
record Command(
        TransactionType type,
        String accountNumber,
        long amount
) {}
// Command parser
class CommandParser {
    public static Command parse(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Command must contain type, account number and amount.");
        }
        TransactionType type =
                TransactionType.valueOf(parts[0].toUpperCase());
        String accountNumber = parts[1];
        long amount = Long.parseLong(parts[2]);
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be positive.");
        }
        if (!accountNumber.matches("AC[0-9]{4}")) {
            throw new IllegalArgumentException(
                    "Invalid account number.");
        }
        return new Command(type, accountNumber, amount);
    }
}
// Statement formatter
class StatementFormatter {
    public static String buildStatement(Account account) {
        StringBuilder statement = new StringBuilder();
        statement.append("========== MiniBank Statement ==========\n");
        statement.append("Account Number: ")
                .append(account.getAccountNumber()).append("\n");
        statement.append("Account Holder: ")
                .append(account.getOwnerName()).append("\n");
        statement.append("Balance: Rs. ")
                .append(account.getBalance()).append("\n");
        statement.append("Account Active: ")
                .append(account.isActive()).append("\n");
        statement.append("========================================");
        return statement.toString();
    }
}