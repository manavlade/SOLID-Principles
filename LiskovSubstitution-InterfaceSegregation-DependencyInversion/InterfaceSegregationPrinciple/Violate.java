interface PaymentGateway {
    void chargeCard(double amount);

    void processUpi(String vpa, double amount);

    void initiateNetBanking(String bankCode, double amount);

    void refund(String transactionId);

    void generateSettlementReport();
}

class RazorpayGateway implements PaymentGateway {
    public void chargeCard(double amount) {

    }

    public void processUpi(String vpa, double amount) {

    }

    public void initiateNetBanking(String bankCode, double amount) {

    }

    public void refund(String transactionId) {

    }

    public void generateSettlementReport() {

    }
}

class CryptoGateway implements PaymentGateway {
    public void chargeCard(double amount) {
        throw new UnsupportedOperationException();
    }

    public void processUpi(String vpa, double amount) {
        throw new UnsupportedOperationException();
    }

    public void initiateNetBanking(String bankCode, double amount) {
        throw new UnsupportedOperationException();
    }

    public void refund(String transactionId) {
    }

    public void generateSettlementReport() {
    }
}