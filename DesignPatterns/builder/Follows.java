package DesignPatterns.builder;

/*  
Now I ahve my own buissness and it is working and the orders are increasing day by day
I have already have a factory but before making the products I need to make sure payment is recieved 
but there are many params and taking this many params may lead to bugs if there are multiple developers working on same code
so to resolve this I thought of using buider design pattern we create a transaction class mention and the fields use final so that once created no one can edit
create a builder class which will set the required fields and when we create instance will call the builder method and create seperate functions 
to set optional fields and add validation before build so basically we sorted the issue wherein we had multiple params
*/

class TransactionBuilder {
    private final String transactionId;
    private final String accountId;
    private final double amount;
    private final String currency;
    private final String paymentType;
    private final String merchantId;
    private final String ipAddress;
    private final String deviceId;

    // Private constructor — only Builder can create a Transaction
    private TransactionBuilder(Builder builder) {
        this.transactionId = builder.transactionId;
        this.accountId = builder.accountId;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.paymentType = builder.paymentType;
        this.merchantId = builder.merchantId;
        this.ipAddress = builder.ipAddress;
        this.deviceId = builder.deviceId;
    }

    static class Builder {

        private String transactionId;
        private String accountId;
        private double amount;
        private String currency;
        private String paymentType;
        private String merchantId;
        private String ipAddress;
        private String deviceId;

        public Builder(String transactionId, String accountId,
                double amount, String currency, String paymentType) {
            this.transactionId = transactionId;
            this.accountId = accountId;
            this.amount = amount;
            this.currency = currency;
            this.paymentType = paymentType;
        }

        // Optional fields — each returns 'this' for chaining
        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public TransactionBuilder build() {
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount should be greater than 0");
            }
            if (accountId == null || accountId.isBlank()) {
                throw new IllegalArgumentException("Account ID is required");
            }
            return new TransactionBuilder(this);
        }
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMerchantId() {
        return merchantId;
    }

    @Override
    public String toString() {
        return "TransactionBuilder {" +
                "transactionId='" + transactionId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount + ", currency='" + currency + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

}

public class Follows {
    public static void main(String[] args) {

        TransactionBuilder upiTxn = new TransactionBuilder.Builder("TXN101", "ACC500", 5000, "INR", "UPI").build();
        System.out.println(upiTxn);

        TransactionBuilder cardTxn = new TransactionBuilder.Builder(
                "TXN001", "ACC001", 1500.0, "INR", "UPI")
                .merchantId("ZOMATO")
                .ipAddress("192.168.1.1")
                .deviceId("DEVICE_XYZ")
                .build();

        System.out.println(cardTxn);

        // Only required fields — optional ones simply absent, no null in sight
        TransactionBuilder achTxn = new TransactionBuilder.Builder(
                "TXN002", "ACC002", 299.99, "USD", "ACH")
                .build();

        System.out.println(achTxn);

        try {

            TransactionBuilder invalidTxn = new TransactionBuilder.Builder("TXN002", null, -500.0, "USD", "ACH")
                    .build();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        }

    }

}
