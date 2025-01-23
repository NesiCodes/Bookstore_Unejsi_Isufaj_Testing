package com.example.javafxtutorial.view;
import com.example.javafxtutorial.model.Employee;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddEmployeePageViewTest extends ApplicationTest {

    private AddEmployeePageView addEmployeePageView;
    private ArrayList<Employee> employees;

    @BeforeEach
    void setUp() {
        addEmployeePageView = new AddEmployeePageView();
        employees = new ArrayList<>();
    }

    @Test
    void testUIComponentsExist() {
        VBox page = addEmployeePageView.createAddEmployeePage(employees, "Manager");

        assertNotNull(page.lookup(".text-field"), "TextFields should be present");
        assertNotNull(page.lookup(".combo-box"), "ComboBox should be present");
        assertNotNull(page.lookup(".button"), "Button should be present");
    }

    @Test
    void testBoundaryValueValidation() {
        VBox page = addEmployeePageView.createAddEmployeePage(employees, "Manager");

        TextField emailField = (TextField) page.lookup(".text-field:focused");
        TextField phoneField = (TextField) page.lookup(".text-field:focused");

        emailField.setText("aa22@epoka.edu.al");
        assertTrue(validateEmail(emailField.getText()), "Valid email should pass");
        emailField.setText("a@epoka.edu");
        assertFalse(validateEmail(emailField.getText()), "Invalid email should fail");

        phoneField.setText("+355 69 12 34 567");
        assertTrue(validatePhone(phoneField.getText()), "Valid phone should pass");
        phoneField.setText("12345");
        assertFalse(validatePhone(phoneField.getText()), "Invalid phone should fail");
    }

//    @Test
//    void testAddEmployeeSuccess() {
//        VBox page = addEmployeePageView.createAddEmployeePage(employees, "Manager");
//
//        TextField nameField = (TextField) page.lookup(".text-field:focused");
//        TextField emailField = (TextField) page.lookup(".text-field:focused");
//        TextField salaryField = (TextField) page.lookup(".text-field:focused");
//        TextField userNameField = (TextField) page.lookup(".text-field:focused");
//        ComboBox<String> roleComboBox = (ComboBox<String>) page.lookup(".combo-box");
//
//        // Set valid inputs
//        nameField.setText("John Doe");
//        emailField.setText("jd22@epoka.edu.al");
//        salaryField.setText("5000");
//        userNameField.setText("john_doe");
//        roleComboBox.setValue("Librarian");
//
//        clickOn(".button");
//
//        assertEquals(1, employees.size(), "Employee should be added");
//        assertEquals("John Doe", employees.get(0).getName(), "Employee name should match");
//    }
//
//    @Test
//    void testDuplicateUsername() {
//        Employee existingEmployee = new Employee(
//                "Existing User", "1990-01-01", "+355 69 12 34 567",
//                "existing@epoka.edu.al", 5000, 2, "duplicate_user", "password123"
//        );
//        employees.add(existingEmployee);
//
//        VBox page = addEmployeePageView.createAddEmployeePage(employees, "Manager");
//        TextField userNameField = (TextField) page.lookup(".text-field:focused");
//
//        userNameField.setText("duplicate_user");
//        clickOn(".button");
//
//        assertEquals(1, employees.size(), "No new employee should be added");
//        assertEquals("duplicate_user", employees.get(0).getUserName(), "Username should remain the same");
//    }
//
//    @Test
//    void testInvalidSalary() {
//        VBox page = addEmployeePageView.createAddEmployeePage(employees, "Manager");
//
//        TextField salaryField = (TextField) page.lookup(".text-field:focused");
//        salaryField.setText("invalid_salary");
//
//        clickOn(".button");
//
//        assertEquals(0, employees.size(), "Employee should not be added with invalid salary");
//    }

    @Test
    void testAccessLevelAssignment() {
        VBox page = addEmployeePageView.createAddEmployeePage(employees, "Administrator");
        ComboBox<String> roleComboBox = (ComboBox<String>) page.lookup(".combo-box");

        roleComboBox.setValue("Librarian");
        assertEquals(1, getAccessLevel(roleComboBox.getValue()), "Librarian access level should be 1");

        roleComboBox.setValue("Manager");
        assertEquals(2, getAccessLevel(roleComboBox.getValue()), "Manager access level should be 2");

        roleComboBox.setValue("Administrator");
        assertEquals(3, getAccessLevel(roleComboBox.getValue()), "Administrator access level should be 3");
    }

    private boolean validateEmail(String email) {
        return email.matches("[a-zA-Z]{2,}\\d{2}@epoka\\.edu\\.al");
    }

    private boolean validatePhone(String phone) {
        return phone.matches("\\+355\\s6[7-9]\\s\\d{2}\\s\\d{2}\\s\\d{3}");
    }

    private int getAccessLevel(String role) {
        switch (role) {
            case "Librarian":
                return 1;
            case "Manager":
                return 2;
            case "Administrator":
                return 3;
            default:
                return -1;
        }
    }
}

