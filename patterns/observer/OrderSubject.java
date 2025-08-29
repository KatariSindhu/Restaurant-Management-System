package patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class OrderSubject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers(String order) {
        for (Observer o : observers) {
            o.update(order);
        }
    }
}
