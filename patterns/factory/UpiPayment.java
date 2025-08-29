package patterns.factory;

public class UpiPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("Paid ₹" + amount + " via UPI.");
    }
}
