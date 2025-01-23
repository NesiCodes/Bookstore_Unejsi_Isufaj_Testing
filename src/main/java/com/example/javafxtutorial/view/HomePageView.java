package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.CartController;
import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomePageView {

    private FlowPane bookList;

    public ScrollPane showView(ArrayList<Book> books, CartController cartController){
        // Create a sample book list using FlowPane
        bookList = new FlowPane();
        bookList.setId("bookList");
        bookList.setHgap(15);
        bookList.setVgap(15);

        for(Book b: books){
            bookList.getChildren().add(createBookEntry(b, cartController));
        }

        bookList.setAlignment(Pos.CENTER);
        bookList.setPadding(new Insets(15));

        // Wrap the FlowPane in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(bookList);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }


    public void updateDisplayedBooks(List<Book> filteredBooks, CartController cartController){
        bookList.getChildren().clear();
        for(Book book : filteredBooks){
            bookList.getChildren().add(createBookEntry(book, cartController));
        }
    }

    private VBox createBookEntry(Book book, CartController cartController) {
        VBox entry = new VBox();
        entry.setId("bookEntry");
        entry.setSpacing(5);

        // Use the ClassLoader to load the image
        String imagePath = "src/main/resources/com/example/javafxtutorial/" + book.getImagePath();

        // Create an Image object
        Image image = new Image(new File(imagePath).toURI().toString());

        ImageView imageView = new ImageView(image);
        imageView.setId("bookImageView"); // Unique ID for the image
        if(book.getQuantity()==0){
            imageView.setOpacity(0.5);
        }
        imageView.setFitWidth(120);
        imageView.setFitHeight(180);

        // Book information
        String bookName = book.getName();
        String authorName = book.getAuthor();

        Label nameLabel = new Label("Name: " + bookName);
        nameLabel.setId("bookNameLabel"); // Unique ID for the name label
        nameLabel.setFont(Font.font("Berlin Sans FB", 16));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(120);

        Label authorLabel = new Label("Author: " + authorName);
        authorLabel.setId("authorLabel"); // Unique ID for the author label
        authorLabel.setFont(Font.font("Berlin Sans FB", 15));
        authorLabel.setWrapText(true);
        authorLabel.setMaxWidth(120);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Label priceLabel = new Label("Price: " + decimalFormat.format(book.getPrice()) + "$");
        priceLabel.setFont(Font.font("Berlin Sans FB", 15));
        priceLabel.setId("priceLabel"); // Unique ID for the price label
        Label quantityLabel = new Label("Quantity: " + book.getQuantity());
        quantityLabel.setFont(Font.font("Berlin Sans FB", 15));
        quantityLabel.setId("quantityLabel"); // Unique ID for the quantity label

        // Add to cart button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setId("addToCartButton_" + bookName.replace(" ", "_")); // Unique ID for the button
        addToCartButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        addToCartButton.setTextFill(Color.WHITE);
        addToCartButton.setFont(Font.font("Berlin Sans FB"));
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(book.getQuantity() == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cant add book");
                    alert.setHeaderText(null);
                    alert.setContentText("There are no books in stock");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }else{
                    cartController.addCartItem(new CartItem(book,1,book.getPrice()));
                }
            }
        });

        // Apply a lighter box shadow at the bottom
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.2)); // Adjust alpha for transparency
        entry.setEffect(dropShadow);

        entry.getChildren().addAll(imageView, nameLabel, authorLabel,priceLabel, quantityLabel, addToCartButton);
        entry.setStyle("-fx-background-radius: 10; -fx-background-color: #FFFFFF; -fx-padding: 10; ");
        return entry;
    }

}