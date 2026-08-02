package DesignPatterns.Factory;

class PaymentServiceViolates {
    public void processPayment(String type, int amount) {

        if (type.equals("UPI")) {
            System.out.println("UPI payment Mode");
        }

        if (type.equals("Card")) {
            System.out.println("Card Payment Mode");
        }

        if (type.equals("WALLET")) {
            System.out.println("WALLET Payment Mode");
        }

        // Product manager says add BNPL → you open this file again
        // Product manager says add CRYPTO → you open this file again
        // Every edit risks breaking UPI, CARD, WALLET that already work

    }
}

public class Violates {

    public static void main(String[] args) {

        PaymentServiceViolates paymentService = new PaymentServiceViolates();

        paymentService.processPayment("UPI", 1000);
        paymentService.processPayment("Card", 2000);
        paymentService.processPayment("WALLET", 3000);

    }

}
