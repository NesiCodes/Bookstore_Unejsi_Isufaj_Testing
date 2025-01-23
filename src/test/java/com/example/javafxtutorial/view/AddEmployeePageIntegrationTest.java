package com.example.javafxtutorial.view;

import com.example.javafxtutorial.model.Employee;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class AddEmployeePageIntegrationTest {

    private VBox addEmployeePage;
    private ArrayList<Employee> employees;

    @Start
    public void start(Stage stage) {
        employees = new ArrayList<>();

        AddEmployeePageView view = new AddEmployeePageView();
        addEmployeePage = view.createAddEmployeePage(employees, "Administrator");

        stage.setScene(new javafx.scene.Scene(addEmployeePage, 800, 600));
        stage.show();
    }

    @Test
    public void testAddEmployee(FxRobot robot) {
        verifyThat("#titleText", Node::isVisible);
        verifyThat("#empNameField", Node::isVisible);
        robot.clickOn("#empNameField").write("John Doe");
        robot.clickOn("#birthdayField").write("1990-01-01");
        robot.clickOn("#phoneNumField").write("+355 69 12 34 567");
        robot.clickOn("#emailField").write("jd20@epoka.edu.al");
        robot.clickOn("#salaryField").write("1000");
        robot.clickOn("#userNameField").write("johndoe");
        robot.clickOn("#passwordField").write("password123");

        ComboBox<String> roleComboBox = robot.lookup("#roleComboBox").query();
        robot.interact(() -> roleComboBox.getSelectionModel().select("Manager"));

        robot.clickOn("#saveButton");

        assertEquals(1, employees.size(), "Employee should be added to the list");

        Employee addedEmployee = employees.get(0);
        assertEquals("John Doe", addedEmployee.getName(), "Employee name should match");
        assertEquals("1990-01-01", addedEmployee.getBirthday(), "Birthday should match");
        assertEquals("+355 69 12 34 567", addedEmployee.getPhone(), "Phone number should match");
        assertEquals("jd20@epoka.edu.al", addedEmployee.getEmail(), "Email should match");
        assertEquals(1000, addedEmployee.getSalary(), "Salary should match");
        assertEquals(2, addedEmployee.getAccessLevel(), "Access level should match");
        assertEquals("johndoe", addedEmployee.getUserName(), "Username should match");
        assertEquals("password123", addedEmployee.getPassword(), "Password should match");
    }

    @Test
    public void testIncompleteFields(FxRobot robot) {
        robot.clickOn("#saveButton");

        assertEquals(0, employees.size(), "No employee should be added if fields are incomplete");
    }

    @Test
    public void testDuplicateUsername(FxRobot robot) {
        employees.add(new Employee("Jane Doe", "1995-05-05", "+355 68 98 76 543", "jd20@epoka.edu.al", 1200, 1, "johndoe", "password123"));

        robot.clickOn("#empNameField").write("John Doe");
        robot.clickOn("#birthdayField").write("1990-01-01");
        robot.clickOn("#phoneNumField").write("+355 69 12 34 567");
        robot.clickOn("#emailField").write("jd20@epoka.edu.al");
        robot.clickOn("#salaryField").write("1000");
        robot.clickOn("#userNameField").write("johndoe"); // Duplicate username
        robot.clickOn("#passwordField").write("password123");

        robot.clickOn("#saveButton");

        assertEquals(1, employees.size(), "Duplicate username should not be added");
    }

    @Test
    public void testInvalidEmail(FxRobot robot) {
        robot.clickOn("#empNameField").write("John Doe");
        robot.clickOn("#birthdayField").write("1990-01-01");
        robot.clickOn("#phoneNumField").write("+355 69 12 34 567");
        robot.clickOn("#emailField").write("invalid-email");
        robot.clickOn("#salaryField").write("1000");
        robot.clickOn("#userNameField").write("johndoe");
        robot.clickOn("#passwordField").write("password123");

        robot.clickOn("#saveButton");

        assertEquals(0, employees.size(), "No employee should be added with an invalid email");
    }

    @Test
    public void testInvalidPhoneNumber(FxRobot robot) {
        robot.clickOn("#empNameField").write("John Doe");
        robot.clickOn("#birthdayField").write("1990-01-01");
        robot.clickOn("#phoneNumField").write("123456789");
        robot.clickOn("#emailField").write("jd20@epoka.edu.al");
        robot.clickOn("#salaryField").write("1000");
        robot.clickOn("#userNameField").write("johndoe");
        robot.clickOn("#passwordField").write("password123");

        robot.clickOn("#saveButton");

        assertEquals(0, employees.size(), "No employee should be added with an invalid phone number");
    }
}