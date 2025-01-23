package com.example.javafxtutorial.view;

import com.example.javafxtutorial.model.Employee;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class AddEmployeePageViewTest2 {

    private static AddEmployeePageView addEmployeePageView;

    @BeforeAll
    public static void setupJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            addEmployeePageView = new AddEmployeePageView();
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void testAreFieldsFilled_AllFieldsFilled() {
        TextField field1 = new TextField("Test1");
        TextField field2 = new TextField("Test2");
        TextField field3 = new TextField("Test3");

        boolean result = addEmployeePageView.areFieldsFilled(field1, field2, field3);
        assertTrue(result, "Expected all fields to be filled.");
    }

    @Test
    public void testAreFieldsFilled_OneFieldEmpty() {
        TextField field1 = new TextField("Test1");
        TextField field2 = new TextField(""); // Empty
        TextField field3 = new TextField("Test3");

        boolean result = addEmployeePageView.areFieldsFilled(field1, field2, field3);
        assertFalse(result, "Expected one empty field to result in false.");
    }

    @Test
    public void testSaveButton_AllFieldsEmpty() { // Covers all fields empty
        ArrayList<Employee> employees = new ArrayList<>();
        ComboBox<String> roleComboBox = new ComboBox<>();

        Platform.runLater(() -> {
            addEmployeePageView.createAddEmployeePage(employees, "Manager");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertEquals("Incomplete Information", alert.getTitle());
        });
    }

    @Test
    public void testInvalidEmail() { // Covers invalid email input
        ArrayList<Employee> employees = new ArrayList<>();
        ComboBox<String> roleComboBox = new ComboBox<>();

        Platform.runLater(() -> {
            addEmployeePageView.createAddEmployeePage(employees, "Manager");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertEquals("Invalid Email Address", alert.getTitle());
        });
    }

    @Test
    public void testFieldsNotFilled() { // Covers empty field validation
        ArrayList<Employee> employees = new ArrayList<>();
        ComboBox<String> roleComboBox = new ComboBox<>();

        Platform.runLater(() -> {
            addEmployeePageView.createAddEmployeePage(employees, "Manager");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertEquals("Incomplete Information", alert.getTitle());
        });
    }

    @Test
    public void testDuplicateUsername() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Jane Doe", "02/02/1990", "+355 68 33 33 333", "jane.doe@epoka.edu.al", 45000, 1, "existingUser", "password2"));

        Platform.runLater(() -> {
            addEmployeePageView.createAddEmployeePage(employees, "Manager");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertEquals("Invalid Username", alert.getTitle());
        });
    }

    @Test
    public void testAddValidEmployee() {
        ArrayList<Employee> employees = new ArrayList<>();
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.setValue("Librarian");

        Platform.runLater(() -> {
            addEmployeePageView.createAddEmployeePage(employees, "Manager");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            assertEquals("Success", alert.getTitle());
            assertEquals(1, employees.size());
        });
    }
    @Test
    public void testValidateInput_ValidInputs() {
        TextField emailField = new TextField("ab12@epoka.edu.al");
        TextField phoneField = new TextField("+355 69 12 34 567");
        TextField salaryField = new TextField("50000");
        TextField passwordField = new TextField("password1");

        boolean result = addEmployeePageView.validateInput(emailField, phoneField, salaryField, passwordField);
        assertTrue(result, "Expected valid inputs to pass validation.");
    }
    @Test
    public void testIsUsernameUnique_UsernameWithLeadingAndTrailingSpaces() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John Doe", "01/01/1990", "+355 68 33 33 333", "john.doe@epoka.edu.al", 50000, 1, "johndoe", "password1"));

        String username = "  johndoe  ";
        boolean result = addEmployeePageView.isUsernameUnique(username, employees);
        assertFalse(result, "Expected username with spaces to be considered non-unique.");
    }


    @Test
    public void testIsUsernameUnique_UsernameCaseInsensitive() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John Doe", "01/01/1990", "+355 68 33 33 333", "john.doe@epoka.edu.al", 50000, 1, "johndoe", "password1"));

        String username = "JOHNDOE";
        boolean result = addEmployeePageView.isUsernameUnique(username, employees);
        assertFalse(result, "Expected case-insensitive username comparison to return non-unique.");
    }

    @Test
    public void testAreFieldsFilled_WithNullInput() {
        TextField field1 = null;
        TextField field2 = new TextField("Test2");
        TextField field3 = new TextField("Test3");

        boolean result = addEmployeePageView.areFieldsFilled(field1, field2, field3);
        assertFalse(result, "Expected null field to return false.");
    }


}
