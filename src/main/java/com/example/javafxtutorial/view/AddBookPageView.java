
package com.example.javafxtutorial.view;

import com.example.javafxtutorial.model.Book;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class AddBookPageView {

    private String imageFilePath =  "com/example/javafxtutorial/book1.png"; // Default image path
    public VBox createAddBookPage(ArrayList<Book> books) {
        Text titleText = new Text("Add a Book");
        titleText.setFont(new Font("Berlin Sans FB",24));

        // Image section
        ImageView bookImageView = new ImageView();
        bookImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/javafxtutorial/book_placeholder.png")));
        bookImageView.setFitWidth(200);
        bookImageView.setFitHeight(300);
        bookImageView.setId("bookImageView");

        VBox bookHolder = new VBox(bookImageView);
        bookHolder.setSpacing(10);

        Label imageLabel = new Label("Image:");
        imageLabel.setFont(new Font("Berlin Sans FB",16));
        Button addImageButton = new Button("Add Image");
        addImageButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        addImageButton.setTextFill(Color.WHITE);
        addImageButton.setFont(Font.font("Berlin Sans FB", 16));
        addImageButton.setId("addImageButton"); // Set an ID for testing

        addImageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                    bookImageView.setImage(image);
                    imageFilePath = selectedFile.getName();
                    File saveFile = new File("src/main/resources/com/example/javafxtutorial/" + selectedFile.getName());

//                    if (saveFile != null) {
                    try {
                        FileChannel source = new FileInputStream(selectedFile).getChannel();
                        FileChannel destination = new FileOutputStream(saveFile).getChannel();
                        destination.transferFrom(source, 0, source.size());
                        source.close();
                        destination.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
//                    }
                }
            }
        });

        // Details section
        Label bookNameLabel = new Label("Book Name:");
        bookNameLabel.setFont(new Font("Berlin Sans FB",16));
        TextField bookNameField = new TextField();
        bookNameField.setId("bookNameField");
        bookNameField.setFont(Font.font("Berlin Sans FB", 16));
        bookNameField.setPromptText("Name");

        Label categoryLabel = new Label("Category:");
        categoryLabel.setFont(new Font("Berlin Sans FB",16));
        TextField categoryField = new TextField();
        categoryField.setFont(Font.font("Berlin Sans FB", 16));
        categoryField.setPromptText("Category");
        categoryField.setId("categoryField");

        Label authorNameLabel = new Label("Author Name:");
        authorNameLabel.setFont(new Font("Berlin Sans FB",16));
        TextField authorNameField = new TextField();
        authorNameField.setFont(Font.font("Berlin Sans FB", 16));
        authorNameField.setPromptText("Author");
        authorNameField.setId("authorNameField");

        Label bookPriceLabel = new Label("Book Price:");
        bookPriceLabel.setFont(new Font("Berlin Sans FB",16));
        TextField bookPriceField = new TextField();
        bookPriceField.setFont(Font.font("Berlin Sans FB", 16));
        bookPriceField.setPromptText("Price");
        bookPriceField.setId("bookPriceField");

        Label bookQuantityLabel = new Label("Book Quantity:");
        bookQuantityLabel.setFont(new Font("Berlin Sans FB",16));
        TextField bookQuantityField = new TextField();
        bookQuantityField.setFont(Font.font("Berlin Sans FB", 16));
        bookQuantityField.setPromptText("Quantity");
        bookQuantityField.setId("bookQuantityField");

        Button saveButton = new Button("Add Book");
        saveButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        saveButton.setTextFill(Color.WHITE);
        saveButton.setFont(Font.font("Berlin Sans FB", 16));
        saveButton.setId("addBookButtonInForm");
        saveButton.setOnAction(actionEvent -> {

            String bookName = bookNameField.getText();
            String categoryName = categoryField.getText();
            String authorName = authorNameField.getText();
            String quantityText = bookQuantityField.getText();
            String priceText = bookPriceField.getText();

            if (bookName.isEmpty() || authorName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || categoryName.isEmpty()) {
                showAlert("Incomplete Fields", "Please fill in all fields before adding a book.");
                return;
            }

            if (imageFilePath == null || imageFilePath.isEmpty()){
                showAlert("Warning", "Please select an image.");
                return;
            }

            boolean bookFound = false;

            for (Book existingBook : books) {
                if (existingBook.getName().equals(bookName)){
                    showAlert("Duplicate Book", "A book with the same name already exists in the list.");
                    bookFound = true;
                    break;
                }
            }
            if (!bookFound){
                try {
                    int quantity = Integer.parseInt(quantityText);
                    double price = Double.parseDouble(priceText);

                    if (quantity >= 0 && price >= 0){
                        System.out.println("got here");
                        Book book = new Book(imageFilePath,bookName,categoryName, "Botimet Pegi",authorName, quantity, price);
                        book.addPurchaseInfo(quantity);
                        books.add(book);
                        System.out.println("current book in array"+books.size());
                        System.out.println("last book added"+book.getName());

                        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        successAlert.setTitle("Success");
                        successAlert.getDialogPane().setId("SuccessAlert");  // Set ID on the DialogPane instead
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Book added successfully");
                        successAlert.showAndWait();
                    }else{
                        showAlert("Error", "Quantity or price cant be negative");
                    }


                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter valid numbers for quantity and price.");
                }
            }
        });

        bookNameField.setId("bookNameField");

        categoryField.setId("categoryField");

        authorNameField.setId("authorNameField");

        bookPriceField.setId("bookPriceField");

        bookQuantityField.setId("bookQuantityField");

        titleText.setId("titleText");

        GridPane detailsGrid = new GridPane();
        detailsGrid.setVgap(10);
        detailsGrid.setHgap(10);
        detailsGrid.addRow(0, imageLabel, addImageButton);
        detailsGrid.addRow(1, bookNameLabel, bookNameField);
        detailsGrid.addRow(2,categoryLabel, categoryField);
        detailsGrid.addRow(3, authorNameLabel, authorNameField);
        detailsGrid.addRow(4, bookPriceLabel, bookPriceField);
        detailsGrid.addRow(5, bookQuantityLabel, bookQuantityField);
        detailsGrid.add(saveButton, 0, 6);
        detailsGrid.setAlignment(Pos.CENTER);

        // Main layout
        HBox innerLayout = new HBox(20);
        innerLayout.setAlignment(Pos.CENTER);
        innerLayout.getChildren().addAll(bookHolder, detailsGrid);

        VBox mainLayout = new VBox(20);
        mainLayout.setSpacing(60);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20,10,100,10));
        mainLayout.getChildren().addAll(titleText, innerLayout);
        mainLayout.setId("add_book_view");
        return mainLayout;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}











