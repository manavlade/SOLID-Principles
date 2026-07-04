/*
I — Interface Segregation Principle
Definition: Don't force a class to implement methods it
 doesn't need. Many small, specific interfaces beat one large, general-purpose one.
*/

interface CardPayable {
    void chargeCard(double amount);
}

interface UpiPayable {
    void processUpi(String vpa, double amount);
}

interface NetBankingPayable {
    void initiateNetBanking(String bankCode, double amount);
}

interface Refundable {
    void refund(String transactionId);
}

interface SettlementReportable {
    void generateSettlementReport();
}

class RazorpayGateway implements CardPayable, UpiPayable, NetBankingPayable, Refundable, SettlementReportable {
    public void chargeCard(double amount) {
        /* ... */ }

    public void processUpi(String vpa, double amount) {
        /* ... */ }

    public void initiateNetBanking(String bankCode, double amount) {
        /* ... */ }

    public void refund(String transactionId) {
        /* ... */ }

    public void generateSettlementReport() {
        /* ... */ }
}

class CryptoGateway implements Refundable, SettlementReportable {
    public void refund(String transactionId) {
        /* ... */ }

    public void generateSettlementReport() {
        /* ... */ }
}