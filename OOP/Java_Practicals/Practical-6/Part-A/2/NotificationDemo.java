@FunctionalInterface
interface Notifier {
    void send(String message);
}
// Marker interface
interface Urgent {
}
// Email sender
class EmailNotifier implements Notifier, Urgent {
    private final Notifier sender;
    EmailNotifier(Notifier sender) {
        this.sender = sender;
    }
    @Override
    public void send(String message) {
        sender.send(message);
    }
}
// SMS sender
class SMSNotifier implements Notifier {
    private final Notifier sender;
    SMSNotifier(Notifier sender) {
        this.sender = sender;
    }
    @Override
    public void send(String message) {
        sender.send(message);
    }
}
public class NotificationDemo {
    public static void main(String[] args) {
        // Email as lambda
        Notifier emailLambda =
                message -> System.out.println("Email: " + message);
        // SMS as lambda
        Notifier smsLambda =
                message -> System.out.println("SMS: " + message);
        // Wrap them
        Notifier email = new EmailNotifier(emailLambda);
        Notifier sms = new SMSNotifier(smsLambda);
        // Array containing both senders
        Notifier[] notifiers = {
            email,
            sms
        };
        String message = "Exam starts at 10 AM.";
        // Broadcast
        for (Notifier notifier : notifiers) {
            notifier.send(message);
            // Send twice if marked Urgent
            if (notifier instanceof Urgent) {
                notifier.send(message);
            }
        }
    }
}