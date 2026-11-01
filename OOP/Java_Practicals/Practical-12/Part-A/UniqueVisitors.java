import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {
        String[] logins = {
            "Amit", "Priya", "Rahul", "Amit",
            "Neha", "Priya", "Karan"
        };
        HashSet<String> set = new HashSet<>();
        LinkedHashSet<String> orderedSet = new LinkedHashSet<>();
        for (String name : logins) {
            set.add(name);
            orderedSet.add(name);
        }
        System.out.println("Unique Visitors: " + set.size());
        System.out.println("First Three Distinct Names:");
        int count = 0;
        for (String name : orderedSet) {
            if (count == 3) {
                break;
            }
            System.out.println(name);
            count++;
        }
    }
}