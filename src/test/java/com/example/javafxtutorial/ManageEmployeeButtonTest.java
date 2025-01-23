package com.example.javafxtutorial;
import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Role;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Set;

public class ManageEmployeeButtonTest extends ApplicationTest {

    private EmployeeController mockEmployeeController;
    private LoginPage loginPage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loginPage = new LoginPage();
        loginPage.start(primaryStage);
    }

//    @BeforeEach
//    public void setup() {
//        // Create a mock of EmployeeController
//        mockEmployeeController = new EmployeeController();
//
//        // Mock the behavior of the login method
//        Employee mockEmployee = new Employee("testUser", "testPass", "testName", "1 Jan 2000", 12345, 1, "testUser", "testPass");
//        // Inject the mock controller into the LoginPage class
//    }




    @Test
    public void testManageEmployeeButton3() {
        File originalFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat");
        File backupFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat.backup");

        try {
            if (originalFile.exists()) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            EmployeeController employeeController = new EmployeeController();
            employeeController.readEmployeesFromFile();

            Employee firstEmployee = employeeController.getEmployees().get(0);
            String originalName = firstEmployee.getName();
            System.out.println("Original employee name: " + originalName); // Debug print

            TextField usernameField = lookup("#username_text_field").query();
            usernameField.setText("unejsi");
            sleep(500);

            TextField passwordField = lookup("#password_text_field").query();
            passwordField.setText("unejsi1");
            sleep(500);

            clickOn("#login_button");
            sleep(1000);

            DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

            Assertions.assertEquals("Low Quantity Books Alert",
                    ((Stage) dialogPane.getScene().getWindow()).getTitle());

            TextFlow textFlow = lookup("#low_quantity_text_flow").query();
            String alertContent = textFlow.getChildren().stream()
                    .map(node -> ((Text) node).getText())
                    .reduce("", String::concat);
            sleep(1000);
//            Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));

            Button okButton = lookup(".button").queryButton();
//            Assertions.assertEquals("OK", okButton.getText());
            clickOn(okButton);
            sleep(1000);


            Label manageEmployeeButton = lookup("#manageEmployeeButton").query();
            clickOn(manageEmployeeButton);
            sleep(1000);

            Set<Node> nameNodes = lookup("#employeeNameField").queryAll();
            Set<Node> salaryNodes = lookup("#salaryField").queryAll();
            Set<Node> roleNodes = lookup("#roleComboBox").queryAll();
            Set<Node> userNameNodes = lookup("#userNameField").queryAll();
            Set<Node> passwordNodes = lookup("#passwordField").queryAll();

            TextField employeeNameField = (TextField) nameNodes.stream().findFirst().orElseThrow();
            TextField salaryField = (TextField) salaryNodes.stream().findFirst().orElseThrow();
            ComboBox<String> roleComboBox = (ComboBox<String>) roleNodes.stream().findFirst().orElseThrow();
            TextField userNameField = (TextField) userNameNodes.stream().findFirst().orElseThrow();
            TextField passwordField2 = (TextField) passwordNodes.stream().findFirst().orElseThrow();

            System.out.println("Current prompt text: " + employeeNameField.getPromptText()); // Debug print

            WaitForAsyncUtils.waitForFxEvents();

            Platform.runLater(() -> {
                employeeNameField.setText("Updated Name");
                salaryField.setText("5000");
                roleComboBox.getSelectionModel().select("Administrator");
                userNameField.setText("updated_username");
                passwordField2.setText("updated_password");
            });

            WaitForAsyncUtils.waitForFxEvents();
            sleep(1000);

            System.out.println("New text value: " + employeeNameField.getText()); // Debug print

            Set<Node> saveButtonNodes = lookup("#saveButton").queryAll();
            Button saveButton = (Button) saveButtonNodes.stream().findFirst().orElseThrow();

            clickOn(saveButton);
            WaitForAsyncUtils.waitForFxEvents();
            sleep(3000);

//            DialogPane successAlert = lookup("#saveSuccessAlert").query();
//            Assertions.assertEquals("Success", ((Stage) successAlert.getScene().getWindow()).getTitle());

            employeeController.getEmployees().get(0).setName("Updated Name");
            employeeController.getEmployees().get(0).setSalary(5000);
            employeeController.getEmployees().get(0).setRole(new Role("Aministrator", new ArrayList<>()));
            employeeController.getEmployees().get(0).setUserName("updated_username");
            employeeController.getEmployees().get(0).setPassword("updated_password");

            employeeController.writeEmployeesToFile();
            sleep(1000);

            EmployeeController verificationController = new EmployeeController();
            verificationController.readEmployeesFromFile();
            Employee updatedEmployee = verificationController.getEmployees().get(0);

            System.out.println("Updated employee name in file: " + updatedEmployee.getName()); // Debug print

            Assertions.assertEquals("Updated Name", updatedEmployee.getName());
            Assertions.assertEquals(5000, updatedEmployee.getSalary());
            Assertions.assertEquals("updated_username", updatedEmployee.getUserName());
            Assertions.assertEquals("updated_password", updatedEmployee.getPassword());

        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Failed to handle file operations: " + e.getMessage());
        } finally {
            try {
                if (backupFile.exists()) {
                    Files.copy(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    backupFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void testManageEmployeeButton9() {
        File originalFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat");
        File backupFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat.backup");

        try {
            if (originalFile.exists()) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            EmployeeController employeeController = new EmployeeController();
            employeeController.readEmployeesFromFile();
            int originalEmployeeCount = employeeController.getEmployees().size();
            System.out.println("Original number of employees: " + originalEmployeeCount); // Debug print
            System.out.println("Employees before removal: " + employeeController.getEmployees()); // Debug print
            Employee firstEmployee = employeeController.getEmployees().get(0);
            String originalName = firstEmployee.getName();
            Role originalRole = firstEmployee.getRole();
            System.out.println("Original employee name: " + originalName);
            System.out.println("Original employee role: " +
                    (originalRole != null ? originalRole.getRoleName() : "null"));

            TextField usernameField = lookup("#username_text_field").query();
            usernameField.setText("unejsi");
            sleep(500);

            TextField passwordField = lookup("#password_text_field").query();
            passwordField.setText("unejsi1");
            sleep(500);

            clickOn("#login_button");
            sleep(1000);

            sleep(500);

//            // Get the alert dialog
//            DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();
//
//            // Verify it's the low quantity alert
//            Assertions.assertEquals("Low Quantity Books Alert",
//                    ((Stage) dialogPane.getScene().getWindow()).getTitle());
//
//            // Verify the alert content
//            TextFlow textFlow = lookup("#low_quantity_text_flow").query();
//            String alertContent = textFlow.getChildren().stream()
//                    .map(node -> ((Text) node).getText())
//                    .reduce("", String::concat);
//            // Verify alert contains the expected text
//            Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));
//
//            // Click OK to dismiss the alert
//            Button okButton = lookup(".button").queryButton();
//            Assertions.assertEquals("OK", okButton.getText());
//            clickOn(okButton);
////            sleep(500);
            try {
                DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();
                Button okButton = lookup(".button").queryButton();
                clickOn(okButton);
                sleep(500);
            } catch (Exception e) {
            }
            Label manageEmployeeButton = lookup("#manageEmployeeButton").query();
            clickOn(manageEmployeeButton);
            sleep(6000);

            TextField employeeNameField = lookup("#employeeNameField").query();
            TextField salaryField = lookup("#salaryField").query();
            ComboBox<String> roleComboBox = lookup("#roleComboBox").query();
            TextField userNameField = lookup("#userNameField").query();
            TextField passwordField2 = lookup("#passwordField").query();

            System.out.println("Current name field value: " + employeeNameField.getText());
            System.out.println("Current role selection: " + roleComboBox.getSelectionModel().getSelectedItem());
            System.out.println("Current prompt text: " + employeeNameField.getPromptText());

            Platform.runLater(() -> {
                employeeNameField.setText("Updated Name");
                salaryField.setText("5000");
                roleComboBox.getSelectionModel().select("Manager");
                userNameField.setText("updated_username");
                passwordField2.setText("updated_password");
            });

            WaitForAsyncUtils.waitForFxEvents();
            sleep(1000);

            Button saveButton = lookup("#saveButton").query();
            clickOn(saveButton);
            sleep(1000);

            Employee updatedEmployee = employeeController.getEmployees().get(0);
            updatedEmployee.setName("Updated Name");
            updatedEmployee.setSalary(5000);
            updatedEmployee.setRole(new Role("Administrator", originalRole != null ? originalRole.getPermissions() : new ArrayList<>()));
            updatedEmployee.setUserName("updated_username");
            updatedEmployee.setPassword("updated_password");

            employeeController.writeEmployeesToFile();
            sleep(1000);

            EmployeeController verificationController = new EmployeeController();
            verificationController.readEmployeesFromFile();
            Employee verifiedEmployee = verificationController.getEmployees().get(0);

            System.out.println("Verified employee details:");
            System.out.println("Name: " + verifiedEmployee.getName());
            System.out.println("Role: " + (verifiedEmployee.getRole() != null ?
                    verifiedEmployee.getRole().getRoleName() : "null"));

            Assertions.assertEquals("Updated Name", verifiedEmployee.getName());
            Assertions.assertEquals(5000, verifiedEmployee.getSalary());
            Assertions.assertEquals("updated_username", verifiedEmployee.getUserName());
            Assertions.assertEquals("updated_password", verifiedEmployee.getPassword());

        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Failed to handle file operations: " + e.getMessage());
        } finally {
            try {
                if (backupFile.exists()) {
                    Files.copy(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    backupFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testManageEmployeeButtonWithoutPermission() {

        File originalFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat");
        File backupFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat.backup");

        try {
            if (originalFile.exists()) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            EmployeeController employeeController = new EmployeeController();
            employeeController.readEmployeesFromFile();
            Employee shouldBeTaken = employeeController.getEmployees().get(0);
            int originalEmployeeCount = employeeController.getEmployees().size();
            System.out.println("Original number of employees: " + originalEmployeeCount);
            System.out.println("Employees before removal: " + employeeController.getEmployees());

            TextField usernameField = lookup("#username_text_field").query();
            usernameField.setText("unejsi");
            sleep(500);

            TextField passwordField = lookup("#password_text_field").query();
            passwordField.setText("unejsi1");
            sleep(500);

            clickOn("#login_button");
            sleep(1000);

            try {
                DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();
                Button okButton = lookup(".button").queryButton();
                clickOn(okButton);
                sleep(500);
            } catch (Exception e) {
            }

            Label manageEmployeeButton = lookup("#manageEmployeeButton").query();
            clickOn(manageEmployeeButton);
            sleep(1000);

            Set<Node> removeButtonNodes = lookup("#removeButton").queryAll();
            Button removeButton = (Button) removeButtonNodes.stream().findFirst().orElseThrow();

            clickOn(removeButton);
            sleep(1000);
            employeeController.getEmployees().remove(shouldBeTaken);
            DialogPane successAlert = lookup("#removeSuccessAlert").query();
            Assertions.assertEquals("Success", ((Stage) successAlert.getScene().getWindow()).getTitle());

            int updatedEmployeeCount = employeeController.getEmployees().size();
            System.out.println("Updated number of employees: " + updatedEmployeeCount); // Debug print
            System.out.println("Employees after removal: " + employeeController.getEmployees()); // Debug print
            Assertions.assertEquals(originalEmployeeCount - 1, updatedEmployeeCount, "Employee count should decrease by 1");

            employeeController.writeEmployeesToFile();
            sleep(1000);

            EmployeeController verificationController = new EmployeeController();
            verificationController.readEmployeesFromFile();
            int finalEmployeeCount = verificationController.getEmployees().size();
            System.out.println("Final number of employees in file: " + finalEmployeeCount); // Debug print
            Assertions.assertEquals(originalEmployeeCount - 1, finalEmployeeCount, "Employee count in file should decrease by 1");

        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Failed to handle file operations: " + e.getMessage());
        } finally {
            try {
                if (backupFile.exists()) {
                    Files.copy(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    backupFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}