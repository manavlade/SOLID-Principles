import java.util.Date;

interface TransactionLogger {
    void log(String accountId, String type, double amount, double newBalance);
}

interface NotificationService {
    void notifyUser(String phone, String message);
}

interface InterestCalculator {
    double calculate(double balance);
}

class FileTransactionLogger implements TransactionLogger {
    @Override
    public void log(String accountId, String type, double amount, double newBalance) {
        String entry = type + ": " + amount + " | New Balance: " + newBalance + " | " + new Date();
        System.out.println("Writing to transaction_log.txt: " + entry);
    }
}

class SMSNotification implements NotificationService {
    @Override
    public void notifyUser(String phone, String message) {
        System.out.println("Sending SMS to " + phone + ": " + message);
    }
}

class SavingsInterestCalculator implements InterestCalculator {
    @Override
    public double calculate(double balance) {
        return balance * 0.05;
    }
}

class FixedInterestCalculator implements InterestCalculator {
    @Override
    public double calculate(double balance) {
        return balance * 0.10;
    }
}

class CurrentInterestCalculator implements InterestCalculator {
    @Override
    public double calculate(double balance) {
        return 0.0;
    }
}

class BankAccount {

    private String accountId;
    private String customerPhone;
    private double balance;

    private final TransactionLogger logger;
    private final NotificationService notificationService;
    private final InterestCalculator interestStrategy;

    public BankAccount(String accountId, String customerPhone, double balance,
            TransactionLogger logger, NotificationService notifier,
            InterestCalculator calculator) {
        this.accountId = accountId;
        this.customerPhone = customerPhone;
        this.balance = balance;
        this.logger = logger;
        this.notificationService = notifier;
        this.interestStrategy = calculator;
    }

    public void deposit(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount should be positive");
        }

        balance += amount;
        logger.log(accountId, "Deposit", amount, balance);
        notificationService.notifyUser(customerPhone, "Your account was credited with " + amount);

    }

    public void withDraw(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient Balance");
        }

        balance -= amount;
        logger.log(accountId, "Withdrawal", amount, balance);
        notificationService.notifyUser(customerPhone, "Your account was debited with " + amount);

    }

    public void calculateInterest() {
        double interest = interestStrategy.calculate(balance);
        balance += interest;
        logger.log(accountId, "Interest", interest, balance);
        notificationService.notifyUser(customerPhone, "Your account was credited with interest " + interest);
    }

    public double getBalance() {
        return balance;
    }

}

public class SOPFollowed {
    public static void main(String[] args) {

        BankAccount bankAccount = new BankAccount(
                "AC001",
                "1234567890",
                10000.0,
                new FileTransactionLogger(),
                new SMSNotification(),
                new SavingsInterestCalculator()
        );

        bankAccount.deposit(10000);
        bankAccount.withDraw(5000);
        bankAccount.calculateInterest();
    }
}
