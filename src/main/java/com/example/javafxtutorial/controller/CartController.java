package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.CartItem;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartController {

    private ArrayList<CartItem> cartItems;

    private int totalQuantity;

    private double totalPrice;

    private boolean isTestMode = false;

    public CartController(){
        cartItems = new ArrayList<>();
    }

    public CartController(ArrayList<CartItem> cartItems, int totalQuantity, double totalPrice) {
        this.cartItems = cartItems;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public void setTestMode(boolean isTestMode) {
        this.isTestMode = isTestMode;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(totalPrice));
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addCartItem(CartItem cartItem) {
        if (cartItem == null) {
            throw new IllegalArgumentException("CartItem cannot be null");
        }
        if (cartItem.getBook() == null) {
            throw new IllegalArgumentException("Book in CartItem cannot be null");
        }
        if (cartItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (cartItem.getQuantity() > cartItem.getBook().getQuantity()) {
            throw new IllegalArgumentException("Quantity exceeds stock availability");
        }

        // Check if the item already exists in the cart
        for (CartItem c : cartItems) {
            if (c.getBook().getImagePath().equals(cartItem.getBook().getImagePath())) {
                if (c.getBook().getQuantity() > c.getQuantity()) {
                    c.setQuantity(c.getQuantity() + 1);
                    c.setSubtotal(c.getQuantity() * c.getBook().getPrice());
                    calculateCartTotalPrice();
                    calculateCartTotalQuantity();
                    handleAlert("The quantity increased", c.getBook().getName() + " quantity has been increased");
                } //Green (thicker) + Brown (thinner):
                //The line has been executed and is fully covered, but there are no branches to test on that line.

                else {
                    handleAlert("No More Books", "You cannot buy more than " + c.getBook().getQuantity() + " for " + c.getBook().getName());
                }
                return;
            }
        }

        // Add new item to the cart
        this.cartItems.add(cartItem);
        calculateCartTotalPrice();
        calculateCartTotalQuantity();
        handleAlert("Item Added", cartItem.getBook().getName() + " has been added");
    }

    protected void handleAlert(String title, String message) {
        if (!isTestMode) {
            showAlert(title, message);
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

    public void removeCartItem(CartItem cartItem){
        this.cartItems.remove(cartItem);
        calculateCartTotalPrice();
        calculateCartTotalQuantity();
    }

    public void calculateCartTotalQuantity(){
        totalQuantity = 0;
        for (CartItem c: cartItems){
            totalQuantity += c.getQuantity();
        }
    }

    public void calculateCartTotalPrice() {
        this.totalPrice = cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }


    @Override
    public String toString() {
        return "CartController{" +
                "cartItems=" + cartItems +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}