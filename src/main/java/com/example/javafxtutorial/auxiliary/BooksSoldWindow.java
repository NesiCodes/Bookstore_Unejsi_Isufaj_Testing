package com.example.javafxtutorial.auxiliary;

import com.example.javafxtutorial.model.CartItem;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BooksSoldWindow extends Application {

    private Label totalCartQuantityLabel;

    private Label totalCartPriceLabel;

    private ArrayList<CartItem> cartItems;


    public BooksSoldWindow(int quantity, double totalPrice, ArrayList<CartItem> cartItems){
        totalCartQuantityLabel = new Label("Total Quantity: " + quantity);
        totalCartPriceLabel = new Label("Total Price: " + totalPrice + "$");
        this.cartItems = cartItems;
    }

    public void showAndWait(Stage printStage) {
        start(printStage);
        printStage.showAndWait();
    }


    @Override
    public void start(Stage primaryStage) {
        VBox cartPage = new VBox();
        cartPage.setSpacing(0);
        cartPage.setAlignment(Pos.CENTER);
        cartPage.setPadding(new Insets(20));

        cartPage.getChildren().add(createCartHeader());

        for(CartItem c: cartItems){
            cartPage.getChildren().add(createCartItem(c, cartPage));
        }

        cartPage.getChildren().add(createCartEnd(cartPage));

        // Create a ScrollPane to enable scrolling if there are many cart items
        ScrollPane scrollPane = new ScrollPane(cartPage);
        scrollPane.setFitToWidth(true);
        primaryStage.setScene(new Scene(scrollPane, 852, 612));

    }


    HBox createCartItem(CartItem singleCartItem, VBox cartPage) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        // Book image
        String imagePath = "src/main/resources/com/example/javafxtutorial/" + singleCartItem.getBook().getImagePath();

        // Create an Image object
        Image image = new Image(new File(imagePath).toURI().toString());

        // Create the ImageView
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(120);

        // Book details
        VBox detailsBox = new VBox();
        detailsBox.setSpacing(5);

        Label nameLabel = new Label("Name: " + singleCartItem.getBook().getName());
        nameLabel.setFont(Font.font("Berlin Sans FB", 14));

        Label authorLabel = new Label("Author: " + singleCartItem.getBook().getAuthor());
        authorLabel.setFont(Font.font("Berlin Sans FB", 14));

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Label priceLabel = new Label("Price: " + decimalFormat.format(singleCartItem.getBook().getPrice()) + "$");
        priceLabel.setFont(Font.font("Berlin Sans FB", 14));

        detailsBox.getChildren().addAll(nameLabel, authorLabel, priceLabel);
        detailsBox.setAlignment(Pos.CENTER);

        VBox actionsBox = new VBox();
        actionsBox.setSpacing(5);

        Label quantityLabel = new Label("Quantity: ");
        quantityLabel.setFont(Font.font("Berlin Sans FB", 14));

        HBox quantity = new HBox();
        quantity.setSpacing(7);



        Label quantityAmount = new Label(Integer.toString(singleCartItem.getQuantity()));
        quantityAmount.setFont(Font.font("Berlin Sans FB", 14));


        quantity.getChildren().addAll(quantityAmount);

        Label subtotalText = new Label("Subtotal: $" + decimalFormat.format(singleCartItem.getSubtotal()));
        subtotalText.setFont(Font.font("Berlin Sans FB", 13));



        actionsBox.getChildren().addAll(quantity, subtotalText);
        actionsBox.setAlignment(Pos.CENTER);

        cartItem.getChildren().addAll(imageView, createRegion(), detailsBox, createRegion(), actionsBox);


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

    HBox createCartHeader(){
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        Label productImage = new Label("Product Image");
        productImage.setFont(Font.font("Berlin Sans FB", 16));
        productImage.setTextFill(Color.WHITE);

        Label productDetails = new Label("Product Details");
        productDetails.setFont(Font.font("Berlin Sans FB", 16));
        productDetails.setTextFill(Color.WHITE);

        Label actions = new Label("Subtotal");
        actions.setFont(Font.font("Berlin Sans FB", 16));
        actions.setTextFill(Color.WHITE);

        cartItem.getChildren().addAll(productImage, createRegion(), productDetails, createRegion(), actions);

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


    HBox createCartEnd(VBox cartPage) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        VBox cartTotal = new VBox();
        cartTotal.setSpacing(10);

        totalCartQuantityLabel.setFont(Font.font("Berlin Sans FB", 16));

        totalCartPriceLabel.setFont(Font.font("Berlin Sans FB", 16));

        cartTotal.getChildren().addAll(totalCartQuantityLabel, totalCartPriceLabel);

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


    private Region createRegion() {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

    public Label getTotalCartQuantityLabel() {
        return totalCartQuantityLabel;
    }

    public void setTotalCartQuantityLabel(Label totalCartQuantityLabel) {
        this.totalCartQuantityLabel = totalCartQuantityLabel;
    }

    public Label getTotalCartPriceLabel() {
        return totalCartPriceLabel;
    }

    public void setTotalCartPriceLabel(Label totalCartPriceLabel) {
        this.totalCartPriceLabel = totalCartPriceLabel;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}

