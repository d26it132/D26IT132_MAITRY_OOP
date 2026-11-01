import java.util.Scanner;
import java.util.Objects;
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
        // Create Customer objects with addresses
        Customer c1 = new Customer("Amit", "amit@gmail.com",
                "9876543210",
                new Customer.Address("ABC Road", "Ahmedabad", "380001"));
        Customer c2 = new Customer("Neha", "neha@gmail.com",
                "9876543211",
                new Customer.Address("XYZ Road", "Surat", "395001"));
        Customer c3 = new Customer("Rahul", "rahul@gmail.com",
                "9876543212",
                new Customer.Address("Main Road", "Vadodara", "390001"));
        System.out.println("\n===== Customer Details =====");
        System.out.println(c1.getCustomerId() + " " + c1.getName());
        System.out.println(c2.getCustomerId() + " " + c2.getName());
        System.out.println(c3.getCustomerId() + " " + c3.getName());
        // Display nested Address details
        Customer.Address address = c1.getAddress();
        System.out.println("Address: " + address.getLine() + ", "
                + address.getCity() + ", " + address.getPincode());
        // Clone a customer
        Customer clonedCustomer = c1.clone();
        System.out.println("\n===== Cloned Customer =====");
        System.out.println(clonedCustomer.getCustomerId() + " "
                + clonedCustomer.getName());
        // Create three accounts
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
        accounts[2].withdraw(3000);
        // Print accounts using toString()
        System.out.println("\n===== Account Details =====");
        for (Account account : accounts) {
            System.out.println(account);
        }
        // Compare accounts using equals()
        System.out.println("\n===== Account Comparison =====");
        System.out.println("Account 1 equals Account 2: "
                + accounts[0].equals(accounts[1]));
        System.out.println("Account 1 equals itself: "
                + accounts[0].equals(accounts[0]));
        // Check object type using instanceof
        System.out.println("\n===== Type Checking =====");
        Object obj = accounts[0];
        if (obj instanceof Account) {
            System.out.println("The object is an Account.");
        } else {
            System.out.println("The object is not an Account.");
        }
        int choice;
        // Display menu until EXIT is selected
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
    // Nested Address class
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
    // Generate customer ID
    private static String generateCustomerId() {
        return "CUST" + (++customerCounter);
    }
    // Constructor without address
    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, null);
    }
    // Constructor with address
    public Customer(String name, String email, String mobile,
                    Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
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
    public Address getAddress() {
        return address;
    }
    // Clone customer
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
    // Generate account number
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
    // Constructor with zero opening balance
    public Account(String ownerName) {
        this(ownerName, 0);
    }
    // Deposit money
    public void deposit(long amount) {
        if (amount > 0 && active) {
            balance += amount;
        } else {
            System.out.println(
                    "Invalid deposit amount or inactive account.");
        }
    }
    // Withdraw money
    public boolean withdraw(long amount) {
        if (amount > 0 && active && balance >= amount) {
            balance -= amount;
            return true;
        }
        System.out.println(
                "Withdrawal failed: insufficient balance or invalid amount.");
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
    // Convert account details to readable text
    @Override
    public String toString() {
        return "Account Number: " + accountNumber
                + ", Owner: " + ownerName
                + ", Balance: Rs. " + balance;
    }
    // Compare accounts by account number
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
    // Generate hash code using account number
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}