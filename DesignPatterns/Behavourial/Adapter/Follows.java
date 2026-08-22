package DesignPatterns.Behavourial.Adapter;

class LegacyBankAPI {
    public String initiateIso8583Payment(String accountNum, int amountInPaise, String IFSCCode) {
        System.out.println(
                "ISO 8583 -> AccountNum: " + accountNum + "Amount: " + amountInPaise + "Ifsccode: " + IFSCCode);
        return "ISO_TXN" + System.currentTimeMillis();
    }
}

/*
 * This is your application's abstraction.
 * Your application doesn't care whether payment is processed by:
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
        return "IDFC0001234";
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
    }
}
