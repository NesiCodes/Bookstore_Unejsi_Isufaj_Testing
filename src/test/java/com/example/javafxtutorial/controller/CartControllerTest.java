package com.example.javafxtutorial.controller;
import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CartControllerTest {

    @Mock
    private CartItem mockCartItem;

    @Mock
    private Book mockBook;

    private CartController cartController;

    @BeforeEach
    void setUp() {
        cartController = new CartController();
        mockCartItem = mock(CartItem.class);
        mockBook = mock(Book.class);
    }

    @Test
    void testDeleteCartItem() {
        when(mockBook.getImagePath()).thenReturn("book1.jpg");
        when(mockBook.getQuantity()).thenReturn(10);
        when(mockBook.getPrice()).thenReturn(20.0);
        when(mockBook.getName()).thenReturn("Test Book");

        when(mockCartItem.getBook()).thenReturn(mockBook);
        when(mockCartItem.getQuantity()).thenReturn(1);
        when(mockCartItem.getSubtotal()).thenReturn(20.0);

        cartController.setTestMode(true);
        cartController.addCartItem(mockCartItem);

        // Assert: Initial state
        assertEquals(1, cartController.getCartItems().size());

        // Act: Delete the cart item
        cartController.removeCartItem(mockCartItem);

        // Assert: Verify item is removed
        assertEquals(0, cartController.getCartItems().size());
        assertEquals(0.0, cartController.getTotalPrice());
        assertEquals(0, cartController.getTotalQuantity());
    }@Test
    void testAddCartItem_IncrementQuantity() {
        cartController.setTestMode(true);

        // Arrange: Mock book properties
        when(mockBook.getImagePath()).thenReturn("book1.jpg");
        when(mockBook.getQuantity()).thenReturn(5); // Stock limit is 5
        when(mockBook.getPrice()).thenReturn(20.0);

        // Create a real CartItem instead of mocking it
        CartItem realCartItem = new CartItem(mockBook, 1, 20);

        // Act 1: Add the item to the cart initially
        cartController.addCartItem(realCartItem);

        // Act 2: Add the same item again to simulate incrementing quantity
        cartController.addCartItem(realCartItem);

        // Retrieve the updated item directly from the cart
        CartItem updatedItem = cartController.getCartItems().get(0);

        // Assert: Check if the quantity and price updated correctly
        assertEquals(2, updatedItem.getQuantity(), "Quantity should increment by 1.");
        assertEquals(40.0, updatedItem.getSubtotal(), 0.01, "Subtotal should update accordingly.");
        assertEquals(40.0, cartController.getTotalPrice(), 0.01, "Cart total price should update.");
    }


    @Test
    void testDeleteNonExistingCartItem() {
        // Arrange: Mock book and cart item
        when(mockBook.getImagePath()).thenReturn("book1.jpg");
        when(mockBook.getQuantity()).thenReturn(10);
        when(mockBook.getPrice()).thenReturn(20.0);
        when(mockBook.getName()).thenReturn("Test Book");

        when(mockCartItem.getBook()).thenReturn(mockBook);
        when(mockCartItem.getQuantity()).thenReturn(1);
        when(mockCartItem.getSubtotal()).thenReturn(20.0);

        cartController.setTestMode(true);

        // Assert: Deleting a non-existing item does not throw exception
        assertDoesNotThrow(() -> cartController.removeCartItem(mockCartItem));
        assertEquals(0, cartController.getCartItems().size());
    }

    @Test
    void testDeleteCartItem_MultipleItems() {
        // Arrange: Mock two books and their respective cart items
        Book mockBook2 = mock(Book.class);
        CartItem mockCartItem2 = mock(CartItem.class);

        when(mockBook.getImagePath()).thenReturn("book1.jpg");
        when(mockBook.getQuantity()).thenReturn(10);
        when(mockBook.getPrice()).thenReturn(20.0);
        when(mockBook.getName()).thenReturn("Test Book 1");

        when(mockCartItem.getBook()).thenReturn(mockBook);
        when(mockCartItem.getQuantity()).thenReturn(1);
        when(mockCartItem.getSubtotal()).thenReturn(20.0);

        when(mockBook2.getImagePath()).thenReturn("book2.jpg");
        when(mockBook2.getQuantity()).thenReturn(5);
        when(mockBook2.getPrice()).thenReturn(30.0);
        when(mockBook2.getName()).thenReturn("Test Book 2");

        when(mockCartItem2.getBook()).thenReturn(mockBook2);
        when(mockCartItem2.getQuantity()).thenReturn(2);
        when(mockCartItem2.getSubtotal()).thenReturn(60.0);

        cartController.setTestMode(true);
        cartController.addCartItem(mockCartItem);
        cartController.addCartItem(mockCartItem2);

        // Assert: Initial state
        assertEquals(2, cartController.getCartItems().size());
        assertEquals(80.0, cartController.getTotalPrice());
        assertEquals(3, cartController.getTotalQuantity());

        // Act: Delete one cart item
        cartController.removeCartItem(mockCartItem);

        // Assert: Verify remaining cart items
        assertEquals(1, cartController.getCartItems().size());
        assertEquals(60.0, cartController.getTotalPrice());
        assertEquals(2, cartController.getTotalQuantity());

        // Act: Delete the remaining item
        cartController.removeCartItem(mockCartItem2);

        // Assert: Verify cart is empty
        assertEquals(0, cartController.getCartItems().size());
        assertEquals(0.0, cartController.getTotalPrice());
        assertEquals(0, cartController.getTotalQuantity());
    }
    @Test
    void testAddCartItem_NullCartItem() {
        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cartController.addCartItem(null);
        });
        assertEquals("CartItem cannot be null", exception.getMessage());
    }
