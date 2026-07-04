package DesignPatterns.singleton;

class PaymentConfig {
    
    private static volatile PaymentConfig instance;

    private String environment;

    private PaymentConfig() {
        this.environment = "SANDBOX";
    }

    public static PaymentConfig getInstance() {
        if (instance == null) {
            synchronized (PaymentConfig.class) {
                if (instance == null) {
                    instance = new PaymentConfig();
                }
            }
        }
        return instance;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}

class FCheckoutService {
    public void checkout() {
        PaymentConfig config = PaymentConfig.getInstance();
        config.setEnvironment("PRODUCTION");
        System.out.println("Checkout using: " + config.getEnvironment());
    }
}

class FRefundService {
    public void refund() {
        PaymentConfig config = PaymentConfig.getInstance(); 
        System.out.println("Refund using: " + config.getEnvironment());
    }
}

public class Follows {
    public static void main(String[] args) {
        FCheckoutService checkoutService = new FCheckoutService();
        FRefundService refundService = new FRefundService();

        checkoutService.checkout();
        refundService.refund();

        PaymentConfig config = PaymentConfig.getInstance();
        System.out.println("Final env: " + config.getEnvironment());
    }
}
