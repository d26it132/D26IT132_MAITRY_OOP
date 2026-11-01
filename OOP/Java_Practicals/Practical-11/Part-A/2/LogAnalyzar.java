import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class LogAnalyzar {
    public static void main(String[] args) {

        try {
            Files.write(Paths.get("log1.txt"), Arrays.asList(
                    "Server started",
                    "User login successful",
                    "Error: connection failed",
                    "Server stopped"
            ));

            Files.write(Paths.get("log2.txt"), Arrays.asList(
                    "User login successful",
                    "Database connected",
                    "Error: timeout occurred",
                    "Error: file not found",
                    "User logout"
            ));

            Files.write(Paths.get("log3.txt"), Arrays.asList(
                    "Server started",
                    "Error: file not found",
                    "Backup completed"
            ));

            Path[] files = {
                    Paths.get("log1.txt"),
                    Paths.get("log2.txt"),
                    Paths.get("log3.txt")
            };

            String keyword = "Error";

            int totalLines = 0;
            int keywordLines = 0;

            for (Path path : files) {

                int fileLines = 0;
                int fileKeywordLines = 0;

                try (BufferedReader reader = Files.newBufferedReader(path)) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        fileLines++;
                        totalLines++;

                        if (line.contains(keyword)) {
                            fileKeywordLines++;
                            keywordLines++;
                        }
                    }
                }

                BasicFileAttributes attributes =
                        Files.readAttributes(path, BasicFileAttributes.class);

                System.out.println("File: " + path.getFileName());
                System.out.println("Size: " + attributes.size() + " bytes");
                System.out.println("Lines: " + fileLines);
                System.out.println("Lines containing \"" +
                        keyword + "\": " + fileKeywordLines);
                System.out.println();
            }

            System.out.println("----- Overall Result -----");
            System.out.println("Total lines: " + totalLines);
            System.out.println("Lines containing \"" +
                    keyword + "\": " + keywordLines);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}