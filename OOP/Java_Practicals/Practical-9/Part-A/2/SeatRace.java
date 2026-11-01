class SeatBooking {
    private int seatsLeft = 5;
    private int booked = 0;
    public void book(String name) {
        if (seatsLeft > 0) {
            // Small delay makes the race condition easier to observe
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            seatsLeft--;
            booked++;
            System.out.println(name + " booked a seat");
        } else {
            System.out.println(name + " failed - no seats");
        }
    }
    public int getBooked() {
        return booked;
    }
}
class BookingThread extends Thread {
    private SeatBooking booking;
    BookingThread(SeatBooking booking) {
        this.booking = booking;
    }
    public void run() {
        booking.book(Thread.currentThread().getName());
    }
}
public class SeatRace {
    public static void main(String[] args) throws Exception {
        SeatBooking booking = new SeatBooking();
        BookingThread[] threads = new BookingThread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new BookingThread(booking);
            threads[i].setName("Customer-" + (i + 1));
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        System.out.println("\nTotal successful bookings: "
                + booking.getBooked());
    }
}