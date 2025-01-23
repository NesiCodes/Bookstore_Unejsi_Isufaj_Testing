package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.BookController;
import com.example.javafxtutorial.controller.CartController;
import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.controller.OrderController;
import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Order;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class StatisticsDataIntegrationTest {

    private StatisticsPageView statisticsPageView;
    private BookController bookController;
    private OrderController orderController;
    private EmployeeController employeeController;
    private CartController cartController;

    @Start
    public void start(Stage stage) {

        bookController = new BookController();
        orderController = new OrderController();
        employeeController = new EmployeeController();
        cartController = new CartController();
        cartController.setTestMode(true);

        statisticsPageView = new StatisticsPageView(orderController, bookController, employeeController);
    }

    @BeforeEach
    public void setUp() {
        bookController.getBooks().clear();
        orderController.getOrders().clear();
        employeeController.getEmployees().clear();
        cartController.getCartItems().clear();
    }


    @Test
    public void testGetMoneySpentForBook_NoPurchases() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        bookController.getBooks().add(book);

        double moneySpent = statisticsPageView.getMoneySpentForBook(book);
        assertEquals(0.0, moneySpent, "Money spent should be 0 for a book with no purchases");
    }

    @Test
    public void testGetMoneySpentForBook_WithPurchases() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        HashMap<Date, Double> purchasedInfo = new HashMap<>();
        purchasedInfo.put(new Date(), 100.0); // $100 spent on the book
        book.setPurchasedInfo(purchasedInfo);
        bookController.getBooks().add(book);

        double moneySpent = statisticsPageView.getMoneySpentForBook(book);
        assertEquals(100.0, moneySpent, "Money spent should be 100 for the book");
    }

    @Test
    public void testGetMoneySpentForBook_WithDateRange() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        HashMap<Date, Double> purchasedInfo = new HashMap<>();
        purchasedInfo.put(Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0); // $100 spent on 2023-01-01
        purchasedInfo.put(Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0); // $200 spent on 2023-12-31
        book.setPurchasedInfo(purchasedInfo);
        bookController.getBooks().add(book);

        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        double moneySpent = statisticsPageView.getMoneySpentForBook(book, fromDate, toDate);
        assertEquals(300.0, moneySpent, "Money spent should be 300 for the book within the date range");
    }


    @Test
    public void testGetSoldQuantityForBook_NoSales() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        bookController.getBooks().add(book);

        int soldQuantity = statisticsPageView.getSoldQuantityForBook(book);
        assertEquals(0, soldQuantity, "Sold quantity should be 0 for a book with no sales");
    }

    @Test
    public void testGetSoldQuantityForBook_WithSales() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        bookController.getBooks().add(book);
        cartController.addCartItem(new CartItem(book, 3, 3 * book.getPrice()));

        Order order = new Order(cartController.getCartItems(), cartController.getTotalQuantity(), cartController.getTotalPrice(), null);
        orderController.getOrders().add(order);

        int soldQuantity = statisticsPageView.getSoldQuantityForBook(book);
        assertEquals(3, soldQuantity, "Sold quantity should be 3 for the book");
    }

    @Test
    public void testGetSoldQuantityForBook_WithDateRange() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);

        cartController.getCartItems().clear(); // Ensure the cart is empty
        cartController.addCartItem(new CartItem(book, 2, 2 * book.getPrice()));

        Order order1 = new Order(cartController.getCartItems(), cartController.getTotalQuantity(), cartController.getTotalPrice(), null);
        order1.setDateCreated(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        System.out.println("Order 1 - Total Quantity: " + order1.getTotalQuantity());

        cartController.getCartItems().clear();
        cartController.calculateCartTotalQuantity();
        cartController.calculateCartTotalPrice();
        cartController.addCartItem(new CartItem(book, 2, 2 * book.getPrice()));

        Order order2 = new Order(cartController.getCartItems(), cartController.getTotalQuantity(), cartController.getTotalPrice(), null);
        order2.setDateCreated(Date.from(LocalDate.of(2022, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        System.out.println("Order 2 - Total Quantity: " + order2.getTotalQuantity());

        orderController.getOrders().add(order1);
        orderController.getOrders().add(order2);

        LocalDate fromDate = LocalDate.of(2022, 1, 1);
        LocalDate toDate = LocalDate.of(2022, 12, 31);
        int soldQuantity = statisticsPageView.getSoldQuantityForBook(book, fromDate, toDate);
        assertEquals(4, soldQuantity, "Sold quantity should be 4 for the book within the date range");
    }


    @Test
    public void testGetMoneyEarnedFromBook_NoSales() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        bookController.getBooks().add(book);

        double moneyEarned = statisticsPageView.getMoneyEarnedFromBook(book);
        assertEquals(0.0, moneyEarned, "Money earned should be 0 for a book with no sales");
    }

    @Test
    public void testGetMoneyEarnedFromBook_WithSales() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        bookController.getBooks().add(book);
        cartController.addCartItem(new CartItem(book, 3, 3 * book.getPrice()));


        Order order = new Order(cartController.getCartItems(), cartController.getTotalQuantity(), cartController.getTotalPrice(), null);
        orderController.getOrders().add(order);

        double moneyEarned = statisticsPageView.getMoneyEarnedFromBook(book);
        assertEquals(60.0, moneyEarned, "Money earned should be 60 for the book");
    }

    @Test
    public void testGetMoneyEarnedFromBook_WithDateRange() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        cartController.addCartItem(new CartItem(book, 2, 2 * book.getPrice()));


        Order order1 = new Order(cartController.getCartItems(), cartController.getTotalQuantity(), cartController.getTotalPrice(), null);
        order1.setDateCreated(Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        orderController.getOrders().add(order1);

        cartController.getCartItems().clear();
        cartController.addCartItem(new CartItem(book, 2, 2 * book.getPrice()));
        Order order2 = new Order(cartController.getCartItems(), cartController.getTotalQuantity(), cartController.getTotalPrice(), null);
        order2.setDateCreated(Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        orderController.getOrders().add(order2);

        // Test the method with a date range
        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        double moneyEarned = statisticsPageView.getMoneyEarnedFromBook(book, fromDate, toDate);
        assertEquals(80.0, moneyEarned, "Money earned should be 80 for the book within the date range");
    }

    // ==================== getTotalIncomes Tests ====================

    @Test
    public void testGetTotalIncomes_NoOrders() {
        double totalIncomes = statisticsPageView.getTotalIncomes();
        assertEquals(0.0, totalIncomes, "Total incomes should be 0 with no orders");
    }

    @Test
    public void testGetTotalIncomes_WithOrders() {
        Order order1 = new Order(null,0,50.0,null);
        orderController.getOrders().add(order1);

        Order order2 = new Order(null,0,100.0,null);
        orderController.getOrders().add(order2);

        double totalIncomes = statisticsPageView.getTotalIncomes();
        assertEquals(150.0, totalIncomes, "Total incomes should be 150 with the given orders");
    }

    @Test
    public void testGetTotalIncomes_WithDateRange() {
        Order order1 = new Order(null,0,50.0,null);
        order1.setDateCreated(Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Order order2 = new Order(null,0,100.0,null);
        order2.setDateCreated(Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        orderController.getOrders().add(order1);
        orderController.getOrders().add(order2);

        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        double totalIncomes = statisticsPageView.getTotalIncomes(fromDate, toDate);
        assertEquals(150.0, totalIncomes, "Total incomes should be 150 within the date range");
    }


    @Test
    public void testGetTotalCost_NoPurchasesOrSalaries() {
        double totalCost = statisticsPageView.getTotalCost();
        assertEquals(0.0, totalCost, "Total cost should be 0 with no purchases or salaries");
    }

    @Test
    public void testGetTotalCost_WithPurchases() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        HashMap<Date, Double> purchasedInfo = new HashMap<>();
        purchasedInfo.put(new Date(), 100.0); // $100 spent on the book
        book.setPurchasedInfo(purchasedInfo);
        bookController.getBooks().add(book);

        double totalCost = statisticsPageView.getTotalCost();
        assertEquals(100.0, totalCost, "Total cost should be 100 with the given purchases");
    }

    @Test
    public void testGetTotalCost_WithDateRange() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        HashMap<Date, Double> purchasedInfo = new HashMap<>();
        purchasedInfo.put(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0); // $100 spent on 2023-01-01
        purchasedInfo.put(Date.from(LocalDate.of(2022, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0); // $200 spent on 2023-12-31
        book.setPurchasedInfo(purchasedInfo);
        bookController.getBooks().add(book);

        Employee employee = new Employee("John Doe", "1990-01-01", "+355 69 12 34 567", "jd20@epoka.edu.al", 1000, 1, "johndoe", "password123");
        employeeController.getEmployees().add(employee);

        LocalDate fromDate = LocalDate.of(2022, 1, 1);
        LocalDate toDate = LocalDate.of(2022, 12, 31);
        double totalCost = statisticsPageView.getTotalCost(fromDate, toDate);
        assertEquals(12300.0, totalCost, "Total cost should be 12300.0 within the date range (purchases + salaries)");
    }

    @Test
    public void testGetBoughtQuantityForBook_NoPurchases() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        bookController.getBooks().add(book);

        int quantity = statisticsPageView.getBoughtQuantityForBook(book);
        assertEquals(0, quantity, "Quantity should be 0 for a book with no purchases");
    }

    @Test
    public void testGetBoughtQuantityForBook_WithPurchases() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        HashMap<Date, Double> purchasedInfo = new HashMap<>();
        purchasedInfo.put(new Date(), 100.0); // $100 spent on the book
        book.setPurchasedInfo(purchasedInfo);
        bookController.getBooks().add(book);

        int quantity = statisticsPageView.getBoughtQuantityForBook(book);
        assertEquals(5, quantity, "Quantity should be 5 for $100 spent on a $20 book");
    }

    @Test
    public void testGetBoughtQuantityForBook_WithDateRange() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        HashMap<Date, Double> purchasedInfo = new HashMap<>();
        purchasedInfo.put(Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0); // $100 spent on 2023-01-01
        purchasedInfo.put(Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0); // $200 spent on 2023-12-31
        book.setPurchasedInfo(purchasedInfo);
        bookController.getBooks().add(book);

        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        int quantity = statisticsPageView.getBoughtQuantityForBook(book, fromDate, toDate);
        assertEquals(15, quantity, "Quantity should be 15 for $300 spent on a $20 book within the date range");
    }

    @Test
    public void testGetBoughtQuantityForBook_OutsideDateRange() {
        Book book = new Book("book1", "Book 1", "Fiction", "Publisher", "Author", 10, 20.0);
        HashMap<Date, Double> purchasedInfo = new HashMap<>();
        purchasedInfo.put(Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0); // $100 spent on 2022-01-01 (outside the range)
        purchasedInfo.put(Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0); // $200 spent on 2023-12-31 (inside the range)
        book.setPurchasedInfo(purchasedInfo);
        bookController.getBooks().add(book);

        LocalDate fromDate = LocalDate.of(2023, 1, 1);
        LocalDate toDate = LocalDate.of(2023, 12, 31);
        int quantity = statisticsPageView.getBoughtQuantityForBook(book, fromDate, toDate);
        assertEquals(10, quantity, "Quantity should be 10 for $200 spent on a $20 book within the date range");
    }
}