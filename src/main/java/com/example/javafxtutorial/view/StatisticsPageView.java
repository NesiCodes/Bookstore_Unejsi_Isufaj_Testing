package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.BookController;
import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.controller.OrderController;
import com.example.javafxtutorial.model.Book;
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
import javafx.util.Duration;

import java.io.File;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class StatisticsPageView {

    private OrderController orderController;

    private BookController bookController;

    private EmployeeController employeeController;


    public StatisticsPageView(OrderController orderController, BookController bookController, EmployeeController employeeController){
        this.orderController = orderController;
        this.bookController = bookController;
        this.employeeController = employeeController;
    }

    public ScrollPane createStatisticsPage(String currentLoggedUserRole) {
        // Create the bar with date pickers and search button
        HBox searchBar = createSearchBar();


        VBox cartPage = new VBox();
        cartPage.setSpacing(10);
        cartPage.setAlignment(Pos.CENTER);
        cartPage.setPadding(new Insets(20));

        cartPage.getChildren().addAll(searchBar, createStatisticsHeader());

        for (Book b: bookController.getBooks()){
            cartPage.getChildren().add(createBookStatItem(b));
        }

        if (currentLoggedUserRole.equals("Administrator")){
            cartPage.getChildren().add(createStatisticsEnd(cartPage));
        }

        // Set IDs for search bar components
        ((DatePicker) searchBar.getChildren().get(1)).setId("fromDatePicker");
        ((DatePicker) searchBar.getChildren().get(3)).setId("toDatePicker");
        ((Button) searchBar.getChildren().get(4)).setId("searchButton");


        searchBar.getChildren().get(4).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    if (((DatePicker)searchBar.getChildren().get(1)).getValue().isAfter(((DatePicker)searchBar.getChildren().get(3)).getValue())){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Invalid Format");
                        alert.setHeaderText(null);
                        alert.setContentText("First date must not be greater than the second!");

                        alert.show();
                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(event -> alert.close());
                        pause.play();
                    }else{
                        removeAllBookStats(cartPage);
                        if (currentLoggedUserRole.equals("Administrator")){
                            cartPage.getChildren().remove(cartPage.getChildren().size()-1);
                        }
                        for (Book b: bookController.getBooks()){
                            cartPage.getChildren().add(createBookStatItem(b,((DatePicker)searchBar.getChildren().get(1)).getValue(), ((DatePicker)searchBar.getChildren().get(3)).getValue()));
                        }
                        if (currentLoggedUserRole.equals("Administrator")){
                            cartPage.getChildren().add(createStatisticsEnd(cartPage, ((DatePicker)searchBar.getChildren().get(1)).getValue(), ((DatePicker)searchBar.getChildren().get(3)).getValue()));
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

        return scrollPane;
    }

    private HBox createSearchBar() {
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setPadding(new Insets(10));

        DatePicker fromDatePicker = new DatePicker();
        DatePicker toDatePicker = new DatePicker();
        Button searchButton = new Button("Search");

        searchBar.getChildren().addAll(
                new Label("From"),
                fromDatePicker,
                new Label("To"),
                toDatePicker,
                searchButton
        );

        return searchBar;
    }

    private HBox createStatisticsHeader(){
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        Label productImage = new Label("Book Image");
        productImage.setFont(Font.font("Berlin Sans FB", 16));
        productImage.setTextFill(Color.WHITE);

        Label boughtInformation = new Label("Bought Information");
        boughtInformation.setFont(Font.font("Berlin Sans FB", 16));
        boughtInformation.setTextFill(Color.WHITE);

        Label soldInformation = new Label("Sold Information");
        soldInformation.setFont(Font.font("Berlin Sans FB", 16));
        soldInformation.setTextFill(Color.WHITE);


        cartItem.getChildren().addAll(productImage, createRegion(), boughtInformation, createRegion(), soldInformation);

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

    private HBox createBookStatItem(Book b, LocalDate l2, LocalDate l3) {
        HBox cartItem = new HBox();
        cartItem.setId("bookStatItem"); // Set ID for the book stat item
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        ImageView bookImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/javafxtutorial/" + b.getImagePath())));
        bookImageView.setFitWidth(80);
        bookImageView.setFitHeight(120);

        VBox boughtInfoBox = new VBox();
        boughtInfoBox.setSpacing(5);

        Label boughtQuantity = new Label("Bough Quantity: " + getBoughtQuantityForBook(b,l2,l3));
        boughtQuantity.setFont(Font.font("Berlin Sans FB", 14));
        Label moneySpent = new Label("Money Spent: " + getMoneySpentForBook(b,l2,l3));
        moneySpent.setFont(Font.font("Berlin Sans FB", 14));

        boughtInfoBox.getChildren().addAll(boughtQuantity, moneySpent);
        boughtInfoBox.setAlignment(Pos.CENTER);

        VBox soldInfoBox = new VBox();
        soldInfoBox.setSpacing(5);

        Label soldQuantity = new Label("Sold Quantity: " + getSoldQuantityForBook(b,l2,l3));
        soldQuantity.setFont(Font.font("Berlin Sans FB", 14));
        Label moneyEarned = new Label("Money Earned: " + getMoneyEarnedFromBook(b,l2,l3));
        moneyEarned.setFont(Font.font("Berlin Sans FB", 14));

        soldInfoBox.getChildren().addAll(soldQuantity, moneyEarned);
        soldInfoBox.setAlignment(Pos.CENTER);


        // Add nodes to cart item
        cartItem.getChildren().addAll(bookImageView, createRegion(), boughtInfoBox, createRegion(),soldInfoBox);


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

    private HBox createBookStatItem(Book b) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT);

        // Use the ClassLoader to load the image
        String imagePath = "src/main/resources/com/example/javafxtutorial/" + b.getImagePath();

        Image image = new Image(new File(imagePath).toURI().toString());

        ImageView bookImageView = new ImageView(image);
        bookImageView.setFitWidth(80);
        bookImageView.setFitHeight(120);

        VBox boughtInfoBox = new VBox();
        boughtInfoBox.setSpacing(5);

        Label boughtQuantity = new Label("Bough Quantity: " + getBoughtQuantityForBook(b));
        boughtQuantity.setFont(Font.font("Berlin Sans FB", 14));
        Label moneySpent = new Label("Money Spent: " + getMoneySpentForBook(b) + "$");
        moneySpent.setFont(Font.font("Berlin Sans FB", 14));

        boughtInfoBox.getChildren().addAll(boughtQuantity, moneySpent);
        boughtInfoBox.setAlignment(Pos.CENTER);

        VBox soldInfoBox = new VBox();
        soldInfoBox.setSpacing(5);

        Label soldQuantity = new Label("Sold Quantity: " + getSoldQuantityForBook(b));
        soldQuantity.setFont(Font.font("Berlin Sans FB", 14));
        Label moneyEarned = new Label("Money Earned: " + getMoneyEarnedFromBook(b) + "$");
        moneyEarned.setFont(Font.font("Berlin Sans FB", 14));

        soldInfoBox.getChildren().addAll(soldQuantity, moneyEarned);
        soldInfoBox.setAlignment(Pos.CENTER);


        // Add nodes to cart item
        cartItem.getChildren().addAll(bookImageView, createRegion(), boughtInfoBox, createRegion(),soldInfoBox);


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

    private HBox createStatisticsEnd(VBox cartPage) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        VBox cartTotal = new VBox();
        cartTotal.setSpacing(10);

        Label totalIncomesLabel = new Label("Total Incomes: " + getTotalIncomes() + "$");
        totalIncomesLabel.setId("totalIncomesLabel"); // Set ID for total incomes label
        totalIncomesLabel.setFont(Font.font("Berlin Sans FB", 16));

        Label totalCostLabel = new Label("Total Cost: " + getTotalCost() + "$");
        totalCostLabel.setId("totalCostLabel"); // Set ID for total cost label
        totalCostLabel.setFont(Font.font("Berlin Sans FB", 16));

        cartTotal.getChildren().addAll(totalIncomesLabel, totalCostLabel);

        cartItem.getChildren().addAll(createRegion(), createRegion(), cartTotal);


        // Apply a lighter box shadow at the bottom
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.2)); // Adjust alpha for transparency
        cartItem.setEffect(dropShadow);
        cartItem.setStyle("-fx-background-radius: 0; -fx-background-color: #FFFFFF; -fx-padding: 10; ");
        Border border = new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0)));
        cartItem.setBorder(border);
        return cartItem;
    }

    private HBox createStatisticsEnd(VBox cartPage, LocalDate l2, LocalDate l3) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        VBox cartTotal = new VBox();
        cartTotal.setSpacing(10);

        Label totalIncomesLabel = new Label("Total Incomes: " + getTotalIncomes(l2,l3) + "$");
        totalIncomesLabel.setFont(Font.font("Berlin Sans FB", 16));

        Label totalCostLabel = new Label("Total Cost: " + getTotalCost(l2,l3) + "$");
        totalCostLabel.setFont(Font.font("Berlin Sans FB", 16));

        cartTotal.getChildren().addAll(totalIncomesLabel, totalCostLabel);

        cartItem.getChildren().addAll(createRegion(), createRegion(), cartTotal);


        // Apply a lighter box shadow at the bottom
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.2)); // Adjust alpha for transparency
        cartItem.setEffect(dropShadow);
        cartItem.setStyle("-fx-background-radius: 0; -fx-background-color: #FFFFFF; -fx-padding: 10; ");
        Border border = new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0)));
        cartItem.setBorder(border);
        return cartItem;
    }

    public int getBoughtQuantityForBook(Book b){

        double sum = 0;

        for (Map.Entry<Date, Double> entry : b.getPurchasedInfo().entrySet()) {

            double value = entry.getValue();
            sum += value;
        }


        return (int) (sum / b.getPrice());
    }

    public int getBoughtQuantityForBook(Book b, LocalDate l2, LocalDate l3){

        double sum = 0;

        for (Map.Entry<Date, Double> entry : b.getPurchasedInfo().entrySet()) {

            Instant instant = entry.getKey().toInstant();
            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (isBetweenInclusive(l1, l2, l3)) {
                double value = entry.getValue();
                sum += value;
            }
        }

        return (int) (sum / b.getPrice());
    }

    public double getMoneySpentForBook(Book b){

        double sum = 0;

        for (Map.Entry<Date, Double> entry : b.getPurchasedInfo().entrySet()) {
            double value = entry.getValue();
            sum += value;
        }


        return sum;
    }

    public double getMoneySpentForBook(Book b, LocalDate l2, LocalDate l3){

        double sum = 0;

        for (Map.Entry<Date, Double> entry : b.getPurchasedInfo().entrySet()) {
            Instant instant = entry.getKey().toInstant();
            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (isBetweenInclusive(l1, l2, l3)) {
                double value = entry.getValue();
                sum += value;
            }
        }

        return sum;
    }

    public int getSoldQuantityForBook(Book b){

        int soldQuantity = 0;

        for (Order o: orderController.getOrders()){

            for (CartItem c: o.getCartItems()){

                if (c.getBook().getIsbn().equals(b.getIsbn())){
                    soldQuantity += c.getQuantity();
                }

            }
        }
        return soldQuantity;
    }

    public int getSoldQuantityForBook(Book b, LocalDate l2, LocalDate l3) {
        int soldQuantity = 0;

        for (Order o : orderController.getOrders()) {
            Instant instant = o.getDateCreated().toInstant();
            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            System.out.println("Order Date: " + l1); // Debug: Print the order date

            if (isBetweenInclusive(l1, l2, l3)) {
                for (CartItem c : o.getCartItems()) {
                    if (c.getBook().getIsbn().equals(b.getIsbn())) {
                        System.out.println("Cart Item: " + c.getQuantity() + " copies of " + b.getName()); // Debug: Print the cart item
                        soldQuantity += c.getQuantity();
                    }
                }
            }
        }

        System.out.println("Total Sold Quantity: " + soldQuantity); // Debug: Print the total sold quantity
        return soldQuantity;
    }

    public double getMoneyEarnedFromBook(Book b){

        double moneyEarned = 0;

        for (Order o: orderController.getOrders()){


            for (CartItem c: o.getCartItems()){

                if (c.getBook().getIsbn().equals(b.getIsbn())){
                    moneyEarned += c.getSubtotal();
                }

            }
        }
        return moneyEarned;

    }

    public double getMoneyEarnedFromBook(Book b, LocalDate l2, LocalDate l3){

        double moneyEarned = 0;



        for (Order o: orderController.getOrders()){

            Instant instant = o.getDateCreated().toInstant();
            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (isBetweenInclusive(l1, l2, l3)) {
                for (CartItem c: o.getCartItems()){

                    if (c.getBook().getIsbn().equals(b.getIsbn())){
                        moneyEarned += c.getSubtotal();
                    }

                }
            }

        }
        return moneyEarned;

    }

    public double getTotalIncomes(){
        double totalIncomes = 0;

        for (Order o: orderController.getOrders()){

            totalIncomes += o.getTotalPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(totalIncomes));
    }

    public double getTotalIncomes(LocalDate l2, LocalDate l3){
        double totalIncomes = 0;

        for (Order o: orderController.getOrders()){
            Instant instant = o.getDateCreated().toInstant();
            LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if (isBetweenInclusive(l1, l2, l3)) {
                totalIncomes += o.getTotalPrice();
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(totalIncomes));
    }


    public double getTotalCost(){
        double totalCosts = 0;

        for (Book b: bookController.getBooks()){
            for (Map.Entry<Date, Double> entry : b.getPurchasedInfo().entrySet()) {

                double value = entry.getValue();
                totalCosts += value;

            }
        }

        //cant calculate staff salaries with total filter


        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(totalCosts));
    }

    public double getTotalCost(LocalDate l2, LocalDate l3){
        double totalCosts = 0;

        for (Book b: bookController.getBooks()){
            for (Map.Entry<Date, Double> entry : b.getPurchasedInfo().entrySet()) {

                Instant instant = entry.getKey().toInstant();
                LocalDate l1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                if (isBetweenInclusive(l1,l2,l3)){
                    double value = entry.getValue();
                    totalCosts += value;
                }
            }
        }

        int differenceInDays = (int) ChronoUnit.DAYS.between(l2, l3);
        int months = differenceInDays / 30;

        if (months >= 1){
            for (Employee e: employeeController.getEmployees()){
                totalCosts += (months * e.getSalary());
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(totalCosts));
    }


    public boolean isBetweenInclusive(LocalDate l1, LocalDate l2, LocalDate l3) {
        return (l1.isEqual(l2) || l1.isAfter(l2)) && (l1.isEqual(l3) || l1.isBefore(l3));
    }

    private void removeAllBookStats(VBox cartPage){

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

