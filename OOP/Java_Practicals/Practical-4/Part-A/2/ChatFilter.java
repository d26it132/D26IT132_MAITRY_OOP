public class ChatFilter {

    public static String filterLogs(String[] logs, String keyword) {

        int count = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {

            // Split into maximum 3 parts: time, user, message
            String[] parts = line.split(" ", 3);

            // Skip malformed lines
            if (parts.length < 3) {
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            // Case-insensitive keyword search
            if (message.toLowerCase().contains(keyword.toLowerCase())) {

                count++;

                report.append(time)
                      .append(" ")
                      .append(user)
                      .append(": ")
                      .append(message)
                      .append("\n");
            }
        }

        return "Matches: " + count + "\n" + report;
    }
}