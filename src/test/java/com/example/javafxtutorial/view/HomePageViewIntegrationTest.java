package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.CartController;
import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
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
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
public class HomePageViewIntegrationTest {

    private ScrollPane homePage;
    private CartController cartController;
    private HomePageView homePageView;

    @Start
    public void start(Stage stage) {
        cartController = new CartController();

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("placeholder.png", "Book 1", "Fiction", "Publisher 1", "Author 1", 10, 19.99));
        books.add(new Book("placeholder.png", "Book 2", "Non-Fiction", "Publisher 2", "Author 2", 0, 29.99)); // Out of stock

        homePageView = new HomePageView();
        homePage = homePageView.showView(books, cartController);

        stage.setScene(new javafx.scene.Scene(homePage, 800, 600));
        stage.show();
    }

    @BeforeEach
    public void clearCart(){
        System.out.println("got here");
        cartController.getCartItems().clear();
    }

    @Test
    public void testAddToCart(FxRobot robot) {
        verifyThat("#bookEntry", Node::isVisible);

        assertEquals(0, cartController.getCartItems().size(), "Cart should be empty initially");

        robot.clickOn("#addToCartButton_Book_1");

        assertEquals(1, cartController.getCartItems().size(), "Cart should contain one item");
        CartItem cartItem = cartController.getCartItems().get(0);
        assertEquals("Book 1", cartItem.getBook().getName(), "Book name should match");
        assertEquals(1, cartItem.getQuantity(), "Quantity should be 1");
        assertEquals(19.99, cartItem.getSubtotal(), 0.01, "Price should match");
    }

    @Test
    public void testOutOfStockBook(FxRobot robot) {
        verifyThat("#bookEntry", Node::isVisible);

        robot.clickOn("#addToCartButton_Book_2");

        robot.sleep(1000);

        verifyThat(".alert", Node::isVisible);

        assertEquals(0, cartController.getCartItems().size(), "Cart should remain empty for out-of-stock books");
    }

    @Test
    public void testBookDetails(FxRobot robot) {
        verifyThat("#bookNameLabel", hasText("Name: Book 1"));
        verifyThat("#authorLabel", hasText("Author: Author 1"));
        verifyThat("#priceLabel", hasText("Price: 19.99$"));
        verifyThat("#quantityLabel", hasText("Quantity: 10"));

        ImageView imageView = robot.lookup("#bookImageView").query();
        assertNotNull(imageView, "Book image should be displayed");
    }

    @Test
    public void testUpdateDisplayedBooks(FxRobot robot) {
        ArrayList<Book> filteredBooks = new ArrayList<>();
        filteredBooks.add(new Book("placeholder.png", "Book 3", "Science", "Publisher 3", "Author 3", 5, 15.99));

        robot.interact(() -> {
            homePageView.updateDisplayedBooks(filteredBooks, cartController);
        });

        verifyThat("#bookNameLabel", hasText("Name: Book 3"));
        verifyThat("#authorLabel", hasText("Author: Author 3"));
        verifyThat("#priceLabel", hasText("Price: 15.99$"));
        verifyThat("#quantityLabel", hasText("Quantity: 5"));
    }
}