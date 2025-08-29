package ui;
/*
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import patterns.builder.MenuItem;
import patterns.factory.Payment;
import patterns.factory.PaymentFactory;
import patterns.observer.*;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Restaurant Ordering System");

        Label lblItem = new Label("Select Item:");
        ComboBox<String> cmbItem = new ComboBox<>();
        cmbItem.getItems().addAll("Burger - ₹150", "Pizza - ₹250", "Coke - ₹50");

        CheckBox chkCheese = new CheckBox("Add Cheese (+₹30)");
        CheckBox chkSauce = new CheckBox("Add Sauce (+₹20)");

        Label lblPayment = new Label("Select Payment Method:");
        ComboBox<String> cmbPayment = new ComboBox<>();
        cmbPayment.getItems().addAll("Cash", "Card", "UPI");

        Button btnOrder = new Button("Place Order");
        TextArea txtOutput = new TextArea();
        txtOutput.setEditable(false);

        btnOrder.setOnAction(e -> {
            if (cmbItem.getValue() == null || cmbPayment.getValue() == null) {
                txtOutput.setText("Please select item and payment method.");
                return;
            }

            String itemName = cmbItem.getValue().split(" - ")[0];
            int price = Integer.parseInt(cmbItem.getValue().split("₹")[1]);

            MenuItem.MenuItemBuilder builder = new MenuItem.MenuItemBuilder(itemName, price);
            if (chkCheese.isSelected()) builder.addCheese();
            if (chkSauce.isSelected()) builder.addSauce();

            MenuItem menuItem = builder.build();

            // Observer pattern
            OrderSubject orderSubject = new OrderSubject();
            orderSubject.addObserver(new Kitchen());
            orderSubject.addObserver(new Billing());
            orderSubject.notifyObservers(menuItem.toString());

            // Payment (Factory pattern)
            Payment payment = PaymentFactory.getPayment(cmbPayment.getValue());
            payment.pay(menuItem.getPrice());

            txtOutput.setText("Order Placed:\n" + menuItem + "\nPaid via " + cmbPayment.getValue());
        });

        VBox root = new VBox(10, lblItem, cmbItem, chkCheese, chkSauce, lblPayment, cmbPayment, btnOrder, txtOutput);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import patterns.builder.MenuItem;
import patterns.factory.Payment;
import patterns.factory.PaymentFactory;
import patterns.observer.*;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private List<MenuItem> cart = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Restaurant Ordering System");

        Label lblItem = new Label("Select Item:");
        ComboBox<String> cmbItem = new ComboBox<>();
        cmbItem.getItems().addAll("Burger - ₹150", "Pizza - ₹250", "Coke - ₹50");

        CheckBox chkCheese = new CheckBox("Add Cheese (+₹30)");
        CheckBox chkSauce = new CheckBox("Add Sauce (+₹20)");

        Button btnAddToCart = new Button("Add to Cart");

        Label lblCart = new Label("Your Cart:");
        ListView<String> cartList = new ListView<>();

        Label lblPayment = new Label("Select Payment Method:");
        ComboBox<String> cmbPayment = new ComboBox<>();
        cmbPayment.getItems().addAll("Cash", "Card", "UPI");

        Button btnOrder = new Button("Place Order");
        TextArea txtOutput = new TextArea();
        txtOutput.setEditable(false);

        // Add item to cart
        btnAddToCart.setOnAction(e -> {
            if (cmbItem.getValue() == null) {
                txtOutput.setText("Please select an item.");
                return;
            }

            String itemName = cmbItem.getValue().split(" - ")[0];
            int price = Integer.parseInt(cmbItem.getValue().split("₹")[1]);

            MenuItem.MenuItemBuilder builder = new MenuItem.MenuItemBuilder(itemName, price);
            if (chkCheese.isSelected()) builder.addCheese();
            if (chkSauce.isSelected()) builder.addSauce();

            MenuItem menuItem = builder.build();
            cart.add(menuItem);

            cartList.getItems().add(menuItem.toString());

            // Reset checkboxes for next selection
            chkCheese.setSelected(false);
            chkSauce.setSelected(false);
        });

        // Place final order
        btnOrder.setOnAction(e -> {
            if (cart.isEmpty() || cmbPayment.getValue() == null) {
                txtOutput.setText("Please add items and select payment method.");
                return;
            }

            int totalPrice = cart.stream().mapToInt(MenuItem::getPrice).sum();

            // Observer pattern
            OrderSubject orderSubject = new OrderSubject();
            orderSubject.addObserver(new Kitchen());
            orderSubject.addObserver(new Billing());
            cart.forEach(item -> orderSubject.notifyObservers(item.toString()));

            // Payment (Factory pattern)
            Payment payment = PaymentFactory.getPayment(cmbPayment.getValue());
            payment.pay(totalPrice);

            StringBuilder orderSummary = new StringBuilder("Order Placed:\n");
            cart.forEach(item -> orderSummary.append(item).append("\n"));
            orderSummary.append("Total = ₹").append(totalPrice).append("\n");
            orderSummary.append("Paid via ").append(cmbPayment.getValue());

            txtOutput.setText(orderSummary.toString());
            cart.clear();
            cartList.getItems().clear();
        });

        VBox root = new VBox(10, lblItem, cmbItem, chkCheese, chkSauce, btnAddToCart, lblCart, cartList,
                lblPayment, cmbPayment, btnOrder, txtOutput);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 450, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

