
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

public class ReportGenerator {

    public static void generate(Path logsFolder, Path reportFile)
            throws IOException {

        long totalDeposits = 0;
        long totalWithdrawals = 0;

        StringBuilder report = new StringBuilder();

        report.append("MINIBANK END-OF-DAY REPORT\n");
        report.append("=========================\n\n");

        if (Files.isDirectory(logsFolder)) {

            try (DirectoryStream<Path> stream =
                    Files.newDirectoryStream(logsFolder)) {

                for (Path file : stream) {

                    if (!Files.isRegularFile(file)) {
                        continue;
                    }

                    BasicFileAttributes attributes =
                            Files.readAttributes(
                                file,
                                BasicFileAttributes.class
                            );

                    report.append("File: ")
                          .append(file.getFileName())
                          .append("\n");

                    report.append("Size: ")
                          .append(attributes.size())
                          .append(" bytes\n");

                    report.append("Last modified: ")
                          .append(Instant.ofEpochMilli(
                              attributes.lastModifiedTime().toMillis()
                          ))
                          .append("\n\n");

                    try (BufferedReader reader =
                            Files.newBufferedReader(
                                file, StandardCharsets.UTF_8)) {

                        String line;

                        while ((line = reader.readLine()) != null) {

                            String[] parts = line.trim().split("\\s+");

                            if (parts.length != 2) {
                                continue;
                            }

                            try {
                                long amount = Long.parseLong(parts[1]);

                                if (amount < 0) {
                                    continue;
                                }

                                if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                    totalDeposits += amount;
                                } else if (
                                    parts[0].equalsIgnoreCase("WITHDRAW")
                                    || parts[0].equalsIgnoreCase("WITHDRAWAL")
                                ) {
                                    totalWithdrawals += amount;
                                }

                            } catch (NumberFormatException e) {
                                // Ignore malformed transaction amounts.
                            }
                        }
                    }
                }
            }
        } else {
            report.append("Logs directory not found.\n\n");
        }

        report.append("TRANSACTION SUMMARY\n");
        report.append("-------------------\n");
        report.append("Total deposits: ")
              .append(totalDeposits).append("\n");
        report.append("Total withdrawals: ")
              .append(totalWithdrawals).append("\n");
        report.append("Net transaction amount: ")
              .append(totalDeposits - totalWithdrawals).append("\n");

        Path parent = reportFile.toAbsolutePath().getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(
            reportFile,
            report.toString(),
            StandardCharsets.UTF_8
        );

        System.out.println(report);
    }
}