//    @Test
//    void testAddCartItem_QuantityExceedsStock() {
//        // Arrange: Create a CartItem with quantity exceeding stock
//        when(mockBook.getQuantity()).thenReturn(5); // Book stock is 5
//        CartItem invalidCartItem = new CartItem(mockBook, 10, 200.0); // Quantity exceeds stock
//
//        // Act & Assert: Verify exception is thrown for exceeding stock
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            cartController.addCartItem(invalidCartItem);
//        });
//
//        assertEquals("Quantity exceeds stock availability", exception.getMessage());
//    }

    @Test
    void testAddCartItem_NonPositiveQuantity() {
        // Arrange: Create a CartItem with non-positive quantity
        when(mockBook.getQuantity()).thenReturn(10); // Book stock
        CartItem invalidCartItem = new CartItem(mockBook, 0, 0.0); // Quantity is zero

        // Act & Assert: Verify exception is thrown for non-positive quantity
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cartController.addCartItem(invalidCartItem);
        });

        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

//    @Test
//    void testAddCartItem_ValidBook() {
//        // Arrange: Create a valid book and cart item
//        when(mockBook.getQuantity()).thenReturn(10);
//        when(mockBook.getPrice()).thenReturn(20.0);
//        when(mockBook.getName()).thenReturn("Test Book");
//
//        CartItem validCartItem = new CartItem(mockBook, 1, 20.0);
//
//        // Act: Add the valid cart item
//        cartController.addCartItem(validCartItem);
//
//        // Assert: Verify cart item is added successfully
//        assertEquals(1, cartController.getCartItems().size());
//        assertEquals(20.0, cartController.getTotalPrice());
//    }
//

    @Test
    void testAddCartItem_NullBookInCartItem() {
        // Arrange
        CartItem invalidCartItem = new CartItem(null, 1, 10); // Book is null

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cartController.addCartItem(invalidCartItem);
        });
        assertEquals("Book in CartItem cannot be null", exception.getMessage());
    }
//    @Test
//    void testAddCartItem_InvalidQuantityZeroOrNegative() {
//        // Arrange
//        when(mockBook.getQuantity()).thenReturn(10);
//        CartItem invalidCartItemZero = new CartItem(mockBook, 0); // Quantity is zero
//        CartItem invalidCartItemNegative = new CartItem(mockBook, -1); // Quantity is negative
//
//        // Act and Assert for quantity zero
//        IllegalArgumentException exceptionZero = assertThrows(IllegalArgumentException.class, () -> {
//            cartController.addCartItem(invalidCartItemZero);
//        });
//        assertEquals("Quantity must be greater than zero", exceptionZero.getMessage());
//
//        // Act and Assert for quantity negative
//        IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class, () -> {
//            cartController.addCartItem(invalidCartItemNegative);
//        });
//        assertEquals("Quantity must be greater than zero", exceptionNegative.getMessage());
//    }

    @Test
    void testAddCartItem_QuantityExceedsStock() {
        // Arrange
        when(mockBook.getQuantity()).thenReturn(5); // Stock is 5
        CartItem invalidCartItem = new CartItem(mockBook, 6,20); // Quantity exceeds stock

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cartController.addCartItem(invalidCartItem);
        });
        assertEquals("Quantity exceeds stock availability", exception.getMessage());
    }

    @Test
    void testAddCartItem_ExceedsStockLimit() {
        cartController.setTestMode(true);

        // Arrange: Mock book properties
        when(mockBook.getName()).thenReturn("Test Book");
        when(mockBook.getImagePath()).thenReturn("book1.jpg");
        when(mockBook.getQuantity()).thenReturn(2); // Stock limit is 2
        when(mockBook.getPrice()).thenReturn(20.0);

        // Create a CartItem instance
        CartItem realCartItem = new CartItem(mockBook, 2,40); // Add max quantity

        // Act 1: Add the item to the cart
        cartController.addCartItem(realCartItem);

        // Act 2: Attempt to add beyond stock limit
        cartController.addCartItem(realCartItem);

        // Assert: Verify the alert was triggered and cart was not updated
        List<CartItem> cartItems = cartController.getCartItems();
        assertEquals(1, cartItems.size(), "Cart should still contain only one item.");
        CartItem existingItem = cartItems.get(0);
        assertEquals(2, existingItem.getQuantity(), "Quantity should not exceed stock limit.");
        assertEquals(40.0, cartController.getTotalPrice(), 0.01, "Cart total price should remain unchanged.");

        // Optionally, verify that the alert was triggered
        // This depends on how `handleAlert` is implemented (e.g., mock or log verification).
        // For example, if handleAlert logs messages, you could check the logs.
    }@Test
    void testHandleAlertTriggered() {
        // Mock handleAlert
        CartController spyController = spy(cartController);
        doNothing().when(spyController).handleAlert(anyString(), anyString());

        // Arrange: Mock book properties
        when(mockBook.getName()).thenReturn("Test Book");
        when(mockBook.getImagePath()).thenReturn("book1.jpg");
        when(mockBook.getQuantity()).thenReturn(2); // Stock limit is 2
        when(mockBook.getPrice()).thenReturn(20.0);

        // Create a CartItem instance
        CartItem realCartItem = new CartItem(mockBook, 2, 40); // Add max quantity

        // Add the item to the cart
        spyController.addCartItem(realCartItem);

        // Act: Attempt to add beyond stock limit
        spyController.addCartItem(realCartItem);

        // Verify handleAlert was called
        verify(spyController).handleAlert(eq("No More Books"), eq("You cannot buy more than 2 for Test Book"));
    }


}