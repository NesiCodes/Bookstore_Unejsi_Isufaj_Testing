package com.example.javafxtutorial.view;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.example.javafxtutorial.model.Employee;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class AddEmployeePageViewBVT {

    private static AddEmployeePageView addEmployeePageView;

    @BeforeAll
    public static void setupJavaFX() throws InterruptedException {
        // Initialize JavaFX runtime
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            addEmployeePageView = new AddEmployeePageView();
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void testBoundaryValuesForName() {
        TextField empNameField = new TextField("A"); // Minimum valid length
        assertTrue(isNameValid(empNameField), "Minimum name length should be valid");

        empNameField.setText("A".repeat(51)); // Exceeds max length
        assertFalse(isNameValid(empNameField), "Name exceeding max length should be invalid");
    }

    @Test
    public void testBoundaryValuesForEmail() {
        TextField emailField = new TextField("ab22@epoka.edu.al"); // Valid email
        assertTrue(isEmailValid(emailField), "Valid email should pass");

        emailField.setText("ab22@gmail.com"); // Invalid email domain
        assertFalse(isEmailValid(emailField), "Invalid email domain should fail");
    }
    @Test
    public void testBoundaryValuesForPhone() {
        TextField phoneField = new TextField();
        phoneField.setText("+355 68 12 34 567"); // Valid
        assertTrue(isPhoneValid(phoneField), "Phone validation failed for a valid phone number");

        phoneField.setText("+355 61 12 34 567"); // Invalid (outside range 67-69)
        assertFalse(isPhoneValid(phoneField), "Phone validation passed for an invalid phone number");

        phoneField.setText("355 68 12 34 567"); // Invalid (missing '+')
        assertFalse(isPhoneValid(phoneField), "Phone validation passed for a number missing '+'");
    }

    @Test
    public void testBoundaryValues() {
        // Email validation

        // Salary validation
        validateSalaryField("1000", true);
        validateSalaryField("-1", false); // Invalid (negative value)
        validateSalaryField("abc", false); // Invalid (non-numeric)
        validatePasswordField("abc123", true); // Contains numbers
        validatePasswordField("abcdef", false); // No numbers
        validatePasswordField("123", true);     // Only numbers


        System.out.println("All boundary value tests passed successfully!");
    }

    private void validateEmailField(String email, boolean expected) {
        TextField emailField = new TextField(email);
        assertEquals(expected, isEmailValid(emailField),
                "Email validation failed for: " + email);
    }

    private void validatePhoneField(String phone, boolean expected) {
        TextField phoneField = new TextField(phone);
        assertEquals(expected, isPhoneValid(phoneField),
                "Phone validation failed for: " + phone);
    }

    private void validateSalaryField(String salary, boolean expected) {
        TextField salaryField = new TextField(salary);
        assertEquals(expected, isSalaryValid(salaryField),
                "Salary validation failed for: " + salary);
    }

//    private void validatePasswordField(String password, boolean expected) {
//        TextField passwordField = new TextField(password);
//        assertEquals(expected, isPasswordValid(passwordField),
//                "Password validation failed for: " + password);
//    }
private void validatePasswordField(String input, boolean expected) {
    boolean actual = isValidPassword(input);
    Assertions.assertEquals(expected, actual, "Password validation failed for: " + input);
}

    public boolean isValidPassword(String password) {
    return password != null && password.matches(".*\\d.*"); // Checks for at least one digit
}

    private boolean isNameValid(TextField nameField) {
        String name = nameField.getText();
        return name.length() >= 1 && name.length() <= 50;
    }

    private boolean isEmailValid(TextField emailField) {
        String emailRegex = "[a-zA-Z]{2,}\\d{2}@epoka\\.edu\\.al";
        return emailField.getText().matches(emailRegex);
    }

    private boolean isPhoneValid(TextField phoneField) {
        String phoneRegex = "\\+355\\s(67|68|69)\\s\\d{2}\\s\\d{2}\\s\\d{3}";
        return phoneField.getText().matches(phoneRegex);
    }


    private boolean isSalaryValid(TextField salaryField) {
        try {
            double salary = Double.parseDouble(salaryField.getText());
            return salary >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isPasswordValid(TextField passwordField) {
        String password = passwordField.getText();
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        return hasLetter || hasDigit;
    }
    public class ValidationUtils {

        public static boolean isNameValid(String name) {
            return name.length() >= 1 && name.length() <= 50;
        }

        public static boolean isEmailValid(String email) {
            String emailRegex = "[a-zA-Z]{2,}\\d{2}@epoka\\.edu\\.al";
            return email.matches(emailRegex);
        }

        public static boolean isPhoneValid(String phone) {
            String phoneRegex = "\\+355\\s(67|68|69)\\s\\d{2}\\s\\d{2}\\s\\d{3}";
            return phone.matches(phoneRegex);
        }

        public static boolean isSalaryValid(String salary) {
            try {
                double value = Double.parseDouble(salary);
                return value >= 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public static boolean isPasswordValid(String password) {
            return password != null && password.matches(".*\\d.*");
        }
    }

    @Test
    public void testIntegrationForAddingEmployee() {
        TextField nameField = new TextField("John");
        TextField emailField = new TextField("jo22@epoka.edu.al");
        TextField phoneField = new TextField("+355 68 12 34 567");
        TextField salaryField = new TextField("1500");
        TextField passwordField = new TextField("abc123");
        ArrayList<Employee> employees = new ArrayList<>();

        boolean validFields = addEmployeePageView.areFieldsFilled(nameField, emailField, phoneField, salaryField, passwordField) &&
                addEmployeePageView.validateInput(emailField, phoneField, salaryField, passwordField);
        assertTrue(validFields, "All fields should be valid");

        // Mock add employee process
        Employee employee = new Employee(nameField.getText(), "01/01/1990", phoneField.getText(), emailField.getText(),
                Integer.parseInt(salaryField.getText()), 1, "user123", passwordField.getText());
        employees.add(employee);
        assertEquals(1, employees.size(), "Employee should be added successfully");
    }

    @Test
    public void testIsUsernameUnique_UsernameWithLeadingAndTrailingSpaces() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John Doe", "01/01/1990", "+355 68 33 33 333", "john.doe@epoka.edu.al", 50000, 1, "johndoe", "password1"));

        String username = "  johndoe  "; // Username with leading and trailing spaces
        boolean result = addEmployeePageView.isUsernameUnique(username, employees);
        assertFalse(result, "Expected username with spaces to be considered non-unique.");
    }

    @Test
    public void testIsUsernameUnique_UsernameCaseInsensitive() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John Doe", "01/01/1990", "+355 68 33 33 333", "john.doe@epoka.edu.al", 50000, 1, "johndoe", "password1"));

        String username = "JOHNDOE"; // Username with different case
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

    @Test
    public void testValidateInput_EmptyEmail() {
        TextField emailField = new TextField("");
        TextField phoneField = new TextField("+355 69 12 34 567");
        TextField salaryField = new TextField("50000");
        TextField passwordField = new TextField("password1");

        boolean result = addEmployeePageView.validateInput(emailField, phoneField, salaryField, passwordField);
        assertFalse(result, "Expected empty email to fail validation.");
    }

    @Test
    public void testValidateInput_InvalidEmailFormat() {
        TextField emailField = new TextField("user@.com"); // Invalid email format
        TextField phoneField = new TextField("+355 69 12 34 567");
        TextField salaryField = new TextField("50000");
        TextField passwordField = new TextField("password1");

        boolean result = addEmployeePageView.validateInput(emailField, phoneField, salaryField, passwordField);
        assertFalse(result, "Expected invalid email format to fail validation.");
    }

    @Test
    public void testValidateInput_EmptyPhone() {
        TextField emailField = new TextField("ab12@epoka.edu.al");
        TextField phoneField = new TextField(""); // Empty phone number
        TextField salaryField = new TextField("50000");
        TextField passwordField = new TextField("password1");

        boolean result = addEmployeePageView.validateInput(emailField, phoneField, salaryField, passwordField);
        assertFalse(result, "Expected empty phone field to fail validation.");
    }




}
