package util;

public class BankResource implements AutoCloseable {

    public BankResource() {
        System.out.println("Bank resource opened.");
    }

    public void performTask() {
        System.out.println("Performing banking operation.");
    }

    @Override
    public void close() {
        System.out.println("Bank resource closed automatically.");
    }
}