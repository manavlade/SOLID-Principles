package DesignPatterns.abstractFactory;

// Now I am a buissness owner and I have created 3 products to sell they are payment processor, kyc validator and reciept generator
//Now these were created by me for showing it to my customers now they liked it so I bought a factory to produce my products on mass scale and the factory name is PaymentFactory 
//Now I have received orders from other countries as well so I need to produce my products for them as well 
//So now since both countries ahve different operating methods I have created two seperate sections inside my factory which will handle this condition
// FOr India we have IndiaPaymentFactory and for US we have UsPaymentFactory
//now someone needs to tell which product order we have got so for that we have created a Checkoutservice factory which will tell which factory to use
//hence in this way no matter how many countries we add in our list the existing code won't break

interface PaymentProcessor {
    public void processPayment(double amount);
}

interface KYCValidator {
    public void validate(String customerID);
}

interface RecieptGenerator {
    public void generateReciept(double amount, String customerID);
}

interface PaymentFactory {
    PaymentProcessor createProcessor();

    KYCValidator createValidator();

    RecieptGenerator createRecieptGenerator();
}

class UPIPayment implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing UPI of Rs " + amount + " via UPI/NPCI");
    }
}

class AadharValidator implements KYCValidator {
    @Override
    public void validate(String customerID) {
        System.out.println("Validating via Aadhar/UIDAI");
    }
}

class InrRecieptGenerator implements RecieptGenerator {
    @Override
    public void generateReciept(double amount, String customerID) {
        System.out.println("Generating INR Reciept of Rs " + amount + " for customer " + customerID);
    }
}

class IndiaPaymentFactory implements PaymentFactory {
    public PaymentProcessor createProcessor() {
        return new UPIPayment();
    }

    public KYCValidator createValidator() {
        return new AadharValidator();
    }

    public RecieptGenerator createRecieptGenerator() {
        return new InrRecieptGenerator();
    }
}

class AchProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing ACH of Rs " + amount + " via ACH/Fedwire");
    }
}

class SsnValidator implements KYCValidator {
    @Override
    public void validate(String customerID) {
        System.out.println("Validating via SSN/IRS for customer " + customerID);
    }
}

class UsdRecieptGenerator implements RecieptGenerator {
    @Override
    public void generateReciept(double amount, String customerID) {
        System.out.println("Generating USD Reciept of Rs " + amount + " for customer " + customerID);
    }
}

class USPaymentFactory implements PaymentFactory {
    public PaymentProcessor createProcessor() {
        return new AchProcessor();
    }

    public KYCValidator createValidator() {
        return new SsnValidator();
    }

    public RecieptGenerator createRecieptGenerator() {
        return new UsdRecieptGenerator();
    }
}

class CheckoutService {
    private final PaymentProcessor processor;
    private final KYCValidator validator;
    private final RecieptGenerator recieptGenerator;

    public CheckoutService(PaymentFactory factory) {
        this.processor = factory.createProcessor();
        this.validator = factory.createValidator();
        this.recieptGenerator = factory.createRecieptGenerator();
    }

    public void checkout(double amount, String customerID) {
        validator.validate(customerID);
        processor.processPayment(amount);
        recieptGenerator.generateReciept(amount, customerID);
    }

}

public class Follows {
    public static void main(String[] args) {

        System.out.println("=== India Checkout ===");
        PaymentFactory indiaFactory = new IndiaPaymentFactory();
        CheckoutService indiaCheckout = new CheckoutService(indiaFactory);
        indiaCheckout.checkout(1000, "IND123");

        System.out.println("\n=== US Checkout ===");
        PaymentFactory usFactory = new USPaymentFactory();
        CheckoutService usCheckout = new CheckoutService(usFactory);
        usCheckout.checkout(1000, "USA123");
    }
}
