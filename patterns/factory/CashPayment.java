package patterns.factory;

public class CashPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("Paid ₹" + amount + " in cash.");
    }
}
