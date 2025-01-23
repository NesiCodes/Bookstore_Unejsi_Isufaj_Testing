//package com.example.javafxtutorial.view;
//
//import com.example.javafxtutorial.controller.EmployeeController;
//import com.example.javafxtutorial.controller.OrderController;
//import com.example.javafxtutorial.model.CartItem;
//import com.example.javafxtutorial.model.Employee;
//import com.example.javafxtutorial.model.Order;
//import javafx.application.Platform;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.Node;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class CheckPerformancePageViewTest {
//
//    @Mock
//    private OrderController orderController;
//
//    @Mock
//    private EmployeeController employeeController;
//
//    @Mock
//    private Employee currentLoggedUser;
//
//    @Mock
//    private Stage primaryStage;
//
//    private CheckPerformancePageView checkPerformancePageView;
//
//    @BeforeAll
//    public static void initToolkit() {
//        // Initialize JavaFX Toolkit
//        new JFXPanel();
//        Platform.setImplicitExit(false); // Prevent JavaFX from shutting down
//    }
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this); // Initialize mocks
//        checkPerformancePageView = new CheckPerformancePageView(orderController, employeeController, currentLoggedUser, primaryStage);
//    }
//
//    @Test
//    public void testGetNumberOfBillsForEmployee() {
//        // Mock data
//        Employee employee = new Employee();
//        employee.setUserId("emp1");
//
//        Order order1 = new Order();
//        order1.setEmployee(employee);
//        order1.setDateCreated(Date.from(Instant.now()));
//
//        Order order2 = new Order();
//        order2.setEmployee(employee);
//        order2.setDateCreated(Date.from(Instant.now().minusSeconds(86400))); // 1 day ago
//
//        ArrayList<Order> orders = new ArrayList<>();
//        orders.add(order1);
//        orders.add(order2);
//
//        when(orderController.getOrders()).thenReturn(orders);
//
//        // Test with date range
//        LocalDate fromDate = LocalDate.now().minusDays(1);
//        LocalDate toDate = LocalDate.now();
//
//        int numberOfBills = checkPerformancePageView.getNumberOfBillsForEmployee(employee, fromDate, toDate);
//        assertEquals(2, numberOfBills); // Both orders are within the date range
//    }
//
//    @Test
//    public void testGetNumberOfBooksSoldForEmployee() {
//        // Mock data
//        Employee employee = new Employee();
//        employee.setUserId("emp1");
//
//        Order order1 = new Order();
//        order1.setEmployee(employee);
//        order1.setTotalQuantity(5); // 5 books sold
//        order1.setDateCreated(Date.from(Instant.now()));
//
//        Order order2 = new Order();
//        order2.setEmployee(employee);
//        order2.setTotalQuantity(3); // 3 books sold
//        order2.setDateCreated(Date.from(Instant.now().minusSeconds(86400))); // 1 day ago
//
//        List<Order> orders = new ArrayList<>();
//        orders.add(order1);
//        orders.add(order2);
//
//        when(orderController.getOrders()).thenReturn((ArrayList<Order>) orders);
//
//        // Test with date range
//        LocalDate fromDate = LocalDate.now().minusDays(1);
//        LocalDate toDate = LocalDate.now();
//
//        int numberOfBooksSold = checkPerformancePageView.getNumberOfBooksSoldForEmployee(employee, fromDate, toDate);
//        assertEquals(8, numberOfBooksSold); // 5 + 3 = 8 books sold
//    }
//
//    @Test
//    public void testGetMoneyMadeForEmployee() {
//        // Mock data
//        Employee employee = new Employee();
//        employee.setUserId("emp1");
//
//        Order order1 = new Order();
//        order1.setEmployee(employee);
//        order1.setTotalPrice(100.0); // $100 made
//        order1.setDateCreated(Date.from(Instant.now()));
//
//        Order order2 = new Order();
//        order2.setEmployee(employee);
//        order2.setTotalPrice(50.0); // $50 made
//        order2.setDateCreated(Date.from(Instant.now().minusSeconds(86400))); // 1 day ago
//
//        ArrayList<Order> orders = new ArrayList<>();
//        orders.add(order1);
//        orders.add(order2);
//
//        when(orderController.getOrders()).thenReturn(orders);
//
//        // Test with date range
//        LocalDate fromDate = LocalDate.now().minusDays(1);
//        LocalDate toDate = LocalDate.now();
//
//        double moneyMade = checkPerformancePageView.getMoneyMadeForEmployee(employee, fromDate, toDate);
//        assertEquals(150.0, moneyMade); // $100 + $50 = $150
//    }
//
//    @Test
//    public void testIsBetweenInclusive() {
//        LocalDate date = LocalDate.now();
//        LocalDate startDate = date.minusDays(1);
//        LocalDate endDate = date.plusDays(1);
//
//        // Test inclusive boundaries
//        assertTrue(checkPerformancePageView.isBetweenInclusive(date, startDate, endDate));
//        assertTrue(checkPerformancePageView.isBetweenInclusive(startDate, startDate, endDate));
//        assertTrue(checkPerformancePageView.isBetweenInclusive(endDate, startDate, endDate));
//
//        // Test outside boundaries
//        assertFalse(checkPerformancePageView.isBetweenInclusive(date.minusDays(2), startDate, endDate));
//        assertFalse(checkPerformancePageView.isBetweenInclusive(date.plusDays(2), startDate, endDate));
//    }
//
//    @Test
//    public void testRemoveAllEmployees() {
//        // Mock data
//        VBox cartPage = new VBox();
//        HBox employeeItem1 = new HBox();
//        HBox employeeItem2 = new HBox();
//        HBox header = new HBox();
//
//        // Add employee items and header to the VBox
//        cartPage.getChildren().addAll(header, employeeItem1, employeeItem2);
//
//        // Call the method to remove employee items
//        checkPerformancePageView.removeAllEmployees(cartPage);
//
//        // Verify that only the header remains
//        assertEquals(1, cartPage.getChildren().size());
//        assertTrue(cartPage.getChildren().contains(header));
//    }
//}