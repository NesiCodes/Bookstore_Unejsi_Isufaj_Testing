package com.example.javafxtutorial.view;

import com.example.javafxtutorial.auxiliary.InvalidQuantityException;
import com.example.javafxtutorial.model.Book;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class EditBookDetailsPageView {

    public ScrollPane createEditBookPage(ArrayList<Book> books, boolean isIntegrationTest) {
        VBox cartPage = new VBox();
        cartPage.setId("edit_book_view");
        cartPage.setSpacing(10);
        cartPage.setAlignment(Pos.CENTER);
        cartPage.setPadding(new Insets(20));

        cartPage.getChildren().add(createEditBookHeader());

        for(Book b: books){
            cartPage.getChildren().add(createBookItem(b, books, isIntegrationTest));
        }

        // Create a ScrollPane to enable scrolling if there are many cart items
        ScrollPane scrollPane = new ScrollPane(cartPage);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    private HBox createEditBookHeader(){
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        Label productImage = new Label("Book Image");
        productImage.setFont(Font.font("Berlin Sans FB", 16));
        productImage.setTextFill(Color.WHITE);

        Label productDetails = new Label("Book Name");
        productDetails.setFont(Font.font("Berlin Sans FB", 16));
        productDetails.setTextFill(Color.WHITE);

        Label bookAuthor = new Label("Book Author");
        bookAuthor.setFont(Font.font("Berlin Sans FB", 16));
        bookAuthor.setTextFill(Color.WHITE);

        Label bookPrice = new Label("Book Price");
        bookPrice.setFont(Font.font("Berlin Sans FB", 16));
        bookPrice.setTextFill(Color.WHITE);

        Label bookQuantity = new Label("Book Quantity");
        bookQuantity.setFont(Font.font("Berlin Sans FB", 16));
        bookQuantity.setTextFill(Color.WHITE);

        Label actions = new Label("Actions");
        actions.setFont(Font.font("Berlin Sans FB", 16));
        actions.setTextFill(Color.WHITE);

        cartItem.getChildren().addAll(productImage, createRegion(), productDetails, createRegion(), bookAuthor, createRegion(), bookPrice, createRegion(), bookQuantity, createRegion(), actions);

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

    private HBox createBookItem(Book book, ArrayList<Book> books, boolean isIntegrationTest) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        // Use the ClassLoader to load the image
        String imagePath = "src/main/resources/com/example/javafxtutorial/" + book.getImagePath();

        Image image = new Image(new File(imagePath).toURI().toString());

        // Create the ImageView
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(120);


        VBox bookNameBox = new VBox();
        bookNameBox.setSpacing(5);

        Label bookNameLabel = new Label("Name: ");
        bookNameLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField bookNameField = new TextField();
        bookNameField.setFont(Font.font("Berlin Sans FB", 14));
        bookNameField.setPromptText(book.getName());
        bookNameField.setId("bookNameField");

        bookNameBox.getChildren().addAll(bookNameLabel, bookNameField);
        bookNameBox.setAlignment(Pos.CENTER);

        VBox bookAuthorBox = new VBox();
        bookAuthorBox.setSpacing(5);

        Label bookAuthorLabel = new Label("Author: ");
        bookAuthorLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField bookAuthorField = new TextField();
        bookAuthorField.setId("bookAuthorField");
        bookAuthorField.setFont(Font.font("Berlin Sans FB", 14));
        bookAuthorField.setPromptText(book.getAuthor());

        bookAuthorBox.getChildren().addAll(bookAuthorLabel, bookAuthorField);
        bookAuthorBox.setAlignment(Pos.CENTER);

        VBox bookPriceBox = new VBox();
        bookPriceBox.setSpacing(5);

        Label bookPriceLabel = new Label("Price: ");
        bookPriceLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField bookPriceField = new TextField();
        bookPriceField.setId("bookPriceField");
        bookPriceField.setFont(Font.font("Berlin Sans FB", 14));
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        bookPriceField.setPromptText(Double.toString(Double.parseDouble(decimalFormat.format(book.getPrice()))));

        bookPriceBox.getChildren().addAll(bookPriceLabel, bookPriceField);
        bookPriceBox.setAlignment(Pos.CENTER);

        VBox bookQuantityBox = new VBox();
        bookQuantityBox.setSpacing(5);

        Label bookQuantityLabel = new Label("Quantity: ");
        bookQuantityLabel.setFont(Font.font("Berlin Sans FB", 14));
        TextField bookQuantityField = new TextField();
        bookQuantityField.setId("bookQuantityField");
        bookQuantityField.setFont(Font.font("Berlin Sans FB", 14));
        bookQuantityField.setPromptText(Integer.toString(book.getQuantity()));

        bookQuantityBox.getChildren().addAll(bookQuantityLabel, bookQuantityField);
        bookQuantityBox.setAlignment(Pos.CENTER);

        VBox actionsBox = new VBox();
        actionsBox.setSpacing(5);

        Button saveButton = new Button("Save Changes");
        saveButton.setId("saveChanges");
        saveButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        saveButton.setTextFill(Color.WHITE);
        saveButton.setFont(Font.font("Berlin Sans FB", 14));

        if (isIntegrationTest){
            bookNameField.setId("bookNameField_" + books.indexOf(book));

            bookAuthorField.setId("bookAuthorField_" + books.indexOf(book));

            bookPriceField.setId("bookPriceField_" + books.indexOf(book));

            bookQuantityField.setId("bookQuantityField_" + books.indexOf(book));

            saveButton.setId("saveButton_" + books.indexOf(book));
        }


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    if (!bookNameField.getText().isEmpty()) {
                        book.setName(bookNameField.getText());
                    }

                    if (!bookAuthorField.getText().isEmpty()) {
                        book.setAuthor(bookAuthorField.getText());
                    }

                    if (!bookQuantityField.getText().isEmpty()) {
                        int quantity = parseQuantity(bookQuantityField.getText());

                        if (quantity >= 0){
                            if (quantity > book.getQuantity()) {
                                System.out.println("Added purchase info");
                                book.addPurchaseInfo(quantity - book.getQuantity());
                            }
                            book.setQuantity(quantity);
                        }else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Invalid");
                            alert.setHeaderText(null);
                            alert.setContentText("Quantity was not modified because it cant be negative!");

                            alert.show();
                        }
                    }

                    if (!bookPriceField.getText().isEmpty()) {
                        try {
                            double price = Double.parseDouble(bookPriceField.getText());
                            if (price >= 0){
                                book.setPrice(price);
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Invalid");
                                alert.setHeaderText(null);
                                alert.setContentText("Price was not modified because it cant be negative!");

                                alert.show();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid price format");
                        }
                    }

                    // Display a success alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.getDialogPane().setId("SuccessAlertEdit");
                    alert.setHeaderText(null);
                    alert.setContentText("Book successfully modified.");
                    alert.show();
//                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
//                    pause.setOnFinished(event -> alert.close());
//                    pause.play();

                } catch (InvalidQuantityException e) {
                    // Handle invalid quantity exception (if needed)
                    System.out.println(e.getMessage());
                }
            }

        });


        actionsBox.getChildren().addAll(saveButton);
        actionsBox.setAlignment(Pos.CENTER);

        cartItem.getChildren().addAll(imageView, createRegion(), bookNameBox, createRegion(),bookAuthorBox,createRegion(),bookPriceBox,createRegion(),bookQuantityBox, createRegion(), actionsBox);


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

    public int parseQuantity(String quantityStr) throws InvalidQuantityException {
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            throw new InvalidQuantityException("Quantity cannot be null or empty");
        }

        try {
            // Trim whitespace before parsing
            int quantity = Integer.parseInt(quantityStr.trim());
            if (quantity < 0) {
                throw new InvalidQuantityException("Quantity cannot be negative");
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new InvalidQuantityException("Invalid quantity: " + quantityStr);
        }
    }

    private Region createRegion() {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

}

