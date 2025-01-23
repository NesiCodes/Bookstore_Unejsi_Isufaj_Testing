package com.example.javafxtutorial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Order implements Serializable {

    private String orderId;

    private ArrayList<CartItem> cartItems;

    private int totalQuantity;

    private double totalPrice;

    private Date dateCreated;

    private Employee employee;

    public Order(ArrayList<CartItem> cartItems, int totalQuantity, double totalPrice, Employee employee) {
        this.orderId = UUID.randomUUID().toString();
        this.cartItems = cartItems;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.employee = employee;
        this.dateCreated = new Date();
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", cartItems=" + cartItems +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                ", dateCreated=" + dateCreated +
                ", employee=" + employee +
                '}';
    }
}
