import java.util.HashMap;
import java.util.Map;

// Custom checked exception
class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(int shortfall) {
        super("Out of stock. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

// Custom checked exception
class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

// Warehouse class
class Warehouse {
    private Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("Pen", 10);
        stock.put("Book", 5);
        stock.put("Bag", 3);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity must be greater than 0."
            );
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            int shortfall = qty - available;
            throw new OutOfStockException(shortfall);
        }

        stock.put(item, available - qty);

        System.out.println("Issued " + qty + " " + item);
    }
}

// Main class
public class StockIssue {
    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        // List of requests
        String[] items = {"Pen", "Book", "Bag", "Pen", "Book"};
        int[] quantities = {5, 10, 0, 8, -2};

        for (int i = 0; i < items.length; i++) {

            try {
                System.out.println(
                    "Request: " + items[i] + " - " + quantities[i]
                );

                warehouse.issue(items[i], quantities[i]);

            } catch (OutOfStockException e) {

                System.out.println(
                    "Failure: " + e.getMessage()
                );

            } catch (InvalidQuantityException e) {

                System.out.println(
                    "Failure: " + e.getMessage()
                );
            }

            // The loop continues even when an exception occurs
            System.out.println("--------------------");
        }

        System.out.println("All requests processed.");
    }
}