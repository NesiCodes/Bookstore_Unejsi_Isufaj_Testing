package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.RoleController;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;

public class ManagePermissionsPageView {

    private RoleController roleController;

    public ManagePermissionsPageView(RoleController roleController){
        this.roleController = roleController;
    }

    public HBox createManagePermissionPage() {

        // Create sections for Librarian and Manager
        VBox librarianSection = createSection("Librarian", "librarian.png");
        VBox managerSection = createSection("Manager", "manager.png");

        HBox root = new HBox(100);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(librarianSection, managerSection);

        return root;
    }

    private VBox createSection(String role, String imageName) {
        // Create Image and ImageView for the user
        Image image = new Image(getClass().getResourceAsStream("/com/example/javafxtutorial/" + imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(130);
        imageView.setFitHeight(130);

        Label nameLabel = new Label(role);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Create Checkboxes for permissions
        CheckBox homePageCheckBox = new CheckBox("Home");
        homePageCheckBox.setId(role.toLowerCase() + "HomeCheckBox"); // Unique ID
        homePageCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox cartCheckBox = new CheckBox("Cart");
        cartCheckBox.setId(role.toLowerCase() + "CartCheckBox"); // Unique ID
        cartCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox addBookCheckBox = new CheckBox("Add Book");
        addBookCheckBox.setId(role.toLowerCase() + "AddBookCheckBox"); // Unique ID
        addBookCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox editBooksCheckBox = new CheckBox("Edit Books");
        editBooksCheckBox.setId(role.toLowerCase() + "EditBooksCheckBox"); // Unique ID
        editBooksCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox checkPerformanceCheckBox = new CheckBox("Check Performance");
        checkPerformanceCheckBox.setId(role.toLowerCase() + "CheckPerformanceCheckBox");
        checkPerformanceCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox statisticsCheckBox = new CheckBox("Statistics");
        statisticsCheckBox.setId(role.toLowerCase() + "StatisticsCheckBox");
        statisticsCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox addEmployeeCheckBox = new CheckBox("Add Employee");
        addEmployeeCheckBox.setId(role.toLowerCase() + "AddEmployeeCheckBox"); // Unique ID
        addEmployeeCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox manageEmployeesCheckBox = new CheckBox("Manage Employees");
        manageEmployeesCheckBox.setId(role.toLowerCase() + "ManageEmployeesCheckBox"); // Unique ID
        manageEmployeesCheckBox.setFont(Font.font("Berlin Sans FB", 14));

        CheckBox managePermissionsCheckBox = new CheckBox("Manage Permissions");
        managePermissionsCheckBox.setId(role.toLowerCase() + "ManagePermissionsCheckBox"); // Unique ID
        managePermissionsCheckBox.setFont(Font.font("Berlin Sans FB", 14));


        Button saveButton = new Button("Save Changes");
        saveButton.setId(role.toLowerCase() + "SaveButton"); // Unique ID
        saveButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        saveButton.setTextFill(Color.WHITE);
        saveButton.setFont(Font.font("Berlin Sans FB", 14));

        // Create a layout for the section
        VBox sectionLayout = new VBox(20);
        sectionLayout.setAlignment(Pos.CENTER);
        sectionLayout.getChildren().addAll(imageView, nameLabel,homePageCheckBox, cartCheckBox, addBookCheckBox, editBooksCheckBox,
                checkPerformanceCheckBox, statisticsCheckBox, addEmployeeCheckBox, manageEmployeesCheckBox, managePermissionsCheckBox, saveButton);

        sectionLayout.setId(role.toLowerCase() + "Section");
        imageView.setId(role.toLowerCase() + "ImageView");


        if(role.equals("Librarian")){
            homePageCheckBox.setDisable(true);
            cartCheckBox.setDisable(true);
            addEmployeeCheckBox.setDisable(true);
            manageEmployeesCheckBox.setDisable(true);
            managePermissionsCheckBox.setDisable(true);
            homePageCheckBox.setSelected(permissionExistsForRole(role, homePageCheckBox.getText()));
            cartCheckBox.setSelected(permissionExistsForRole(role, cartCheckBox.getText()));
            addBookCheckBox.setSelected(permissionExistsForRole(role, addBookCheckBox.getText()));
            editBooksCheckBox.setSelected(permissionExistsForRole(role, editBooksCheckBox.getText()));
            checkPerformanceCheckBox.setSelected(permissionExistsForRole(role, checkPerformanceCheckBox.getText()));
            statisticsCheckBox.setSelected(permissionExistsForRole(role, statisticsCheckBox.getText()));
            addEmployeeCheckBox.setSelected(permissionExistsForRole(role, addEmployeeCheckBox.getText()));
            manageEmployeesCheckBox.setSelected(permissionExistsForRole(role, manageEmployeesCheckBox.getText()));

        }else if(role.equals("Manager")){
            addBookCheckBox.setDisable(true);
            editBooksCheckBox.setDisable(true);
            checkPerformanceCheckBox.setDisable(true);
            statisticsCheckBox.setDisable(true);
            managePermissionsCheckBox.setDisable(true);
            homePageCheckBox.setSelected(permissionExistsForRole(role, homePageCheckBox.getText()));
            cartCheckBox.setSelected(permissionExistsForRole(role, cartCheckBox.getText()));
            addBookCheckBox.setSelected(permissionExistsForRole(role, addBookCheckBox.getText()));
            editBooksCheckBox.setSelected(permissionExistsForRole(role, editBooksCheckBox.getText()));
            checkPerformanceCheckBox.setSelected(permissionExistsForRole(role, checkPerformanceCheckBox.getText()));
            statisticsCheckBox.setSelected(permissionExistsForRole(role, statisticsCheckBox.getText()));
            addEmployeeCheckBox.setSelected(permissionExistsForRole(role, addEmployeeCheckBox.getText()));
            manageEmployeesCheckBox.setSelected(permissionExistsForRole(role, manageEmployeesCheckBox.getText()));
        }


        saveButton.setOnAction(actionEvent -> {
            if(role.equals("Librarian")){
                ArrayList<String> newPermissions = new ArrayList<>();
                for (Node node : sectionLayout.getChildren()) {
                    if (node instanceof CheckBox) {
                        if (((CheckBox) node).isSelected()){
                            newPermissions.add(((CheckBox) node).getText());
                        }
                    }
                }
                roleController.getRoles()[0].setPermissions(newPermissions);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Permissions saved successfully");

                alert.show();
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> alert.close());
                pause.play();
            }else if(role.equals("Manager")){
                ArrayList<String> newPermissions = new ArrayList<>();
                for (Node node : sectionLayout.getChildren()) {
                    if (node instanceof CheckBox) {
                        if (((CheckBox) node).isSelected()){
                            newPermissions.add(((CheckBox) node).getText());
                        }
                    }
                }
                roleController.getRoles()[1].setPermissions(newPermissions);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Permissions saved successfully");

                alert.show();
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> alert.close());
                pause.play();
            }
        });

        BackgroundFill backgroundFill = new BackgroundFill(javafx.scene.paint.Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        sectionLayout.setBackground(background);
        sectionLayout.setPadding(new Insets(12));

        VBox finalLayout = new VBox();
        finalLayout.setAlignment(Pos.CENTER);
        finalLayout.getChildren().add(sectionLayout);
        return finalLayout;
    }

    private boolean permissionExistsForRole(String roleName, String permissionName){
        if(roleName.equals("Librarian")){
            for (String s: roleController.getRoles()[0].getPermissions()){
                if (permissionName.equals(s)){
                    return true;
                }
            }
            return false;
        }else{ //if its not librarian than its a manager
            for (String s: roleController.getRoles()[1].getPermissions()){
                if (permissionName.equals(s)){
                    return true;
                }
            }
            return false;
        }
    }

}