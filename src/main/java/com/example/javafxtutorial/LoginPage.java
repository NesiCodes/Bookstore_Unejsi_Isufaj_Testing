package com.example.javafxtutorial;

import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.controller.RoleController;
import com.example.javafxtutorial.model.Employee;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends Application {
    private EmployeeController employeeController;

    @Override
    public void start(Stage primaryStage) {

        EmployeeController employeeController = new EmployeeController();
        RoleController roleController = new RoleController();

        Image image = new Image(getClass().getResource("logo.png").toExternalForm());
        ImageView imageView = new ImageView(image);

        Text companyNameText = new Text("BookLand");
        companyNameText.setFill(Color.rgb(255, 255, 255));
        companyNameText.setFont(Font.font("Showcard Gothic", FontWeight.BOLD, 24));

        VBox imageAndTextContainer = new VBox(imageView, companyNameText);
        imageAndTextContainer.setAlignment(Pos.CENTER);
        imageAndTextContainer.setSpacing(20);

        Text copyrightText = new Text("© Copyright 2024 Bookland, All rights reserved");
        copyrightText.setFill(Color.rgb(192, 192, 192));
        copyrightText.setFont(Font.font("Segoe UI Light", 12));

        FlowPane pane1 = new FlowPane(imageAndTextContainer);
        pane1.setOrientation(Orientation.VERTICAL);
        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(9, 121, 121), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        pane1.setBackground(background);
        pane1.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane1);

        StackPane stackPane = new StackPane(copyrightText);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setBackground(background);
        borderPane.setBottom(stackPane);

        VBox loginHeaderText = new VBox();
        loginHeaderText.setAlignment(Pos.CENTER);
        VBox actions = new VBox();
        actions.setSpacing(7);

        // Right side (pane2)
        Text loginText = new Text("LOGIN");
        loginText.setFill(Color.rgb(9, 121, 121));
        loginText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));

        Label username = new Label("Username                                                                             ");
        username.setFont(Font.font("Segoe UI",  12));
        TextField usernameTextField = new TextField();
        usernameTextField.setId("username_text_field");
        usernameTextField.setMaxWidth(Double.MAX_VALUE);
        usernameTextField.setPromptText("Enter Username");

        Label password = new Label("Password");
        password.setFont(Font.font("Segoe UI",  12));
        PasswordField passwordTextField = new PasswordField();
        passwordTextField.setPromptText("Enter Password");
        passwordTextField.setId("password_text_field");

        Button button = new Button("   Login   ");
        button.setTextFill(Color.rgb(235, 235, 235));
        button.setBackground(background);
        button.setFont(Font.font("Segoe UI",  12));
        button.setPrefWidth(68);
        button.setPrefHeight(32);
        button.setId("login_button");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Employee emp = employeeController.login(usernameTextField.getText(), passwordTextField.getText());
                if(emp != null){
                    switch (emp.getAccessLevel()){
                        case 1:
                            emp.setRole(roleController.getRoles()[0]);
                            break;
                        case 2:
                            emp.setRole(roleController.getRoles()[1]);
                            break;
                        case 3:
                            emp.setRole(roleController.getRoles()[2]);
                            break;
                    }
                    primaryStage.close();

                    Stage newStage = new Stage();
                    BookStoreApp bookStoreApp = new BookStoreApp(emp);
                    bookStoreApp.start(newStage);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Incorrect Username or Password");
                    alert.setContentText("Please check your username and password and try again.");

                    alert.showAndWait();
                }

            }
        });

        loginHeaderText.getChildren().add(loginText);
        actions.getChildren().addAll(username, usernameTextField, password, passwordTextField, button);

        VBox centeredContent = new VBox(loginHeaderText, actions);
        centeredContent.setAlignment(Pos.CENTER);
        centeredContent.setSpacing(30);

        centeredContent.setPadding(new Insets(0, 25, 50, 25));

        FlowPane pane2 = new FlowPane(centeredContent);
        pane2.setAlignment(Pos.CENTER);
        pane2.setOrientation(Orientation.VERTICAL);
        pane2.setVgap(5);
        pane2.setPadding(new Insets(25, 25, 25, 25));


        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(borderPane, pane2);
        splitPane.setOrientation(Orientation.HORIZONTAL);

        Scene scene = new Scene(splitPane, 800, 500);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Bookstore Application");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}