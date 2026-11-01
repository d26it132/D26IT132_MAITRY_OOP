package util;

import model.Account;

public class StatementFormatter {

    public static String buildStatement(Account account) {

        StringBuilder statement = new StringBuilder();

        statement.append("========== MiniBank Statement ==========\n");
        statement.append("Account Number: ")
                .append(account.getAccountNumber()).append("\n");
        statement.append("Account Holder: ")
                .append(account.getOwnerName()).append("\n");
        statement.append("Balance: Rs. ")
                .append(account.getBalance()).append("\n");
        statement.append("Interest Rate: ")
                .append(account.interestRate()).append("%\n");
        statement.append("Estimated Yearly Interest: Rs. ")
                .append(String.format("%.2f", account.yearlyInterest()))
                .append("\n");
        statement.append("Account Active: ")
                .append(account.isActive()).append("\n");
        statement.append("========================================");

        return statement.toString();
    }
}