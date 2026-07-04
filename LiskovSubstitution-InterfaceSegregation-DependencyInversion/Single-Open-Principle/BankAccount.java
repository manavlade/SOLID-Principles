
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BankAccount {

    private String accountID;
    private String accounType;
    private double balance;
    private String customerPhone;
    private List<String> transactionHistory = new ArrayList<>();

    public BankAccount(String accountID, String accounType, double balance, String customerPhone) {
        this.accountID = accountID;
        this.accounType = accounType;
        this.balance = balance;
        this.customerPhone = customerPhone;
    }

    /*
     * What's wrong, precisely:
     * 
     * deposit() and withdraw() each do three jobs: balance math, transaction
     * logging (writing to a file), and SMS notification. Three reasons to change
     * living in one method.
     * calculateInterest() is an if-else chain on account type — adding
     * "RECURRING_DEPOSIT" tomorrow means editing this method and re-testing every
     * existing branch.
     * The logging is hardcoded to transaction_log.txt — if you later move to a
     * database or Kafka, you edit BankAccount again, even though "where logs go"
     * has nothing to do with "how an account works."
     * The SMS sending is hardcoded — switching providers, adding email, or
     * supporting WhatsApp notifications all force edits here too.
     */

    // Bad version — violates SRP and OCP
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount should be greater than 0");
        }

        balance += amount;

        String logEntry = "DEPOSIT: " + amount + " | New Balance: " + balance + " | " + new Date();
        transactionHistory.add(logEntry);
        System.out.println("Writing to transaction_log.txt: " + logEntry);

        System.out.println("Sending SMS to " + customerPhone + ": Your account was credited with " + amount);
    }

    public void withDraw(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient Balance");
        }

        balance -= amount;
        String logEntry = "WITHDRAWAL : " + amount + " | New Balance : " + balance + " | " + new Date();
        transactionHistory.add(logEntry);
        System.out.println("Writing to transaction_log.txt :" + logEntry);
    }

    public double calculateInterest() {
        if (accounType.equals("SAVINGS")) {
            return balance * 0.05;
        } else if (accounType.equals("FIXED")) {
            return balance * 0.10;
        } else if (accounType.equals("CURRENT")) {
            return 0.0;
        } else {
            throw new IllegalArgumentException("Invalid Account Type");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccounType() {
        return accounType;
    }

    public static void main(String[] args) {

    }
}