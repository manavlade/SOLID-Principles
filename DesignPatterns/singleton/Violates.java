package DesignPatterns.singleton;

class PaymentConfig {
    private String environment;

    public PaymentConfig() {
        this.environment = "SANDBOX";
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

}

class CheckoutService {
    public void checkout() {
        PaymentConfig config = new PaymentConfig();
        config.setEnvironment("PRODUCTION");
        System.out.println("Checkout using: " + config.getEnvironment());
    }
}

class RefundService {
    public void refund() {
        PaymentConfig config = new PaymentConfig();
        System.out.println("Refund using: " + config.getEnvironment());
    }
}

class NotificationService {
    public void sendNotification() {
        PaymentConfig config = new PaymentConfig();
        System.out.println("Notify using: " + config.getEnvironment());
    }
}

public class Violates {
    public static void main(String[] args) {
        new CheckoutService().checkout();
        new RefundService().refund();
        new NotificationService().sendNotification();
    }
}
