package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    private ArrayList<Order> orders;
    private String filePath = "src/main/resources/com/example/javafxtutorial/database/orders.dat";

    public OrderController() {
        orders = new ArrayList<>();
        System.out.println("Using file path: " + getFilePath());
        readOrdersFromFile();
    }

    public OrderController(String filePath) {
        orders = new ArrayList<>();
        this.filePath = filePath;
        System.out.println("Using file path: " + filePath);
        readOrdersFromFile();
    }


    public void readOrdersFromFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            orders = (ArrayList<Order>) ois.readObject();
            System.out.println("Orders read from file: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeOrdersToFile() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(orders);
            System.out.println("Orders written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOrdersToFile(List<Order> orders) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(orders);
            System.out.println("Orders written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public String getFilePath() {
        return filePath;
    }

    public void addOrder(Order o){
        this.orders.add(o);
    }

    public void printOrders(){
        for (Order o: orders){
            System.out.println(o);
        }
    }


}

