package com.example.javafxtutorial.view;

import com.example.javafxtutorial.auxiliary.BooksSoldWindow;
import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.controller.OrderController;
import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Order;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;


public class CheckPerformancePageView {

    private OrderController orderController;

    private EmployeeController employeeController;

    private Employee currentLoggedUser;

    private Stage primaryStage;

    public CheckPerformancePageView(OrderController orderController, EmployeeController employeeController, Employee currentLoggedUser, Stage primaryStage){
        this.orderController = orderController;
        this.employeeController = employeeController;
        this.currentLoggedUser = currentLoggedUser;
        this.primaryStage = primaryStage;
    }

    public ScrollPane createEmpPerformancePage() {
        // Create the bar with date pickers and search button
        HBox searchBar = createSearchBar();


        VBox cartPage = new VBox();
        cartPage.setSpacing(10);
        cartPage.setAlignment(Pos.CENTER);
        cartPage.setPadding(new Insets(20));

        cartPage.getChildren().addAll(searchBar, createEmpPerformanceHeader());


        for(Employee e: employeeController.getEmployees()){
            if (currentLoggedUser.getRole().getRoleName().equals("Manager")){
                if(!(e.getAccessLevel() == 2 || e.getAccessLevel() == 3) || e.getUserId().equals(currentLoggedUser.getUserId())){
                    cartPage.getChildren().add(createEmpPerformanceItem(e));
                }else{
                    //it wont show employyes other than librarians

                }
            }else if(currentLoggedUser.getRole().getRoleName().equals("Librarian")){
                if(e.getUserId().equals(currentLoggedUser.getUserId())){
                    cartPage.getChildren().add(createEmpPerformanceItem(e));
                }
            }
            else{ // its a administrator
                cartPage.getChildren().add(createEmpPerformanceItem(e));
            }
        }

        searchBar.getChildren().get(4).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    if (((DatePicker)searchBar.getChildren().get(1)).getValue().isAfter(((DatePicker)searchBar.getChildren().get(3)).getValue())){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Invalid Format");
                        alert.setHeaderText(null);
                        alert.setContentText("First date must not be greater than the second!");
// Set the ID for the alert's DialogPane
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.setId("invalidDateRangeAlert");
                        alert.show();
                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(event -> alert.close());
                        pause.play();
                    }else{
                        removeAllEmployees(cartPage);
                        for(Employee e: employeeController.getEmployees()){
                            if (currentLoggedUser.getRole().getRoleName().equals("Manager")){
                                if(!(e.getAccessLevel() == 2 || e.getAccessLevel() == 3) || e.getUserId().equals(currentLoggedUser.getUserId())){
                                    cartPage.getChildren().add(createEmpPerformanceItem(e, ((DatePicker)searchBar.getChildren().get(1)).getValue(), ((DatePicker)searchBar.getChildren().get(3)).getValue()));
                                }else{
                                    //it wont show employyes other than librarians
                                }
                            }else if(currentLoggedUser.getRole().getRoleName().equals("Librarian")){
                                if(e.getUserId().equals(currentLoggedUser.getUserId())){
                                    cartPage.getChildren().add(createEmpPerformanceItem(e, ((DatePicker)searchBar.getChildren().get(1)).getValue(), ((DatePicker)searchBar.getChildren().get(3)).getValue()));
                                }
                            }
                            else{ // its a administrator
                                cartPage.getChildren().add(createEmpPerformanceItem(e, ((DatePicker)searchBar.getChildren().get(1)).getValue(), ((DatePicker)searchBar.getChildren().get(3)).getValue()));
                            }
                        }
                    }
                }catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }

            }
        });

        // Create a ScrollPane to enable scrolling if there are many employees
        ScrollPane scrollPane = new ScrollPane(cartPage);
        scrollPane.setFitToWidth(true);
        scrollPane.setId("checkPerformancePage");
        return scrollPane;
    }

    private HBox createSearchBar() {
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setPadding(new Insets(10));

        DatePicker fromDatePicker = new DatePicker();
        fromDatePicker.setId("fromDatePicker");
        DatePicker toDatePicker = new DatePicker();
        toDatePicker.setId("toDatePicker");
        Button searchButton = new Button("Search");
        searchButton.setId("searchPButton");
        searchBar.getChildren().addAll(
                new Label("From"),
                fromDatePicker,
                new Label("To"),
                toDatePicker,
                searchButton
        );

        return searchBar;
    }

    private HBox createEmpPerformanceHeader(){
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        Label productImage = new Label("Employee Role");
        productImage.setFont(Font.font("Berlin Sans FB", 16));
        productImage.setTextFill(Color.WHITE);

        Label employeeName = new Label("Employee Name");
        employeeName.setFont(Font.font("Berlin Sans FB", 16));
        employeeName.setTextFill(Color.WHITE);
        employeeName.setId("employeeNameLabel");

        Label numberOfBills = new Label("Number of Bills");
        numberOfBills.setFont(Font.font("Berlin Sans FB", 16));
        numberOfBills.setTextFill(Color.WHITE);
        numberOfBills.setId("numberOfBillsLabel");
        Label booksSold = new Label("Books Sold");
        booksSold.setFont(Font.font("Berlin Sans FB", 16));
        booksSold.setTextFill(Color.WHITE);
        booksSold.setId("booksSoldLabel");
        Label moneyMade = new Label("Money Made");
        moneyMade.setFont(Font.font("Berlin Sans FB", 16));
        moneyMade.setTextFill(Color.WHITE);
        moneyMade.setId("moneyMadeLabel");

        cartItem.getChildren().addAll(productImage, createRegion(), employeeName, createRegion(), numberOfBills, createRegion(), booksSold, createRegion(), moneyMade);

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

    private HBox createEmpPerformanceItem(Employee e, LocalDate l2, LocalDate l3) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        String roleNameForImage;
        // Emp Role Image
        if(e.getAccessLevel() == 1){
            roleNameForImage = "librarianPrfpBigger.png";
        }else if(e.getAccessLevel() == 2){
            roleNameForImage = "managerPrfpBigger.png";
        }else{
            roleNameForImage = "administratorPrfpBigger.png";
        }
        ImageView roleImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/javafxtutorial/" + roleNameForImage)));
        roleImageView.setOpacity(0.8);

        VBox empNameBox = new VBox();
        empNameBox.setSpacing(5);

        Label empNameLabel = new Label("Full Name: ");
        empNameLabel.setFont(Font.font("Berlin Sans FB", 14));
        //empNameLabel.setId("employeeNameLabel");
        Label empName = new Label(e.getName());
        empName.setFont(Font.font("Berlin Sans FB", 14));

        empNameBox.getChildren().addAll(empNameLabel, empName);
        empNameBox.setAlignment(Pos.CENTER);

        Label numOfBills = new Label("Number of bills: " + getNumberOfBillsForEmployee(e, l2, l3));
        numOfBills.setFont(Font.font("Berlin Sans FB", 14));
//        numOfBills.setId("numberOfBillsLabel");

        Label booksSold = new Label("Books sold: " + getNumberOfBooksSoldForEmployee(e, l2, l3));
        booksSold.setFont(Font.font("Berlin Sans FB", 14));
//        booksSold.setId("booksSoldLabel");
        booksSold.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {


                int totalQuantity = 0;
                double totalPrice = 0;
                ArrayList<CartItem> cartItems = new ArrayList<>();

                for (Order o : orderController.getOrders()) {
                    Instant instant = o.getDateCreated().toInstant();

                    LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    if (isBetweenInclusive(l1, l2, l3)){
                        if (e.getUserId().equals(o.getEmployee().getUserId())) {
                            totalQuantity += o.getTotalQuantity();
                            totalPrice += o.getTotalPrice();
                            cartItems.addAll(o.getCartItems());
                        }
                    }

                }

                if (totalQuantity == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cant view books");
                    alert.setHeaderText(null);
                    alert.setContentText("There are no books sold");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }else{
                    BooksSoldWindow booksSoldWindow = new BooksSoldWindow(totalQuantity, totalPrice, cartItems);
                    Stage printStage = new Stage();

                    // Set the owner of the new stage to the primary stage
                    printStage.initOwner(primaryStage);

                    // Set modality to WINDOW_MODAL to make it a modal window
                    printStage.initModality(Modality.WINDOW_MODAL);

                    // Show the secondary stage
                    booksSoldWindow.showAndWait(printStage);
                }



            }
        });

        Label moneyMade = new Label("Money made: " + getMoneyMadeForEmployee(e, l2, l3));
        moneyMade.setFont(Font.font("Berlin Sans FB", 14));
