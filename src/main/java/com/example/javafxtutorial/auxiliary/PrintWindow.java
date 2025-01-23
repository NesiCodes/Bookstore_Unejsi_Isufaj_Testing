package com.example.javafxtutorial.auxiliary;

import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Order;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PrintWindow extends Application{

    private Order order;

    public PrintWindow(Order order) {
        this.order = order;
    }

    public void showAndWait(Stage printStage) {
        start(printStage);
        printStage.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Print Order");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);

        Label label1 = new Label("Thank you for buying from our store!");
        label1.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        Label label2 = new Label("Order id: " + order.getOrderId());
        label2.setStyle("-fx-font-size: 16;");

        Label label3 = new Label("Date: " + order.getDateCreated());
        label3.setStyle("-fx-font-size: 16;");

        String roleName;
        if(order.getEmployee().getAccessLevel() == 1){
            roleName = "Librarian";
        }else if(order.getEmployee().getAccessLevel() == 2){
            roleName = "Manager";
        }else{
            roleName = "Administrator";
        }
        Label sellerInfo = new Label("Seller: " + order.getEmployee().getName() + " Role: " + roleName);
        sellerInfo.setStyle("-fx-font-size: 16;");

        Label label4 = new Label("Order Details: ");
        label4.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        vBox1.getChildren().addAll(label2, label3, sellerInfo, label4);

        vBox.getChildren().addAll(label1, vBox1);

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);

        Label header1 = new Label("+------------------+-------------------+--------------+----------+-----------+");
        Label header2 = new Label("|   Book Name      |    Author         |    Price     | Quantity | Subtotal  |");
        Label header3 = new Label("+------------------+-------------------+--------------+----------+-----------+");
        header1.setStyle("-fx-font-size: 14;");
        header2.setStyle("-fx-font-size: 14;");
        header3.setStyle("-fx-font-size: 14;");
        vBox2.getChildren().addAll(header1, header2, header3);

        for (CartItem c : order.getCartItems()) {
            Label l = new Label(String.format("| %-16s | %-17s | $%-11.2f | %-8d | $%-8.2f |", c.getBook().getName(), c.getBook().getAuthor(), c.getBook().getPrice(), c.getQuantity(), c.getSubtotal()));
            l.setStyle("-fx-font-size: 14;");
            vBox2.getChildren().add(l);
        }

        Label line = new Label("+------------------+-------------------+--------------+----------+-----------+");
        line.setStyle("-fx-font-size: 14;");
        vBox2.getChildren().add(line);

        Label totals = new Label("Total Quantity: " + order.getTotalQuantity() + " | Total Price: " + order.getTotalPrice());
        totals.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        vBox.getChildren().addAll(vBox2, totals);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(15);
        Button b1 = new Button("Ok");
        b1.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        b1.setOnAction(actionEvent -> {
            primaryStage.close();
        });

        Button b2 = new Button("Print");
        b2.setStyle("-fx-font-size: 14; -fx-background-color: #008CBA; -fx-text-fill: white;");


        buttons.getChildren().addAll(b1, b2);

        vBox.getChildren().addAll(buttons);

        b2.setOnAction(e -> {
            String resourcePath = "src/main/resources/com/example/javafxtutorial/bills";
            String fileName = order.getOrderId() + ".txt";

            // Create a new file in the resources package
            try {
                // Get the path to the resource as a string
                Path resourcePathString = Paths.get(resourcePath, fileName);
                // Create the file if it doesn't exist
                Files.createFile(resourcePathString);

                // Write information to the file
                writeInformationToFile(resourcePathString.toString(), vBox);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        primaryStage.setScene(new Scene(vBox, 700, 600));
    }

    void writeInformationToFile(String filePath, VBox vBox) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Gather information from vBox and write it to the file (ca kemi te window vendoset dhe te file)
            for (Node node : vBox.getChildren()) {
                if (node instanceof Label) {
                    String text = ((Label) node).getText();
                    writer.write(text + System.lineSeparator());
                }else if(node instanceof VBox){
                    for (Node n: ((VBox) node).getChildren()){
                        if (n instanceof Label) {
                            String text = ((Label) n).getText();
                            writer.write(text + System.lineSeparator());
                        }
                    }
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
