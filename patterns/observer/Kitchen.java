package patterns.observer;

public class Kitchen implements Observer {
    @Override
    public void update(String order) {
        System.out.println("Kitchen: Prepare " + order);
    }
}
