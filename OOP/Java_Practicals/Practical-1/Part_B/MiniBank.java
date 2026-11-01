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
            // Switch expression
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
