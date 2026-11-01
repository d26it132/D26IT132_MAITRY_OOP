interface Switchable {
    void on();
    void off();
    default void toggle() {
        on();
    }
}
class Fan implements Switchable {
    String name;
    boolean isOn = false;
    Fan(String name) {
        this.name = name;
    }
    @Override
    public void on() {
        isOn = true;
        System.out.println(name + " is ON");
    }
    @Override
    public void off() {
        isOn = false;
        System.out.println(name + " is OFF");
    }
}
class Light implements Switchable {
    String name;
    boolean isOn = false;
    Light(String name) {
        this.name = name;
    }
    @Override
    public void on() {
        isOn = true;
        System.out.println(name + " is ON");
    }
    @Override
    public void off() {
        isOn = false;
        System.out.println(name + " is OFF");
    }
}
// Functional Interface
@FunctionalInterface
interface SwitchPolicy {
    boolean maySwitchOn(Switchable device, int hour);
}
public class RemoteControl {
    public static void main(String[] args) {
        // Mixed array of devices
        Switchable[] devices = {
            new Fan("Fan"),
            new Light("Light")
        };
        // Toggle each device
        System.out.println("Toggling Devices:");
        for (Switchable device : devices) {
            device.toggle();
        }
        // Anonymous class
        SwitchPolicy anonymousPolicy = new SwitchPolicy() {
            @Override
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };
        // Lambda
        SwitchPolicy lambdaPolicy =
                (device, hour) -> hour >= 8 && hour <= 20;

        System.out.println("\nAnonymous Class Policy:");
        int hour = 10;
        for (Switchable device : devices) {
            System.out.println(
                "Can switch on at " + hour + ":00? "
                + anonymousPolicy.maySwitchOn(device, hour)
            );
        }
        System.out.println("\nLambda Policy:");
        for (Switchable device : devices) {
            System.out.println(
                "Can switch on at " + hour + ":00? "
                + lambdaPolicy.maySwitchOn(device, hour)
            );
        }
    }
}