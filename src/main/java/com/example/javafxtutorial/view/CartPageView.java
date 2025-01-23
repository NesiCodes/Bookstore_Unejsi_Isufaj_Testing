package com.example.javafxtutorial.view;

import com.example.javafxtutorial.auxiliary.PrintWindow;
import com.example.javafxtutorial.controller.BookController;
import com.example.javafxtutorial.controller.CartController;
import com.example.javafxtutorial.controller.OrderController;
import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Order;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartPageView {

    private CartController cartController;

    private OrderController orderController;

    private BookController bookController;

    private Employee currentLoggedEmployee;

    private Label totalCartQuantityLabel;

    private Label totalCartPriceLabel;

    private Stage primaryStage;

    public CartPageView(CartController cartController, OrderController orderController,  BookController bookController,Employee employee, Stage primaryStage){
        this.bookController = bookController;
        this.cartController = cartController;
        this.orderController = orderController;
        this.currentLoggedEmployee=employee;
        totalCartQuantityLabel = new Label("Total Quantity: " + cartController.getTotalQuantity());
        totalCartPriceLabel = new Label("Total Price: " + cartController.getTotalPrice() + "$");
        this.primaryStage = primaryStage;
    }

    public ScrollPane createCartPage() {
        VBox cartPage = new VBox();
        cartPage.setSpacing(0);
        cartPage.setAlignment(Pos.CENTER);
        cartPage.setPadding(new Insets(20));

        cartPage.getChildren().add(createCartHeader());

        for(CartItem c: cartController.getCartItems()){
            cartPage.getChildren().add(createCartItem(c, cartPage));
        }

        cartPage.getChildren().add(createCartEnd(cartPage));

        // Create a ScrollPane to enable scrolling if there are many cart items
        ScrollPane scrollPane = new ScrollPane(cartPage);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }


    HBox createCartItem(CartItem singleCartItem, VBox cartPage) {
        HBox cartItem = new HBox();
        cartItem.setSpacing(10);
        cartItem.setAlignment(Pos.CENTER_LEFT); // Align the cart item content to the left

        // Book image
        String imagePath = "src/main/resources/com/example/javafxtutorial/" + singleCartItem.getBook().getImagePath();

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

        Button substractButton = new Button("-");
        substractButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        substractButton.setTextFill(Color.WHITE);
        substractButton.setFont(Font.font("Berlin Sans FB"));

        Label quantityAmount = new Label(Integer.toString(singleCartItem.getQuantity()));
        quantityAmount.setFont(Font.font("Berlin Sans FB", 14));
        Button addButton = new Button("+");
        addButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        addButton.setTextFill(Color.WHITE);
        addButton.setFont(Font.font("Berlin Sans FB"));

        quantity.getChildren().addAll(substractButton, quantityAmount, addButton);
        Button removeButton = new Button("Remove");
        removeButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        removeButton.setTextFill(Color.WHITE);
        removeButton.setFont(Font.font("Berlin Sans FB"));
        removeButton.setOnAction(event -> {
            cartPage.getChildren().remove(cartItem);
            cartController.removeCartItem(singleCartItem);
            System.out.println("Book removed from cart: " + singleCartItem.getBook().getName());
            totalCartQuantityLabel.setText("Total Quantity: " + cartController.getTotalQuantity());
            totalCartPriceLabel.setText("Total Price: " + cartController.getTotalPrice() + "$");
        });

        // Subtotal
        Label subtotalText = new Label("Subtotal: $" + decimalFormat.format(singleCartItem.getSubtotal()));
        subtotalText.setFont(Font.font("Berlin Sans FB", 13));

        substractButton.setOnAction(actionEvent -> {
            if(singleCartItem.getQuantity() == 1){
                cartPage.getChildren().remove(cartItem);
                cartController.removeCartItem(singleCartItem);
                totalCartQuantityLabel.setText("Total Quantity: " + cartController.getTotalQuantity());
                totalCartPriceLabel.setText("Total Price: " + cartController.getTotalPrice() + "$");
            }else{
                singleCartItem.setQuantity(singleCartItem.getQuantity() - 1);
                quantityAmount.setText(String.valueOf(singleCartItem.getQuantity()));
                singleCartItem.setSubtotal(singleCartItem.getQuantity() * singleCartItem.getBook().getPrice());
                subtotalText.setText("Subtotal: " + singleCartItem.getSubtotal() + "$");
                cartController.calculateCartTotalPrice();
                cartController.calculateCartTotalQuantity();
                totalCartQuantityLabel.setText("Total Quantity: " + cartController.getTotalQuantity());
                totalCartPriceLabel.setText("Total Price: " + cartController.getTotalPrice() + "$");
            }
        });

        addButton.setOnAction(actionEvent -> {
            if(singleCartItem.getBook().getQuantity() > singleCartItem.getQuantity()){
            singleCartItem.setQuantity(singleCartItem.getQuantity() + 1);
            quantityAmount.setText(String.valueOf(singleCartItem.getQuantity()));
            singleCartItem.setSubtotal(singleCartItem.getQuantity() * singleCartItem.getBook().getPrice());
            subtotalText.setText("Subtotal: " + singleCartItem.getSubtotal() + "$");
            cartController.calculateCartTotalPrice();
            cartController.calculateCartTotalQuantity();
            totalCartQuantityLabel.setText("Total Quantity: " + cartController.getTotalQuantity());
            totalCartPriceLabel.setText("Total Price: " + cartController.getTotalPrice() + "$");}
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No More Books");
                alert.setHeaderText(null);
                alert.setContentText("You cannot buy more than "+ singleCartItem.getBook().getQuantity()+" for " + singleCartItem.getBook().getName() );

                alert.show();
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> alert.close());
                pause.play();;
            }
        });

        actionsBox.getChildren().addAll(quantity, removeButton, subtotalText);
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

        Label actions = new Label("Actions");
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

        Button checkoutButton = new Button("Checkout");
        checkoutButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        checkoutButton.setTextFill(Color.WHITE);
        checkoutButton.setFont(Font.font("Berlin Sans FB", 16));
        checkoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (cartController.getTotalQuantity() == 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cant checkout");
                    alert.setHeaderText(null);
                    alert.setContentText("Cart is empty");

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }else{
                    Order order = new Order(cartController.getCartItems(), cartController.getTotalQuantity(), cartController.getTotalPrice(), currentLoggedEmployee);

                    for (CartItem c : cartController.getCartItems()){

                        for (int i=0; i<bookController.getBooks().size(); i++){

                            if(c.getBook().getName().equals(bookController.getBooks().get(i).getName())){
                                bookController.getBooks().get(i).setQuantity(bookController.getBooks().get(i).getQuantity() - c.getQuantity());
                                System.out.println("Quantity set to " + bookController.getBooks().get(i).getQuantity() + " for book " + bookController.getBooks().get(i).getName());
                                break;
                            }

                        }

                    }

                    orderController.addOrder(order);
                    PrintWindow printWindow = new PrintWindow(order);
                    Stage printStage = new Stage();

                    // Set the owner of the new stage to the primary stage
                    printStage.initOwner(primaryStage);

                    // Set modality to WINDOW_MODAL to make it a modal window
                    printStage.initModality(Modality.WINDOW_MODAL);

                    // Show the secondary stage
                    printWindow.showAndWait(printStage);

                    cartPage.getChildren().clear();
                    cartController.setCartItems(new ArrayList<>());
                    cartController.setTotalPrice(0);
                    cartController.setTotalQuantity(0);
                    totalCartQuantityLabel.setText("Total Quantity: " + cartController.getTotalQuantity());
                    totalCartPriceLabel.setText("Total Price: " + cartController.getTotalPrice() + "$");
                    cartPage.getChildren().addAll(createCartHeader(), createCartEnd(cartPage));
                }

            }
        });
        cartTotal.getChildren().addAll(totalCartQuantityLabel, totalCartPriceLabel, checkoutButton);

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

}
