package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.model.Employee;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class ManageEmployeePageView {

    private EmployeeController employeeController;

    private String currentLoggedUserRole;

    public ManageEmployeePageView(EmployeeController employeeController){
        this.employeeController = employeeController;
    }

    public ScrollPane createManageEmployeePage(String roleName) {
        this.currentLoggedUserRole = roleName;
        VBox cartPage = new VBox();
        cartPage.setSpacing(10);
        cartPage.setAlignment(Pos.CENTER);
        cartPage.setPadding(new Insets(20));

        cartPage.getChildren().add(createManageEmployeeHeader());

        for(Employee e: employeeController.getEmployees()){
            if(currentLoggedUserRole.equals("Manager")){
                if (!(e.getAccessLevel() == 2 || e.getAccessLevel() == 3)){
                    cartPage.getChildren().add(createEmployeeItem(e, cartPage));
                }else{
                    //it wont show employees other than librarians
                }
            }else{ // its a administrator
                cartPage.getChildren().add(createEmployeeItem(e, cartPage));

            }
        }

        // Create a ScrollPane to enable scrolling if there are many cart items
        ScrollPane scrollPane = new ScrollPane(cartPage);
        scrollPane.setFitToWidth(true);
        scrollPane.setId("manageEmployeePage");


        return scrollPane;
    }

    private HBox createManageEmployeeHeader(){
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left
        cartItem.setId("employeeItem");

        Label empNameLabel = new Label("Emp Name");
        empNameLabel.setFont(Font.font("Berlin Sans FB", 16));
        empNameLabel.setTextFill(Color.WHITE);

        Label salaryLabel = new Label("Emp Salary");
        salaryLabel.setFont(Font.font("Berlin Sans FB", 16));
        salaryLabel.setTextFill(Color.WHITE);

        Label empRoleLabel = new Label("Emp Role");
        empRoleLabel.setFont(Font.font("Berlin Sans FB", 16));
        empRoleLabel.setTextFill(Color.WHITE);

        Label uesrNameLabel = new Label("Username");
        uesrNameLabel.setFont(Font.font("Berlin Sans FB", 16));
        uesrNameLabel.setTextFill(Color.WHITE);

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font("Berlin Sans FB", 16));
        passwordLabel.setTextFill(Color.WHITE);

        Label actions = new Label("Actions");
        actions.setFont(Font.font("Berlin Sans FB", 16));
        actions.setTextFill(Color.WHITE);

        cartItem.getChildren().addAll(empNameLabel, createRegion(), salaryLabel, createRegion(), empRoleLabel, createRegion(), uesrNameLabel, createRegion(), passwordLabel, createRegion(), actions);

        // Apply a lighter box shadow at the bottom
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.2)); // Adjust alpha for transparency
        cartItem.setEffect(dropShadow);
        cartItem.setStyle("-fx-background-radius: 0; -fx-background-color: #097979; -fx-padding: 10; ");
        Border border = new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0)));
        cartItem.setBorder(border);
        return cartItem;
    }

    private HBox createEmployeeItem(Employee employee, VBox cartPage) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        VBox employeeNameBox = new VBox();
        employeeNameBox.setSpacing(5);

        Label employeeNameLabel = new Label("Name: ");
        employeeNameLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField employeeNameField = new TextField();
        employeeNameField.setFont(Font.font("Berlin Sans FB", 14));
        employeeNameField.setPromptText(employee.getName());
        employeeNameField.setId("employeeNameField"); // Unique ID


        employeeNameBox.getChildren().addAll(employeeNameLabel, employeeNameField);
        employeeNameBox.setAlignment(Pos.CENTER);

        VBox salaryBox = new VBox();
        salaryBox.setSpacing(5);

        Label salaryLabel = new Label("Salary: ");
        salaryLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField salaryField = new TextField();
        salaryField.setFont(Font.font("Berlin Sans FB", 14));
        salaryField.setPromptText(String.valueOf(employee.getSalary()));
        salaryField.setId("salaryField"); // Unique ID

        salaryBox.getChildren().addAll(salaryLabel, salaryField);
        salaryBox.setAlignment(Pos.CENTER);

        VBox roleBox = new VBox();
        roleBox.setSpacing(5);

        Label roleLabel = new Label("Role: ");
        roleLabel.setFont(Font.font("Berlin Sans FB", 14));
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.setId("roleComboBox"); // Unique ID

        // Add options to the ComboBox
        if(currentLoggedUserRole.equals("Manager")){
            roleComboBox.getItems().addAll("Librarian");
        }else{
            roleComboBox.getItems().addAll("Librarian", "Manager", "Administrator");
        }
        // Set a default value (optional)
        if(employee.getAccessLevel() == 1){
            roleComboBox.setValue("Librarian");
        }else if(employee.getAccessLevel() == 2){
            roleComboBox.setValue("Manager");
        }else if(employee.getAccessLevel() == 3){
            roleComboBox.setValue("Administrator");
        }

        roleBox.getChildren().addAll(roleLabel, roleComboBox);
        roleBox.setAlignment(Pos.CENTER);


        VBox userNameBox = new VBox();
        userNameBox.setSpacing(5);

        Label userNameLabel = new Label("Username: ");
        userNameLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField userNameField = new TextField();
        userNameField.setFont(Font.font("Berlin Sans FB", 14));
        userNameField.setPromptText(employee.getUserName());
        userNameField.setId("userNameField"); // Unique ID


        userNameBox.getChildren().addAll(userNameLabel, userNameField);
        userNameBox.setAlignment(Pos.CENTER);

        VBox passwordBox = new VBox();
        passwordBox.setSpacing(5);

        Label passwordLabel = new Label("Password: ");
        passwordLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField passwordField = new TextField();
        passwordField.setFont(Font.font("Berlin Sans FB", 14));
        passwordField.setPromptText(employee.getPassword());
        passwordField.setId("passwordField"); // Unique ID

        passwordBox.getChildren().addAll(passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        VBox actionsBox = new VBox();
        actionsBox.setSpacing(5);

        Button saveButton = new Button("Save Changes");
        saveButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        saveButton.setTextFill(Color.WHITE);
        saveButton.setFont(Font.font("Berlin Sans FB", 14));
        saveButton.setId("saveButton"); // Unique ID
        saveButton.setOnAction(actionEvent -> {
            int accessLevel = -1;
            if(roleComboBox.getValue().equals("Librarian")){
                accessLevel = 1;
            } else if (roleComboBox.getValue().equals("Manager")) {
                accessLevel = 2;
            }else if (roleComboBox.getValue().equals("Administrator")){
                accessLevel = 3;
            }
            if (!employeeNameField.getText().isEmpty()){
                employee.setName(employeeNameField.getText());}
            if (!salaryField.getText().isEmpty()){
                employee.setSalary(Integer.parseInt(salaryField.getText()));}
            employee.setAccessLevel(accessLevel);

            if (!userNameField.getText().isEmpty()){
                employee.setUserName(userNameField.getText());}
            if (!passwordField.getText().isEmpty()){
                employee.setPassword(passwordField.getText());}

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Employee Info edited successfully");
            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.setId("saveSuccessAlert");

            alert.show();
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> alert.close());
            pause.play();
        });

        Button removeButton = new Button("Remove");
        removeButton.setBackground(Background.fill(Color.rgb(147,36,50)));
        removeButton.setTextFill(Color.WHITE);
        removeButton.setFont(Font.font("Berlin Sans FB", 14));
        removeButton.setId("removeButton"); // Unique ID
        removeButton.setOnAction(actionEvent -> {
            cartPage.getChildren().remove(cartItem);
            employeeController.getEmployees().remove(employee);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Employee removed from the system successfully");

            DialogPane dialogPane = alert.getDialogPane();

            dialogPane.setId("removeSuccessAlert");

            alert.show();
            PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event -> alert.close());
            pause.play();
        });


        actionsBox.getChildren().addAll(saveButton, removeButton);
        actionsBox.setAlignment(Pos.CENTER);

        // Add nodes to cart item
        cartItem.getChildren().addAll(employeeNameBox, createRegion(), salaryBox, createRegion(),roleBox,createRegion(),userNameBox,createRegion(),passwordBox, createRegion(), actionsBox);


        // Apply a lighter box shadow at the bottom
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.1)); // Adjust alpha for transparency
        cartItem.setEffect(dropShadow);
        cartItem.setStyle("-fx-background-radius: 10; -fx-background-color: #FFFFFF; -fx-padding: 10; ");
        Border border = new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0)));
        cartItem.setBorder(border);
        return cartItem;
    }

    private Region createRegion() {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

}







