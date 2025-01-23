package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderControllerIntegrationTest {

    private OrderController orderController;
    private Path tempDir;
    private File tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        tempDir = Files.createTempDirectory("order-controller-test");
        tempFile = new File(tempDir.toFile(), "orders.dat");

        orderController = new OrderController(tempFile.getAbsolutePath());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(File::delete);
        tempDir.toFile().delete();
    }

    @Test
    public void testReadOrdersFromFile() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(null,0,0,null));
        orders.add(new Order(null,0,0,null));
        orderController.writeOrdersToFile(orders);

        orderController.readOrdersFromFile();

        assertEquals(2, orderController.getOrders().size(), "Expected 2 orders in the file");
    }

    @Test
    public void testWriteOrdersToFile() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(null,0,0,null));
        orders.add(new Order(null,0,0,null));


        orderController.writeOrdersToFile(orders);

        orderController.readOrdersFromFile();

        assertEquals(2, orderController.getOrders().size(), "Expected 2 orders in the file");
    }

    @Test
    public void testAddOrder() {
        Order order = new Order(null,0,0,null);

        orderController.addOrder(order);

        assertEquals(1, orderController.getOrders().size(), "Expected 1 order in the list");
        assertEquals(order, orderController.getOrders().get(0), "The added order should match");
    }

    @Test
    public void testWriteOrdersToFile_EmptyList() {
        orderController.writeOrdersToFile(new ArrayList<>());

        orderController.readOrdersFromFile();

        assertEquals(0, orderController.getOrders().size(), "Expected 0 orders in the file");
    }

    @Test
    public void testReadOrdersFromFile_InvalidFile() {
        tempFile.delete();

        orderController.readOrdersFromFile();

        assertEquals(0, orderController.getOrders().size(), "Expected 0 orders in the list");
    }
}