//        moneyMade.setId("moneyMadeLabel");

        cartItem.getChildren().addAll(roleImageView, createRegion(), empNameBox, createRegion(),numOfBills,createRegion(),booksSold,createRegion(),moneyMade);


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

    private HBox createEmpPerformanceItem(Employee e) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        String roleNameForImage;
        // Emp Role Image
        if(e.getAccessLevel() == 1){
            roleNameForImage = "librarianPrfpBigger.png";
        }else if(e.getAccessLevel() == 2){
            roleNameForImage = "managerPrfpBigger.png";
        }else{
            roleNameForImage = "administratorPrfpBigger.png";
        }

        ImageView roleImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/javafxtutorial/" + roleNameForImage)));
        roleImageView.setOpacity(0.8);

        VBox empNameBox = new VBox();
        empNameBox.setSpacing(5);

        Label empNameLabel = new Label("Full Name: ");
        empNameLabel.setFont(Font.font("Berlin Sans FB", 14));
        Label empName = new Label(e.getName());
        empName.setFont(Font.font("Berlin Sans FB", 14));

        empNameBox.getChildren().addAll(empNameLabel, empName);
        empNameBox.setAlignment(Pos.CENTER);

        Label numOfBills = new Label("Number of bills: " + getNumberOfBillsForEmployee(e));
        numOfBills.setFont(Font.font("Berlin Sans FB", 14));

        Label booksSold = new Label("Books sold: " + getNumberOfBooksSoldForEmployee(e));
        booksSold.setFont(Font.font("Berlin Sans FB", 14));
        booksSold.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int totalQuantity = 0;
                double totalPrice = 0;
                ArrayList<CartItem> cartItems = new ArrayList<>();

                for (Order o : orderController.getOrders()) {
                    if (e.getUserId().equals(o.getEmployee().getUserId())) {
                        totalQuantity += o.getTotalQuantity();
                        totalPrice += o.getTotalPrice();
                        cartItems.addAll(o.getCartItems());
                    }
                }

                if (totalQuantity == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cant view books");
                    alert.setHeaderText(null);
                    alert.setContentText("There are no books sold");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }else{
                    BooksSoldWindow booksSoldWindow = new BooksSoldWindow(totalQuantity, totalPrice, cartItems);
                    Stage printStage = new Stage();

                    // Set the owner of the new stage to the primary stage
                    printStage.initOwner(primaryStage);

                    // Set modality to WINDOW_MODAL to make it a modal window
                    printStage.initModality(Modality.WINDOW_MODAL);

                    // Show the secondary stage
                    booksSoldWindow.showAndWait(printStage);
                }


            }
        });

        Label moneyMade = new Label("Money made: " + getMoneyMadeForEmployee(e));
        moneyMade.setFont(Font.font("Berlin Sans FB", 14));

        // Add nodes to cart item
        cartItem.getChildren().addAll(roleImageView, createRegion(), empNameBox, createRegion(),numOfBills,createRegion(),booksSold,createRegion(),moneyMade);


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

    private int getNumberOfBillsForEmployee(Employee e, LocalDate l2, LocalDate l3){
        int numberOfBills = 0;


        for (Order o: orderController.getOrders()){
            Instant instant = o.getDateCreated().toInstant();

            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (isBetweenInclusive(l1, l2, l3)){
                if (e.getUserId().equals(o.getEmployee().getUserId())){
                    numberOfBills += 1;
                }
            }

        }

        return numberOfBills;
    }

    private int getNumberOfBillsForEmployee(Employee e) {
        int numberOfBills = 0;


        for (Order o : orderController.getOrders()) {
            if (e.getUserId().equals(o.getEmployee().getUserId())) {
                numberOfBills += 1;
            }
        }

        return numberOfBills;
    }

    private int getNumberOfBooksSoldForEmployee(Employee e, LocalDate l2, LocalDate l3){
        int numberOfBooksSold = 0;

        for (Order o: orderController.getOrders()){
            Instant instant = o.getDateCreated().toInstant();

            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (isBetweenInclusive(l1, l2, l3)) {
                if (e.getUserId().equals(o.getEmployee().getUserId())){
                    numberOfBooksSold += o.getTotalQuantity();
                }
            }

        }

        return  numberOfBooksSold;
    }

    private int getNumberOfBooksSoldForEmployee(Employee e) {
        int numberOfBooksSold = 0;

        for (Order o : orderController.getOrders()) {
            if (e.getUserId().equals(o.getEmployee().getUserId())) {
                numberOfBooksSold += o.getTotalQuantity();
            }

        }

        return numberOfBooksSold;
    }

    private double getMoneyMadeForEmployee(Employee e, LocalDate l2, LocalDate l3){
        double moneyMade = 0;

        for (Order o: orderController.getOrders()){
            Instant instant = o.getDateCreated().toInstant();
            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (isBetweenInclusive(l1, l2, l3)) {
                if (e.getUserId().equals(o.getEmployee().getUserId())){
                    moneyMade += o.getTotalPrice();
                }
            }
        }

        return moneyMade;
    }

    private double getMoneyMadeForEmployee(Employee e) {
        double moneyMade = 0;

        for (Order o : orderController.getOrders()) {

            if (e.getUserId().equals(o.getEmployee().getUserId())) {
                moneyMade += o.getTotalPrice();
            }

        }

        return moneyMade;
    }

    public boolean isBetweenInclusive(LocalDate l1, LocalDate l2, LocalDate l3) {
        return (l1.isEqual(l2) || l1.isAfter(l2)) && (l1.isEqual(l3) || l1.isBefore(l3));
    }

    private void removeAllEmployees(VBox cartPage){

        ArrayList<Node> nodesToBeRemoved = new ArrayList<>();

        for (Node node: cartPage.getChildren()){

            if(node instanceof HBox){
                if (((HBox) node).getChildren().get(0) instanceof ImageView){
                    nodesToBeRemoved.add(node);
                }
            }

        }
        cartPage.getChildren().removeAll(nodesToBeRemoved);
    }

    private Region createRegion() {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

}

