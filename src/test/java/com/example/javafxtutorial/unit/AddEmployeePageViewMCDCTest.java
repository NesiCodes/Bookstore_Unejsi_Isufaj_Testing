package com.example.javafxtutorial.view;

import com.example.javafxtutorial.model.Employee;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class AddEmployeePageViewMCDCTest {

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
    public void testAreFieldsFilled_EmptyField() {
        TextField empNameField = new TextField("");
        TextField birthdayField = new TextField("01/01/1990");
        TextField phoneNumField = new TextField("+355 67 22 22 222");
        TextField emailField = new TextField("john.doe01@epoka.edu.al");
        TextField salaryField = new TextField("50000");
        TextField userNameField = new TextField("johndoe");
        TextField passwordField = new TextField("password1");

        Platform.runLater(() -> {
            boolean result = addEmployeePageView.areFieldsFilled(
                    empNameField, birthdayField, phoneNumField, emailField, salaryField, userNameField, passwordField
            );
            assertFalse(result, "A field is empty, but the result is true.");
        });
    }
    @Test
    public void testValidateInput_InvalidEmail() {
        TextField emailField = new TextField("invalid.email.com");
        TextField phoneNumField = new TextField("+355 67 22 22 222"); // Valid phone
        TextField salaryField = new TextField("50000"); // Valid salary
        TextField passwordField = new TextField("password1"); // Valid password

        Platform.runLater(() -> {
            boolean result = addEmployeePageView.validateInput(emailField, phoneNumField, salaryField, passwordField);
            assertFalse(result, "Invalid email passed validation.");
        });
    }

    @Test
    public void testIsUsernameUnique() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", "01/01/1980", "+355 67 12 34 567", "alice@epoka.edu.al", 50000, 1, "alice", "password1"));

        boolean isUnique = addEmployeePageView.isUsernameUnique("bob", employees);
        assertTrue(isUnique, "Username 'bob' is unique but returned false.");

        isUnique = addEmployeePageView.isUsernameUnique("Alice", employees);
        assertFalse(isUnique, "Username 'Alice' (case insensitive) is not unique but returned true.");
    }
    @Test
    public void testIsUsernameUniqueWithSpaces() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", "01/01/1980", "+355 67 12 34 567", "alice@epoka.edu.al", 50000, 1, "alice", "password1"));

        boolean isUnique = addEmployeePageView.isUsernameUnique(" bob ", employees);
        assertTrue(isUnique, "Username ' bob ' is unique but returned false.");

        isUnique = addEmployeePageView.isUsernameUnique(" Alice ", employees);
        assertFalse(isUnique, "Username ' Alice ' (with spaces) is not unique but returned true.");

        isUnique = addEmployeePageView.isUsernameUnique("   ALICE   ", employees);
        assertFalse(isUnique, "Username '   ALICE   ' (with spaces) is not unique but returned true.");
    }


    @Test
    public void testAreFieldsFilled_AllTrue() {
        TextField empNameField = new TextField("John Doe");
        TextField birthdayField = new TextField("01/01/1990");
        TextField phoneNumField = new TextField("+355 67 22 22 222");
        TextField emailField = new TextField("john.doe01@epoka.edu.al");
        TextField salaryField = new TextField("50000");
        TextField userNameField = new TextField("johndoe");
        TextField passwordField = new TextField("password1");

        Platform.runLater(() -> {
            boolean result = addEmployeePageView.areFieldsFilled(
                    empNameField, birthdayField, phoneNumField, emailField, salaryField, userNameField, passwordField
            );
            assertTrue(result, "All fields are filled, but the result is false.");
        });
    }

    @Test
    public void testValidateInput_Reflection() throws Exception {
        TextField emailField = new TextField("john.doe01@epoka.edu.al");
        TextField phoneNumField = new TextField("+355 67 22 22 222");
        TextField salaryField = new TextField("50000");
        TextField passwordField = new TextField("password1");

        Method validateInputMethod = AddEmployeePageView.class.getDeclaredMethod(
                "validateInput",
                TextField.class, TextField.class, TextField.class, TextField.class
        );
        validateInputMethod.setAccessible(true);

        Platform.runLater(() -> {
            try {
                boolean result = (boolean) validateInputMethod.invoke(
                        addEmployeePageView,
                        emailField, phoneNumField, salaryField, passwordField
                );
                assertTrue(result, "All inputs are valid, but the validation result is false.");
            } catch (Exception e) {
                fail("Reflection method invocation failed: " + e.getMessage());
            }
        });
    }

    @Test
    public void testBoundaryValues() {
        TextField emailField = new TextField("test@epoka.edu.al"); // Valid email
        TextField phoneNumField = new TextField("+355 67 22 22 222"); // Valid phone
        TextField salaryField = new TextField("0"); // Boundary value (example)
        TextField passwordField = new TextField("password1"); // Valid password

        Platform.runLater(() -> {
            boolean result = addEmployeePageView.validateInput(emailField, phoneNumField, salaryField, passwordField);
            // Adjust assertion based on expected behavior for boundary values
            assertTrue(result, "Boundary values did not pass validation as expected.");
        });
    }

    private boolean isFieldValid(TextField field) {
        try {
            Method validateInputMethod = AddEmployeePageView.class.getDeclaredMethod(
                    "validateInput",
                    TextField.class, TextField.class, TextField.class, TextField.class,
                    TextField.class, TextField.class, TextField.class
            );
            validateInputMethod.setAccessible(true);

            return (boolean) validateInputMethod.invoke(
                    addEmployeePageView, field, field, field, field, field, field, field
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail("Reflection call to validateInput failed: " + e.getMessage());
            return false;
        }
    }
}
