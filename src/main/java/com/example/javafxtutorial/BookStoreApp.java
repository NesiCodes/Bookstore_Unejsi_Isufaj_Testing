package com.example.javafxtutorial;

import com.example.javafxtutorial.controller.*;
import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Role;
import com.example.javafxtutorial.view.*;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BookStoreApp extends Application {
    private Employee currentLoggedEmployee;

    public BookStoreApp(Employee currentLoggedEmployee) {
        this.currentLoggedEmployee = currentLoggedEmployee;
        System.out.println(currentLoggedEmployee);
    }

    @Override
    public void start(Stage primaryStage) {

        BookController bookController = new BookController();
        CartController cartController = new CartController();
        OrderController orderController = new OrderController();
        EmployeeController employeeController = new EmployeeController();
        RoleController roleController = new RoleController();
        BorderPane root = new BorderPane();
        HomePageView homePageView = new HomePageView();


        // Create navbar with two sections: left and right
        BorderPane navbar = new BorderPane();
        navbar.setPadding(new Insets(10));

        // Left section of the navbar
        HBox leftNavbar = new HBox();
        leftNavbar.setAlignment(Pos.CENTER_LEFT);

        // Bookstore logo
        ImageView logoImageView = new ImageView(new Image(getClass().getResource("booklandhome.png").toExternalForm()));
        logoImageView.setId("logo");

//        logoImageView.setFitWidth(100);
//        logoImageView.setFitHeight(50);

        // Search field
        HBox searchBox = new HBox(0);
        TextField searchField = new TextField();
        searchField.setId("SearchTextField");
        searchField.setPromptText("Search");

        ComboBox<String> searchTypeOptions = new ComboBox<>();
        searchTypeOptions.setId(".combo-box");
        searchTypeOptions.getItems().addAll("Name", "Category", "Author");
        searchTypeOptions.setValue("Name"); // Set default search type

        ImageView searchIcon = new ImageView(new Image(getClass().getResource("searchIcon.png").toExternalForm()));
        searchIcon.setFitWidth(17);
        searchIcon.setFitHeight(17);
        Button searchButton = new Button();
        searchButton.setGraphic(searchIcon);
        searchButton.setId(".button");
        searchBox.getChildren().addAll(searchField, searchTypeOptions, searchButton);
        searchBox.setPadding(new Insets(10, 0, 0, 20));
        searchButton.setOnAction(actionEvent -> {
            String searchType = searchTypeOptions.getValue();
            String searchText = searchField.getText();
            List<Book> filteredBooks = filterBooks(searchText, searchType, bookController);
            homePageView.updateDisplayedBooks(filteredBooks, cartController);
        });

        // Create a Line (ndarese)
        Line separatorLine = new Line();

        separatorLine.setStartX(0);
        separatorLine.setEndX(0);
        separatorLine.setStartY(0);
        separatorLine.setEndY(40); // Adjust the length as needed
        separatorLine.setStroke(Color.GRAY);

        leftNavbar.getChildren().addAll(logoImageView, separatorLine, searchBox);
        leftNavbar.setSpacing(2);

        // Right section of the navbar
        HBox rightNavbar = new HBox();
        rightNavbar.setAlignment(Pos.CENTER_RIGHT);
        rightNavbar.setPadding(new Insets(0, 20, 0, 180));
        rightNavbar.setSpacing(8);

        ImageView roleImageView = new ImageView(new Image(getClass().getResource(currentLoggedEmployee.getRole().getRoleName().toLowerCase() + "Prfp.png").toExternalForm()));
        roleImageView.setOpacity(0.75);
        roleImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(currentLoggedEmployee.toString());
                a.setHeaderText("User info");
                a.showAndWait();
            }
        });
        Label userNameLabel = new Label(currentLoggedEmployee.getName());
        userNameLabel.setFont(Font.font("Berlin Sans FB", 18));
        userNameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(currentLoggedEmployee.toString());
                a.setHeaderText("User info");
                a.showAndWait();
            }
        });
        Button logoutButton = new Button("Logout");
        logoutButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        logoutButton.setTextFill(Color.WHITE);
        logoutButton.setFont(Font.font("Berlin Sans FB"));
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bookController.writeBooksToFile();
                orderController.writeOrdersToFile();
                employeeController.writeEmployeesToFile();
                roleController.writeRolesToFile();
                // Close the current login window
                primaryStage.close();

                // Open the new window
                Stage newStage = new Stage();
                LoginPage loginPage = new LoginPage();
                loginPage.start(newStage);
            }
        });

        rightNavbar.getChildren().addAll(roleImageView, userNameLabel, logoutButton);

        // Combine left and right sections in the main navbar
        navbar.setLeft(leftNavbar);
        navbar.setRight(rightNavbar);
        navbar.setStyle("-fx-background-color: white;");

        // Create sidebar with buttons
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(200);

        Image homeIcon = new Image(getClass().getResource("homeIcon.png").toExternalForm());
        ImageView homeIconImageView = new ImageView(homeIcon);
        Label homeButton = new Label("Home", homeIconImageView);
        homeButton.setFont(Font.font("Berlin Sans FB", 18));

        Image cartIcon = new Image(getClass().getResource("cartIcon.png").toExternalForm());
        ImageView cartIconImageView = new ImageView(cartIcon);
        Label cartButton = new Label("Cart", cartIconImageView);
        cartButton.setFont(Font.font("Berlin Sans FB", 18));


        Image addBookIcon = new Image(getClass().getResource("addBookIcon.png").toExternalForm());
        ImageView addBookIconImageView = new ImageView(addBookIcon);
        Label addBookButton = new Label("Add Book", addBookIconImageView);
        addBookButton.setFont(Font.font("Berlin Sans FB", 18));
        addBookButton.setId("addBookButton");

        Image editBookIcon = new Image(getClass().getResource("editBookIcon.png").toExternalForm());
        ImageView editBookIconImageView = new ImageView(editBookIcon);
        Label editBookButton = new Label("Edit Books", editBookIconImageView);
        editBookButton.setFont(Font.font("Berlin Sans FB", 18));
        editBookButton.setId("editBookButton");


        Image checkPerformanceIcon = new Image(getClass().getResource("checkPerformanceIcon.png").toExternalForm());
        ImageView checkPerformanceImageView = new ImageView(checkPerformanceIcon);
        Label checkPerformanceButton = new Label("Check Performance", checkPerformanceImageView);
        checkPerformanceButton.setFont(Font.font("Berlin Sans FB", 18));
        checkPerformanceButton.setId("checkPerformanceButton");

        Image statisticsIcon = new Image(getClass().getResource("statisticsIcon.png").toExternalForm());
        ImageView statisticsImageView = new ImageView(statisticsIcon);
        Label statisticsButton = new Label("Statistics", statisticsImageView);
        statisticsButton.setFont(Font.font("Berlin Sans FB", 18));

        Image addEmployeeIcon = new Image(getClass().getResource("addEmployeeIcon.png").toExternalForm());
        ImageView addEmployeeEmployeeImageView = new ImageView(addEmployeeIcon);
        Label addEmployeeButton = new Label("Add Employee", addEmployeeEmployeeImageView);
        addEmployeeButton.setFont(Font.font("Berlin Sans FB", 18));

        Image manageEmployeeIcon = new Image(getClass().getResource("manageEmployeeIcon.png").toExternalForm());
        ImageView manageEmployeeImageView = new ImageView(manageEmployeeIcon);
        Label manageEmployeeButton = new Label("Manage Employees", manageEmployeeImageView);
        manageEmployeeButton.setFont(Font.font("Berlin Sans FB", 18));
        manageEmployeeButton.setId("manageEmployeeButton");

        Image managePermissionsIcon = new Image(getClass().getResource("securityIcon.png").toExternalForm());
        ImageView managePermissionsImageView = new ImageView(managePermissionsIcon);
        Label managePermissionsButton = new Label("Manage Permissions", managePermissionsImageView);
        managePermissionsButton.setFont(Font.font("Berlin Sans FB", 18));

        sidebar.getChildren().addAll(homeButton, cartButton, addBookButton, editBookButton, checkPerformanceButton, statisticsButton, addEmployeeButton, manageEmployeeButton, managePermissionsButton);
        sidebar.setAlignment(Pos.CENTER);
        sidebar.setPadding(new Insets(20, 10, 250, 10));
        sidebar.setSpacing(15);
        sidebar.setStyle("-fx-background-color: white;");
        // Combine navbar, sidebar, and book list in a BorderPane
        root.setTop(navbar);
        root.setLeft(sidebar);
        EditBookDetailsPageView editBookDetailsPage = new EditBookDetailsPageView();

        if (currentLoggedEmployee.getRole().getRoleName().equals("Administrator") || currentLoggedEmployee.getRole().getRoleName().equals("Librarian")) {
            root.setCenter(homePageView.showView(bookController.getBooks(), cartController));
        } else { // else its a manager
            if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), homeButton.getText(), roleController.getRoles())) {
                root.setCenter(homePageView.showView(bookController.getBooks(), cartController));
            } else {
                root.setCenter(editBookDetailsPage.createEditBookPage(bookController.getBooks(),false));
            }
        }
        cartButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), cartButton.getText(), roleController.getRoles())) {
                    CartPageView cartPageView = new CartPageView(cartController, orderController, bookController, currentLoggedEmployee, primaryStage);
                    root.setCenter(cartPageView.createCartPage());
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }

            }
        });
        homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), homeButton.getText(), roleController.getRoles())) {
                    root.setCenter(homePageView.showView(bookController.getBooks(), cartController));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                    ;
                }
            }
        });

        addBookButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), addBookButton.getText(), roleController.getRoles())) {
                    AddBookPageView addBookPageView = new AddBookPageView();
                    root.setCenter(addBookPageView.createAddBookPage(bookController.getBooks()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");
                    alert.getDialogPane().setId("access_denied_alert");
                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                    ;
                }

            }
        });
        editBookButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), editBookButton.getText(), roleController.getRoles())) {
                    root.setCenter(editBookDetailsPage.createEditBookPage(bookController.getBooks(),false));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                    ;
                }

            }
        });
        addEmployeeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), addEmployeeButton.getText(), roleController.getRoles())) {
                    AddEmployeePageView addEmployeePageView = new AddEmployeePageView();
                    root.setCenter(addEmployeePageView.createAddEmployeePage(employeeController.getEmployees(), currentLoggedEmployee.getRole().getRoleName()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                    ;
                }

            }
        });
        checkPerformanceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), checkPerformanceButton.getText(), roleController.getRoles())) {
                    CheckPerformancePageView checkPerformancePageView = new CheckPerformancePageView(orderController,employeeController, currentLoggedEmployee, primaryStage);
                    root.setCenter(checkPerformancePageView.createEmpPerformancePage());
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }
            }
        });

        statisticsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), statisticsButton.getText(), roleController.getRoles())){
                    StatisticsPageView statisticsPageView = new StatisticsPageView(orderController, bookController, employeeController);
                    root.setCenter(statisticsPageView.createStatisticsPage(currentLoggedEmployee.getRole().getRoleName()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }
            }
        });

        manageEmployeeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), manageEmployeeButton.getText(), roleController.getRoles())) {
                    ManageEmployeePageView manageEmployeePageView = new ManageEmployeePageView(employeeController);
                    root.setCenter(manageEmployeePageView.createManageEmployeePage(currentLoggedEmployee.getRole().getRoleName()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setId("accessDeniedAlert");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }

            }
        });

        managePermissionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (permissionExistsForRole(currentLoggedEmployee.getRole().getRoleName(), managePermissionsButton.getText(), roleController.getRoles())) {
                    ManagePermissionsPageView managePermissionsPageView = new ManagePermissionsPageView(roleController);
                    root.setCenter(managePermissionsPageView.createManagePermissionPage());
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Access Denied");
                    alert.setHeaderText(null);
                    alert.setContentText("You dont have permission to access this page");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                    ;
                }

            }
        });

        //options.requestFocus();

        // Create the scene and set it to the stage
        Scene scene = new Scene(root, 1052, 712);
        primaryStage.setTitle("Bookstore Application");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                bookController.writeBooksToFile();
                orderController.writeOrdersToFile();
                employeeController.writeEmployeesToFile();
                roleController.writeRolesToFile();
            }
        });

        primaryStage.show();

        // Display alert if there are books with low quantity
        if (!currentLoggedEmployee.getRole().getRoleName().equals("Librarian")) {
            // Get books with quantity less than 5
            List<Book> lowQuantityBooks = bookController.getBooks().stream()
                    .filter(book -> book.getQuantity() < 5)
                    .collect(Collectors.toList());

            if (!lowQuantityBooks.isEmpty()) {
                TextFlow textFlow = new TextFlow();
                textFlow.setPrefWidth(300);

                // Set an ID for the TextFlow to locate it during testing
                textFlow.setId("low_quantity_text_flow");

                Text titleText = new Text("Books with quantity less than 5:\n");
                titleText.setStyle("-fx-font-weight: bold;");

                textFlow.getChildren().add(titleText);
                int i = 0;
                for (Book book : lowQuantityBooks) {
                    String bookName;
                    if (i == lowQuantityBooks.size() - 1) {
                        bookName = book.getName() + ". ";
                    } else {
                        bookName = book.getName() + ", ";
                    }

                    // Check if adding the current book name exceeds the width
                    if (computeTextWidth(bookName, Font.font("System", 12)) + textFlow.getLayoutBounds().getWidth() > textFlow.getPrefWidth()) {
                        // Add a line break before adding the current book name
                        textFlow.getChildren().add(new Text("\n"));
                    }

                    Text bookText = new Text(bookName);
                    textFlow.getChildren().add(bookText);

                    i++;
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().setId("low_quantity_dialog_pane");
                alert.setTitle("Low Quantity Books Alert");
                alert.setHeaderText(null);
                alert.getDialogPane().setContent(textFlow);
                alert.showAndWait();
            } else {
                // If no books with low quantity, display a general message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("First Click Alert");
                alert.setHeaderText(null);
                alert.setContentText("This is the first time the button is clicked!\nNo books with quantity less than 5 found.");

                alert.showAndWait();
            }
        }

    }

    private double computeTextWidth(String text, Font font) {
        Text helper = new Text();
        helper.setFont(font);
        helper.setText(text);
        Scene scene = new Scene(new Group(helper));
        helper.applyCss();
        return helper.getLayoutBounds().getWidth();
    }


    private List<Book> filterBooks(String searchText, String searchType, BookController bc) {
        List<Book> filteredBooks = new ArrayList<>();
        ArrayList<Book> allBooks = bc.getBooks();

        for (Book book : allBooks) {
            if(searchType.equals("Name")){
                if(book.getName().toLowerCase().contains(searchText.toLowerCase())){
                    filteredBooks.add(book);
                }
            }else if(searchType.equals("Category")){
                if(book.getCategory().toLowerCase().contains(searchText.toLowerCase())){
                    filteredBooks.add(book);
                }
            }else if(searchType.equals("Author")){
                if(book.getAuthor().toLowerCase().contains(searchText.toLowerCase())){
                    filteredBooks.add(book);
                }
            }
        }

        return filteredBooks;
    }

    private boolean permissionExistsForRole(String roleName, String permissionName, Role[] roles) {
        if (roleName.equals("Administrator")) {
            return true;
        } else if (roleName.equals("Librarian")) {
            for (String s : roles[0].getPermissions()) {
                if (permissionName.equals(s)) {
                    return true;
                }
            }
            return false;
        } else { //if its not librarian than its a manager
            for (String s : roles[1].getPermissions()) {
                if (permissionName.equals(s)) {
                    return true;
                }
            }
            return false;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
