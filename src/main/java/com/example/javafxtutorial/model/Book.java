package com.example.javafxtutorial.model;

import com.example.javafxtutorial.auxiliary.ISBNGenerator;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

public class Book implements Serializable {

    private String isbn;
    private String imagePath;
    private String name;
    private String category;
    private String supplier;
    private HashMap<Date, Double> purchasedInfo;
    private String author;
    private int quantity;
    private double purchasedPrice;
    private double price;


    public Book(String imagePath, String name, String category, String supplier, String author, int quantity, double price) {
        this.isbn = ISBNGenerator.generateISBN();
        this.imagePath = imagePath;
        this.name = name;
        this.category = category;
        this.supplier = supplier;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
        this.purchasedInfo = new HashMap<>();
    }


//    public Book(String imagePath, String name, String author, int quantity, double price) {
//        this.imagePath = imagePath;
//        this.name = name;
//        this.author = author;
//        this.quantity = quantity;
//        DecimalFormat decimalFormat = new DecimalFormat("#.##");
//        this.price = Double.parseDouble(decimalFormat.format(price));
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.price = Double.parseDouble(decimalFormat.format(price));
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addPurchaseInfo(int quantity){
        this.purchasedInfo.put(new Date(), quantity * price);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public HashMap<Date, Double> getPurchasedInfo() {
        return purchasedInfo;
    }

    public void setPurchasedInfo(HashMap<Date, Double> purchasedInfo) {
        this.purchasedInfo = purchasedInfo;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", supplier='" + supplier + '\'' +
                ", purchasedInfo=" + purchasedInfo +
                ", author='" + author + '\'' +
                ", quantity=" + quantity +
                ", purchasedPrice=" + purchasedPrice +
                ", price=" + price +
                '}';
    }
}
