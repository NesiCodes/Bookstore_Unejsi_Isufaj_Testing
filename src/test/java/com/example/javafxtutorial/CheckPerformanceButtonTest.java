package com.example.javafxtutorial;
import com.example.javafxtutorial.controller.EmployeeController;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class CheckPerformanceButtonTest extends ApplicationTest {

    private EmployeeController mockEmployeeController;
    private LoginPage loginPage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loginPage = new LoginPage();
        loginPage.start(primaryStage);
    }

    @Test
    public void testCheckPerformanceButton() {
        File originalFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat");
        File backupFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat.backup");

        try {
            if (originalFile.exists()) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            TextField usernameField = lookup("#username_text_field").query();
            usernameField.setText("unejsi");
            sleep(500);

            TextField passwordField = lookup("#password_text_field").query();
            passwordField.setText("unejsi1");
            sleep(500);

            clickOn("#login_button");
            sleep(1000);

            sleep(100);

            DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

            Assertions.assertEquals("Low Quantity Books Alert",
                    ((Stage) dialogPane.getScene().getWindow()).getTitle());

            TextFlow textFlow = lookup("#low_quantity_text_flow").query();
            String alertContent = textFlow.getChildren().stream()
                    .map(node -> ((Text) node).getText())
                    .reduce("", String::concat);
//            Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));

            sleep(2000);
            Button okButton = lookup(".button").queryButton();
//            Assertions.assertEquals("OK", okButton.getText());
            clickOn(okButton);
            Label checkPerformanceButton = lookup("#checkPerformanceButton").query();
            clickOn(checkPerformanceButton);
            sleep(6000);


            Label employeeNameLabel = lookup("#employeeNameLabel").query();

            Label numberOfBillsLabel = lookup("#numberOfBillsLabel").query();

            Label booksSoldLabel = lookup("#booksSoldLabel").query();

            Label moneyMadeLabel = lookup("#moneyMadeLabel").query();

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
    public void testCheckPerformanceButtonRange1() {
        File originalFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat");
        File backupFile = new File("src/main/resources/com/example/javafxtutorial/database/employees.dat.backup");

        try {
            if (originalFile.exists()) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            TextField usernameField = lookup("#username_text_field").query();
            usernameField.setText("unejsi");
            sleep(500);

            TextField passwordField = lookup("#password_text_field").query();
            passwordField.setText("unejsi1");
            sleep(500);

            clickOn("#login_button");
            sleep(1000);

            sleep(100);

            DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

            Assertions.assertEquals("Low Quantity Books Alert",
                    ((Stage) dialogPane.getScene().getWindow()).getTitle());

            TextFlow textFlow = lookup("#low_quantity_text_flow").query();
            String alertContent = textFlow.getChildren().stream()
                    .map(node -> ((Text) node).getText())
                    .reduce("", String::concat);

            sleep(3000);
            Button okButton = lookup(".button").queryButton();
//        Assertions.assertEquals("OK", okButton.getText());
            clickOn(okButton);
            Label checkPerformanceButton = lookup("#checkPerformanceButton").query();
            clickOn(checkPerformanceButton);
            sleep(1000);


            Assertions.assertNotNull(lookup("#checkPerformancePage").query(), "Check Performance Page should be displayed");

            DatePicker fromDatePicker = lookup("#fromDatePicker").query();
            DatePicker toDatePicker = lookup("#toDatePicker").query();

            LocalDate fromDate = LocalDate.of(2023, 1, 1);
            LocalDate toDate = LocalDate.of(2023, 12, 31);

            interact(() -> {
                fromDatePicker.setValue(fromDate);
                toDatePicker.setValue(toDate);
            });
            sleep(500);

            Button searchPButton = lookup("#searchPButton").query(); // Fixed ID: searchButton
            clickOn(searchPButton);
            sleep(1000);

            Label employeeNameLabel = lookup("#employeeNameLabel").query();
            Label numberOfBillsLabel = lookup("#numberOfBillsLabel").query();
            Label booksSoldLabel = lookup("#booksSoldLabel").query();
            Label moneyMadeLabel = lookup("#moneyMadeLabel").query();

            Assertions.assertEquals("Employee Name", employeeNameLabel.getText());
            Assertions.assertEquals("Number of Bills", numberOfBillsLabel.getText());
            Assertions.assertEquals("Books Sold", booksSoldLabel.getText());
            Assertions.assertEquals("Money Made", moneyMadeLabel.getText());

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
    }}