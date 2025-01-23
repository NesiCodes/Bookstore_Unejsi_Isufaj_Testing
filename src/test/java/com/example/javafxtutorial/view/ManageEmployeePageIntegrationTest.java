package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.model.Employee;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

@ExtendWith(ApplicationExtension.class)
public class ManageEmployeePageIntegrationTest {

    private ScrollPane manageEmployeePage;
    private EmployeeController employeeController;

    @Start
    public void start(Stage stage) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John Doe", "1990-01-01", "+355 69 12 34 567", "jd20@epoka.edu.al", 1000, 1, "johndoe", "password123"));
        employees.add(new Employee("Jane Doe", "1995-05-05", "+355 68 98 76 543", "jd21@epoka.edu.al", 1200, 2, "janedoe", "password456"));
        employeeController = new EmployeeController();
        employeeController.setEmployees(employees);

        ManageEmployeePageView view = new ManageEmployeePageView(employeeController);
        manageEmployeePage = view.createManageEmployeePage("Administrator"); // Role can be "Administrator" or "Manager"

        stage.setScene(new javafx.scene.Scene(manageEmployeePage, 800, 600));
        stage.show();
    }


    @Test
    public void testRemoveEmployee(FxRobot robot) {
        verifyThat("#employeeNameField", Node::isVisible);

        robot.clickOn("#removeButton");

        assertEquals(1, employeeController.getEmployees().size(), "Employee should be removed from the list");
    }



    @Test
    public void testEmptyFields(FxRobot robot) {
        robot.clickOn("#saveButton");

        Employee employee = employeeController.getEmployees().get(0);
        assertEquals("John Doe", employee.getName(), "Employee name should remain unchanged");
        assertEquals(1000, employee.getSalary(), "Employee salary should remain unchanged");
        assertEquals(1, employee.getAccessLevel(), "Employee role should remain unchanged");
        assertEquals("johndoe", employee.getUserName(), "Employee username should remain unchanged");
        assertEquals("password123", employee.getPassword(), "Employee password should remain unchanged");
    }
}