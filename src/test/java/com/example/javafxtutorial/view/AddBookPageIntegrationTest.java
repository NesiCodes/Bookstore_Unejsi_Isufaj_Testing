package com.example.javafxtutorial.view;

import com.example.javafxtutorial.model.Book;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class AddBookPageIntegrationTest {

    private VBox addBookPage;
    private ArrayList<Book> books;

    @Start
    public void start(Stage stage) {
        books = new ArrayList<>();

        AddBookPageView controller = new AddBookPageView();
        addBookPage = controller.createAddBookPage(books);

        stage.setScene(new javafx.scene.Scene(addBookPage, 800, 600));
        stage.show();
    }

    @Test
    public void testAddBook(FxRobot robot) throws InterruptedException {
        verifyThat("#titleText", Node::isVisible);
        verifyThat("#bookNameField", Node::isVisible);


        File imageFile = new File("src/test/resources/fire-blood-2.jpg");
        Image testImage = new Image(imageFile.toURI().toString());

        ImageView bookImageView = robot.lookup("#bookImageView").query();
        bookImageView.setImage(testImage);
        Thread.sleep(1000);

        robot.clickOn("#bookNameField").write("Test Book");
        robot.clickOn("#categoryField").write("Fiction");
        robot.clickOn("#authorNameField").write("Test Author");
        robot.clickOn("#bookPriceField").write("19.99");
        robot.clickOn("#bookQuantityField").write("10");

        Thread.sleep(1000);


        robot.clickOn("#addBookButtonInForm");

        Thread.sleep(2000);

        assertEquals(1, books.size(), "Book should be added to the list");

        Book addedBook = books.get(0);
        assertEquals("Test Book", addedBook.getName(), "Book name should match");
        assertEquals("Fiction", addedBook.getCategory(), "Category should match");
        assertEquals("Test Author", addedBook.getAuthor(), "Author should match");
        assertEquals(19.99, addedBook.getPrice(), 0.01, "Price should match");
        assertEquals(10, addedBook.getQuantity(), "Quantity should match");
    }

    @Test
    public void testIncompleteFields(FxRobot robot) {
        robot.clickOn("#addBookButtonInForm");

        assertEquals(0, books.size(), "No book should be added if fields are incomplete");
    }

    @Test
    public void testDuplicateBook(FxRobot robot) {
        books.add(new Book("placeholder.png", "Test Book", "Fiction", "Botimet Pegi", "Test Author", 10, 19.99));

        robot.clickOn("#bookNameField").write("Test Book");
        robot.clickOn("#categoryField").write("Fiction");
        robot.clickOn("#authorNameField").write("Test Author");
        robot.clickOn("#bookPriceField").write("19.99");
        robot.clickOn("#bookQuantityField").write("10");

        robot.clickOn("#addBookButtonInForm");

        assertEquals(1, books.size(), "Duplicate book should not be added");
    }
}