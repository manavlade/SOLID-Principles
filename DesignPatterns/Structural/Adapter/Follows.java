package DesignPatterns.Structural.Adapter;

/*
 * We are using adapter design pattern
 * Here we have two different apis of two different banks 
 * LegacyBankAPI -> uses iso 8583
 * ICICIBankAPI -> uses NEFT
 * But we want to use them in a single checkout service
 * So we create an interface PaymentGateway which will be the abstraction
 * and then we create two adapters LegacyBankAdapter and ICICIAdapter which will implement PaymentGateway
 * and in the checkout service we pass the adapter which will be used to process payment
 */

class LegacyBankAPI {
    public String initiateIso8583Payment(String accountNum, int amountInPaise, String IFSCCode) {
        System.out.println(
                "ISO 8583 -> AccountNum: " + accountNum + "Amount: " + amountInPaise + "Ifsccode: " + IFSCCode);
        return "ISO_TXN" + System.currentTimeMillis();
    }
}

class ICICIBankAPI {
    public void TransferFunds(double amount, String beneficiary, String transactionRef) {
        System.out.println("ICICI NEFT → ₹" + amount
                + " to " + beneficiary
                + " | Ref: " + transactionRef);
    }
}

/*
 * This is my application's abstraction.
 * My application doesn't care whether payment is processed by:
 * HDFC
 * ICICI
 * Razorpay
 * Stripe
 * some legacy bank API
 * 
 * This is very important because now my application depends on an interface,
 * not a particular bank implementation.
 */
interface PaymentGateway {
    String processPayment(String accountId, double amountInRupees);
}

class LegacyBankAdapter implements PaymentGateway {
    private final LegacyBankAPI legacyBank;

    public LegacyBankAdapter(LegacyBankAPI legacyBank) {
        this.legacyBank = legacyBank;
    }

    @Override
    public String processPayment(String accountId, double amountInRupees) {
        int amountInPaise = (int) (amountInRupees * 100);
        String IFSCCode = resolevIFSC(accountId);

        return legacyBank.initiateIso8583Payment(accountId, amountInPaise, IFSCCode);
    }

    private String resolevIFSC(String accountId) {
        if (accountId.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        return "IDFC0001234";
    }
}

class ICICIAdapter implements PaymentGateway {
    private final ICICIBankAPI iciciBank;

    public ICICIAdapter(ICICIBankAPI iciciBank) {
        this.iciciBank = iciciBank;
    }

    @Override
    public String processPayment(String accountId, double amountInRupees) {
        String transactionRef = "TXN" + System.currentTimeMillis();
        iciciBank.TransferFunds(amountInRupees, accountId, transactionRef);
        return transactionRef;
    }
}

class CheckoutService {
    private final PaymentGateway paymentGateway;

    public CheckoutService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void checkout(String accountId, double amount) {
        System.out.println("Starting checkout for ₹" + amount);
        String txnId = paymentGateway.processPayment(accountId, amount);
        System.out.println("Success. Transaction ID: " + txnId);
    }
}

public class Follows {

    public static void main(String[] args) {
        LegacyBankAPI legacyBank = new LegacyBankAPI();
        PaymentGateway adapter = new LegacyBankAdapter(legacyBank);
        CheckoutService checkoutService = new CheckoutService(adapter);
        checkoutService.checkout("123456789", 1000.0);

        ICICIBankAPI iciciBank = new ICICIBankAPI();
        PaymentGateway iciciAdapter = new ICICIAdapter(iciciBank);
        CheckoutService iciciCheckout = new CheckoutService(iciciAdapter);
        iciciCheckout.checkout("987654321", 2000.0);
    }
}
