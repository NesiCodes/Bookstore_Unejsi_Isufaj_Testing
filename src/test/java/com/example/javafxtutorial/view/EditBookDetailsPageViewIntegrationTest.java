package com.example.javafxtutorial.view;

import com.example.javafxtutorial.auxiliary.InvalidQuantityException;
import com.example.javafxtutorial.model.Book;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class EditBookDetailsPageViewIntegrationTest {

    private ScrollPane editBookPage;
    private ArrayList<Book> books;
    private EditBookDetailsPageView editBookDetailsPageView;

    @Start
    public void start(Stage stage) {
        books = new ArrayList<>();
        books.add(new Book("placeholder.png", "Book 1", "Fiction", "Publisher 1", "Author 1", 10, 19.99));
        books.add(new Book("placeholder.png", "Book 2", "Non-Fiction", "Publisher 2", "Author 2", 5, 29.99));

        editBookDetailsPageView = new EditBookDetailsPageView();
        editBookPage = editBookDetailsPageView.createEditBookPage(books, true);

        stage.setScene(new javafx.scene.Scene(editBookPage, 800, 600));
        stage.show();
    }

    @BeforeEach
    public void resetBooks() {
        books.get(0).setName("Book 1");
        books.get(0).setAuthor("Author 1");
        books.get(0).setQuantity(10);
        books.get(0).setPrice(19.99);

        books.get(1).setName("Book 2");
        books.get(1).setAuthor("Author 2");
        books.get(1).setQuantity(5);
        books.get(1).setPrice(29.99);
    }

//    @Test
//    public void testInitialDisplay(FxRobot robot) throws InterruptedException {
//        sleep(1000);
//        // Verify that the edit book page is loaded correctly
//        verifyThat("#bookEntry_0", Node::isVisible); // Check for the first book entry
//
//        // Verify the initial values of the first book
//        verifyThat("#bookNameField_0", hasText(""));
//        verifyThat("#bookAuthorField_0", hasText(""));
//        verifyThat("#bookPriceField_0", hasText(""));
//        verifyThat("#bookQuantityField_0", hasText(""));
//
//        // Verify the initial values of the second book
//        verifyThat("#bookEntry_1", Node::isVisible); // Check for the second book entry
//        verifyThat("#bookNameField_1", hasText(""));
//        verifyThat("#bookAuthorField_1", hasText(""));
//        verifyThat("#bookPriceField_1", hasText(""));
//        verifyThat("#bookQuantityField_1", hasText(""));
//    }

    @Test
    public void testUpdateBookDetails(FxRobot robot) {
        robot.clickOn("#bookNameField_0").write("Updated Book 1");
        robot.clickOn("#bookAuthorField_0").write("Updated Author 1");
        robot.clickOn("#bookPriceField_0").write("25.99");
        robot.clickOn("#bookQuantityField_0").write("15");

        robot.clickOn("#saveButton_0");

        assertEquals("Updated Book 1", books.get(0).getName(), "Book name should be updated");
        assertEquals("Updated Author 1", books.get(0).getAuthor(), "Author should be updated");
        assertEquals(25.99, books.get(0).getPrice(), 0.01, "Price should be updated");
        assertEquals(15, books.get(0).getQuantity(), "Quantity should be updated");

        verifyThat(".alert", isVisible());
    }

    @Test
    public void testInvalidQuantity(FxRobot robot) {
        robot.clickOn("#bookQuantityField_0").write("-5");

        robot.clickOn("#saveButton_0");

        assertEquals(10, books.get(0).getQuantity(), "Quantity should not be updated for invalid input");

        verifyThat(".alert", isVisible());
    }

    @Test
    public void testInvalidPrice(FxRobot robot) {
        robot.clickOn("#bookPriceField_0").write("-10.99");

        robot.clickOn("#saveButton_0");

        assertEquals(19.99, books.get(0).getPrice(), 0.01, "Price should not be updated for invalid input");

        verifyThat(".alert", isVisible());
    }

    @Test
    public void testPartialUpdate(FxRobot robot) {
        robot.clickOn("#bookAuthorField_0").write("New Author");

        robot.clickOn("#saveButton_0");

        assertEquals("Book 1", books.get(0).getName(), "Book name should remain unchanged");
        assertEquals("New Author", books.get(0).getAuthor(), "Author should be updated");
        assertEquals(19.99, books.get(0).getPrice(), 0.01, "Price should remain unchanged");
        assertEquals(10, books.get(0).getQuantity(), "Quantity should remain unchanged");

        verifyThat(".alert", isVisible());
    }
}