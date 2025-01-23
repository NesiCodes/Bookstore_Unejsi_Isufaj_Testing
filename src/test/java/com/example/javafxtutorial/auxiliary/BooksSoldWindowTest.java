//package com.example.javafxtutorial.auxiliary;
//
//import com.example.javafxtutorial.model.CartItem;
//import com.example.javafxtutorial.model.Book;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BooksSoldWindowTest {
//
//    @BeforeAll
//    static void initJavaFX() {
//        // Initialize JavaFX runtime for testing
//        new Thread(() -> javafx.application.Application.launch(TestApp.class)).start();
//    }
//
//    @Test
//    void testConstructor() {
//        // Arrange
//        int quantity = 5;
//        double totalPrice = 100.0;
//        ArrayList<CartItem> cartItems = new ArrayList<>();
//
//        // Act
//        BooksSoldWindow booksSoldWindow = new BooksSoldWindow(quantity, totalPrice, cartItems);
//
//        // Assert
//        assertNotNull(booksSoldWindow);
//        assertEquals("Total Quantity: 5", booksSoldWindow.getTotalCartQuantityLabel().getText());
//        assertEquals("Total Price: 100.0$", booksSoldWindow.getTotalCartPriceLabel().getText());
//    }
//
//    @Test
//    void testCreateCartItem() {
//        // Arrange
//        CartItem mockCartItem = Mockito.mock(CartItem.class);
//        Book mockBook = Mockito.mock(Book.class);
//        when(mockCartItem.getBook()).thenReturn(mockBook);
//        when(mockBook.getName()).thenReturn("Test Book");
//        when(mockBook.getAuthor()).thenReturn("Test Author");
//        when(mockBook.getPrice()).thenReturn(20.0);
//        when(mockBook.getImagePath()).thenReturn("test.png");
//        when(mockCartItem.getQuantity()).thenReturn(2);
//        when(mockCartItem.getSubtotal()).thenReturn(40.0);
//
//        VBox mockCartPage = Mockito.mock(VBox.class);
//        BooksSoldWindow booksSoldWindow = new BooksSoldWindow(0, 0.0, new ArrayList<>());
//
//        // Act
//        HBox cartItem = booksSoldWindow.createCartItem(mockCartItem, mockCartPage);
//
//        // Assert
//        assertNotNull(cartItem);
//        assertEquals(5, cartItem.getChildren().size()); // ImageView, Region, Details, Region, Actions
//    }
//
//    @Test
//    void testCreateCartHeader() {
//        // Arrange
//        BooksSoldWindow booksSoldWindow = new BooksSoldWindow(0, 0.0, new ArrayList<>());
//
//        // Act
//        HBox cartHeader = booksSoldWindow.createCartHeader();
//
//        // Assert
//        assertNotNull(cartHeader);
//        assertEquals(5, cartHeader.getChildren().size()); // Labels and Regions
//    }
//
//    @Test
//    void testCreateCartEnd() {
//        // Arrange
//        BooksSoldWindow booksSoldWindow = new BooksSoldWindow(5, 100.0, new ArrayList<>());
//        VBox mockCartPage = Mockito.mock(VBox.class);
//
//        // Act
//        HBox cartEnd = booksSoldWindow.createCartEnd(mockCartPage);
//
//        // Assert
//        assertNotNull(cartEnd);
//        assertEquals(3, cartEnd.getChildren().size()); // Regions and Total Labels
//    }
//
//    @Test
//    void testShowAndWait() {
//        // Arrange
//        Stage mockStage = Mockito.mock(Stage.class);
//        BooksSoldWindow booksSoldWindow = new BooksSoldWindow(0, 0.0, new ArrayList<>());
//
//        // Act
//        booksSoldWindow.showAndWait(mockStage);
//
//        // Assert
//        verify(mockStage, times(1)).showAndWait();
//    }
//
//    // Helper methods to expose private fields for testing
//    private static class TestApp extends javafx.application.Application {
//        @Override
//        public void start(Stage primaryStage) {
//            // No-op
//        }
//    }
//}