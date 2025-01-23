package com.example.javafxtutorial.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CartItem implements Serializable {

    private Book book;

    private int quantity;

    private double subtotal;

    public CartItem(Book book, int quantity, double subtotal) {
        this.book = book;
        this.quantity = quantity;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.subtotal = Double.parseDouble(decimalFormat.format(subtotal));
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.subtotal = Double.parseDouble(decimalFormat.format(subtotal));
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
