import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {

        String[] logs = {
            "10:05 alice Hello there",
            "10:10 bob How are you?",
            "10:15 Charlie This line has no message"
        };

        // Add a malformed line
        String[] logsWithMalformed = {
            "10:05 alice Hello there",
            "10:10 bob How are you?",
            "malformed",
            "10:15 charlie Good morning"
        };

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        String result = ChatFilter.filterLogs(logsWithMalformed, keyword);

        System.out.println(result);

        sc.close();
    }
}