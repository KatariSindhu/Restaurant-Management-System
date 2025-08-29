package patterns.observer;

public class Billing implements Observer {
    @Override
    public void update(String order) {
        System.out.println("Billing: Add " + order + " to bill.");
    }
}
