package DesignPatterns.Structural.Decorator;

/*
So basically to give me freedom of selection of functionality like if I do not
 want to encrypt I will simply not create the instance of that class nothing else
*/

interface PaymentProcessor {
    public void process(double amount);
}

class upiProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("UPI Processing " + amount + "via NPCI");
    }
}

abstract class PaymentDecorator implements PaymentProcessor {
    protected final PaymentProcessor wrapped;

    public PaymentDecorator(PaymentProcessor wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void process(double amount) {
        wrapped.process(amount);
    }
}

class LogingDecorator extends PaymentDecorator {

    public LogingDecorator(PaymentProcessor wrapped) {
        super(wrapped);
    }

    @Override
    public void process(double amount) {
        System.out.println("LOG: Starting payment of ₹" + amount
                + " at " + new java.util.Date());
        wrapped.process(amount);
        System.out.println("LOG: Payment complete for ₹" + amount);
    }
}

class EncryptionDecorator extends PaymentDecorator {

    public EncryptionDecorator(PaymentDecorator wrapped) {
        super(wrapped);
    }

    @Override
    public void process(double amount) {
        System.out.println("ENCRYPT: Encrypting payload for ₹" + amount
                + " using AES-256");
        wrapped.process(amount);
        System.out.println("ENCRYPT: Payload decrypted post-processing");
    }
}

class FraudCheckDecorator extends PaymentDecorator {

    private static final double HIGH_VALUE_THRESHOLD = 50000.0;

    public FraudCheckDecorator(PaymentProcessor wrapped) {
        super(wrapped);
    }

    @Override
    public void process(double amount) {
        if (amount > HIGH_VALUE_THRESHOLD) {
            System.out.println("FRAUD: High-value transaction ₹" + amount
                    + " — triggering enhanced verification");
        } else {
            System.out.println("FRAUD: Transaction ₹" + amount
                    + " cleared by rule engine");
        }
        wrapped.process(amount); // only proceeds if no exception thrown
    }
}

public class Follows {

    public static void main(String[] args) {

        PaymentProcessor pipeline = new FraudCheckDecorator(
                new EncryptionDecorator(
                        new LogingDecorator(
                                new upiProcessor())));

        System.out.println("=== Normal Transaction ===");
        pipeline.process(1500.0);

        System.out.println("\n=== High-Value Transaction ===");
        pipeline.process(75000.0);
    }
}
