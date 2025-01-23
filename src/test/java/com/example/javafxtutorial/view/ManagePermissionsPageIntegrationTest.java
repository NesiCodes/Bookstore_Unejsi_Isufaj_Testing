package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.RoleController;
import com.example.javafxtutorial.model.Role;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

@ExtendWith(ApplicationExtension.class)
public class ManagePermissionsPageIntegrationTest {

    private HBox managePermissionsPage;
    private RoleController roleController;

    @Start
    public void start(Stage stage) {
        Role librarianRole = new Role("Librarian", new ArrayList<>());
        Role managerRole = new Role("Manager", new ArrayList<>());
        roleController = new RoleController();

        ManagePermissionsPageView view = new ManagePermissionsPageView(roleController);
        managePermissionsPage = view.createManagePermissionPage();

        stage.setScene(new javafx.scene.Scene(managePermissionsPage, 800, 600));
        stage.show();
    }

    @Test
    public void testLibrarianPermissions(FxRobot robot) throws InterruptedException {
        Thread.sleep(3000);
        verifyThat("#librarianSection", isVisible());

        verifyThat("#librarianHomeCheckBox", isSelected());
        verifyThat("#librarianCartCheckBox", isSelected());

        robot.clickOn("#librarianAddBookCheckBox");
        robot.clickOn("#librarianEditBooksCheckBox");

        verifyThat("#librarianAddBookCheckBox", isSelected());
        verifyThat("#librarianEditBooksCheckBox", isSelected());


        robot.clickOn("#librarianSaveButton");

        Role librarianRole = roleController.getRoles()[0];
        assertTrue(librarianRole.getPermissions().contains("Add Book"), "Add Book permission should be added");
        assertTrue(librarianRole.getPermissions().contains("Edit Books"), "Edit Books permission should be added");
    }

    @Test
    public void testManagerPermissions(FxRobot robot) throws InterruptedException {
        verifyThat("#managerSection", isVisible());

        verifyThat("#managerAddBookCheckBox", isSelected());
        verifyThat("#managerEditBooksCheckBox", isSelected());
        verifyThat("#managerCheckPerformanceCheckBox", isSelected());
        verifyThat("#managerStatisticsCheckBox", isSelected());

        robot.clickOn("#managerHomeCheckBox");
        robot.clickOn("#managerAddEmployeeCheckBox");

        verifyThat("#managerHomeCheckBox", isSelected());
        verifyThat("#managerAddEmployeeCheckBox", isSelected());

        robot.clickOn("#managerSaveButton");

        Role managerRole = roleController.getRoles()[1];
        assertTrue(managerRole.getPermissions().contains("Home"), "Home permission should be added");
        assertTrue(managerRole.getPermissions().contains("Add Employee"), "Add Employee permission should be added");
        Thread.sleep(3000);
    }

    @Test
    public void testDisabledPermissions(FxRobot robot) {
        verifyThat("#librarianHomeCheckBox", isDisabled());
        verifyThat("#librarianCartCheckBox", isDisabled());
        verifyThat("#librarianAddEmployeeCheckBox", isDisabled());
        verifyThat("#librarianManageEmployeesCheckBox", isDisabled());

        verifyThat("#managerAddBookCheckBox", isDisabled());
        verifyThat("#managerEditBooksCheckBox", isDisabled());
        verifyThat("#managerCheckPerformanceCheckBox", isDisabled());
        verifyThat("#managerStatisticsCheckBox", isDisabled());
    }

    private static org.hamcrest.Matcher<Node> isSelected() {
        return org.hamcrest.Matchers.hasProperty("selected", org.hamcrest.Matchers.is(true));
    }

    private static org.hamcrest.Matcher<Node> isNotSelected() {
        return org.hamcrest.Matchers.hasProperty("selected", org.hamcrest.Matchers.is(false));
    }
}