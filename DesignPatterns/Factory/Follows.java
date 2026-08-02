package DesignPatterns.Factory;

interface PaymentProcessor {
    void processPayment(int amount);
}

class UPIService implements PaymentProcessor {

    @Override
    public void processPayment(int amount) {
        System.out.println("Processing UPI of ₹" + amount + " via NPCI");
    }
}

class CardService implements PaymentProcessor {

    @Override
    public void processPayment(int amount) {
        System.out.println("Processing Card of ₹" + amount + " via VISA/Mastercard");
    }

}

class WalletService implements PaymentProcessor {
    @Override
    public void processPayment(int amount) {
        System.out.println("Processing Wallet of ₹" + amount + " via Paytm/PhonePe");
    }
}

class PaymentServiceFactory {
    public static PaymentProcessor create(String type) {
        switch (type) {
            case "UPI":
                return new UPIService();
            case "CARD":
                return new CardService();
            case "WALLET":
                return new WalletService();
            default:
                throw new IllegalArgumentException("Invalid Payment Type: " + type);
        }
    }
}

public class Follows {

    public static void main(String[] args) {

        PaymentProcessor paymentProcessor = PaymentServiceFactory.create("UPI");
        paymentProcessor.processPayment(1000);

    }

}
