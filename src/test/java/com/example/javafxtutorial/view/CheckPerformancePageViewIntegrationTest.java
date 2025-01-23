//COMMENTED OUT BECAUSE OF SOME BUGS

//package com.example.javafxtutorial.view;
//
//import com.example.javafxtutorial.controller.EmployeeController;
//import com.example.javafxtutorial.controller.OrderController;
//import com.example.javafxtutorial.model.CartItem;
//import com.example.javafxtutorial.model.Employee;
//import com.example.javafxtutorial.model.Order;
//import com.example.javafxtutorial.model.Role;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testfx.api.FxRobot;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.framework.junit5.Start;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.testfx.api.FxAssert.verifyThat;
//import static org.testfx.matcher.base.NodeMatchers.isVisible;
//import static org.testfx.matcher.control.LabeledMatchers.hasText;
//
//@ExtendWith(ApplicationExtension.class)
//public class CheckPerformancePageViewIntegrationTest {
//
//    private ScrollPane performancePage;
//    private EmployeeController employeeController;
//    private OrderController orderController;
//    private Employee currentLoggedUser;
//    private Stage primaryStage;
//
//    @Start
//    public void start(Stage stage) {
//        // Initialize controllers and sample data
//        employeeController = new EmployeeController();
//        orderController = new OrderController();
//        primaryStage = stage;
//
//        // Create sample employees using the updated constructor
//        Employee employee1 = new Employee("John Doe", "1990-01-01", "123456789", "john.doe@example.com", 50000, 1, "johndoe", "password");
//        employee1.setRole(new Role("Librarian"));
//        Employee employee2 = new Employee("Jane Smith", "1995-05-05", "987654321", "jane.smith@example.com", 60000, 2, "janesmith", "password");
//        employee1.setRole(new Role("Manager"));
//
//        employeeController.getEmployees().add(employee1);
//        employeeController.getEmployees().add(employee2);
//
//        // Create sample orders using the updated constructor
//        ArrayList<CartItem> cartItems1 = new ArrayList<>(); // Add CartItem objects if needed
//        ArrayList<CartItem> cartItems2 = new ArrayList<>(); // Add CartItem objects if needed
//
//        Order order1 = new Order(cartItems1, 5, 100.0, employee1);
//        Order order2 = new Order(cartItems2, 10, 200.0, employee2);
//        orderController.getOrders().add(order1);
//        orderController.getOrders().add(order2);
//
//        // Set the current logged-in user
//        currentLoggedUser = employee1;
//
//        // Create the CheckPerformancePageView
//        CheckPerformancePageView performancePageView = new CheckPerformancePageView(orderController, employeeController, currentLoggedUser, primaryStage);
//        performancePage = performancePageView.createEmpPerformancePage();
//
//        // Set up the scene and stage
//        stage.setScene(new javafx.scene.Scene(performancePage, 800, 600));
//        stage.show();
//    }
//
//    @BeforeEach
//    public void resetData() {
//        // Reset the data before each test
//        employeeController.getEmployees().clear();
//        orderController.getOrders().clear();
//    }
//
//    @Test
//    public void testInitialDisplay(FxRobot robot) {
//        // Wait for the employee entries to be visible
//        robot.sleep(1000); // Adjust the sleep duration as needed
//
//        // Verify that the performance page is loaded correctly
//        verifyThat("#employeeEntry_0", Node::isVisible); // Check for the first employee entry
//        verifyThat("#employeeEntry_1", Node::isVisible); // Check for the second employee entry
//
//        // Verify the initial values of the first employee
//        verifyThat("#employeeName_0", hasText("Full Name: John Doe"));
//        verifyThat("#numberOfBills_0", hasText("Number of bills: 1"));
//        verifyThat("#booksSold_0", hasText("Books sold: 5"));
//        verifyThat("#moneyMade_0", hasText("Money made: 100.0"));
//
//        // Verify the initial values of the second employee
//        verifyThat("#employeeName_1", hasText("Full Name: Jane Smith"));
//        verifyThat("#numberOfBills_1", hasText("Number of bills: 1"));
//        verifyThat("#booksSold_1", hasText("Books sold: 10"));
//        verifyThat("#moneyMade_1", hasText("Money made: 200.0"));
//    }
//
//    @Test
//    public void testSearchByDateRange(FxRobot robot) {
//        // Wait for the employee entries to be visible
//        robot.sleep(1000); // Adjust the sleep duration as needed
//
//        // Set the date range
//        DatePicker fromDatePicker = robot.lookup("#fromDatePicker").query();
//        DatePicker toDatePicker = robot.lookup("#toDatePicker").query();
//        robot.interact(() -> {
//            fromDatePicker.setValue(LocalDate.now().minusDays(3));
//            toDatePicker.setValue(LocalDate.now().minusDays(1));
//        });
//
//        // Click the search button
//        robot.clickOn("#searchButton");
//
//        // Wait for the employee entries to be updated
//        robot.sleep(1000); // Adjust the sleep duration as needed
//
//        // Verify that the performance data is updated based on the date range
//        verifyThat("#employeeEntry_0", Node::isVisible); // Check for the first employee entry
//        verifyThat("#employeeEntry_1", Node::isVisible); // Check for the second employee entry
//
//        // Verify the updated values of the first employee
//        verifyThat("#employeeName_0", hasText("Full Name: John Doe"));
//        verifyThat("#numberOfBills_0", hasText("Number of bills: 1"));
//        verifyThat("#booksSold_0", hasText("Books sold: 5"));
//        verifyThat("#moneyMade_0", hasText("Money made: 100.0"));
//
//        // Verify the updated values of the second employee
//        verifyThat("#employeeName_1", hasText("Full Name: Jane Smith"));
//        verifyThat("#numberOfBills_1", hasText("Number of bills: 1"));
//        verifyThat("#booksSold_1", hasText("Books sold: 10"));
//        verifyThat("#moneyMade_1", hasText("Money made: 200.0"));
//    }
//
//    @Test
//    public void testInvalidDateRange(FxRobot robot) {
//        // Wait for the employee entries to be visible
//        robot.sleep(1000); // Adjust the sleep duration as needed
//
//        // Set an invalid date range (from date after to date)
//        DatePicker fromDatePicker = robot.lookup("#fromDatePicker").query();
//        DatePicker toDatePicker = robot.lookup("#toDatePicker").query();
//        robot.interact(() -> {
//            fromDatePicker.setValue(LocalDate.now().minusDays(1));
//            toDatePicker.setValue(LocalDate.now().minusDays(3));
//        });
//
//        // Click the search button
//        robot.clickOn("#searchButton");
//
//        // Wait for the alert to be shown
//        robot.sleep(1000); // Adjust the sleep duration as needed
//
//        // Verify that an alert is shown
//        verifyThat(".alert", isVisible());
//
//        // Verify that the performance data remains unchanged
//        verifyThat("#employeeEntry_0", Node::isVisible); // Check for the first employee entry
//        verifyThat("#employeeEntry_1", Node::isVisible); // Check for the second employee entry
//    }
//
//    @Test
//    public void testBooksSoldClick(FxRobot robot) {
//        // Wait for the employee entries to be visible
//        robot.sleep(1000); // Adjust the sleep duration as needed
//
//        // Click the "Books sold" label for the first employee
//        robot.clickOn("#booksSold_0");
//
//        // Wait for the BooksSoldWindow to be shown
//        robot.sleep(1000); // Adjust the sleep duration as needed
//
//        // Verify that the BooksSoldWindow is shown
//        verifyThat("#booksSoldWindow", isVisible());
//    }
//}