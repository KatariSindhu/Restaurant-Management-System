package patterns.factory;

public class PaymentFactory {
    public static Payment getPayment(String type) {
        switch (type.toLowerCase()) {
            case "cash": return new CashPayment();
            case "card": return new CardPayment();
            case "upi": return new UpiPayment();
            default: throw new IllegalArgumentException("Unknown payment type");
        }
    }
}
