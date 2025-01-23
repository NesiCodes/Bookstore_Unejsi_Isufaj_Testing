//package com.example.javafxtutorial.auxiliary;
//
//import com.example.javafxtutorial.model.Book;
//import com.example.javafxtutorial.model.CartItem;
//import com.example.javafxtutorial.model.Employee;
//import com.example.javafxtutorial.model.Order;
//import javafx.application.Platform;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.testfx.framework.junit5.ApplicationExtension;
//import org.testfx.util.WaitForAsyncUtils;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(ApplicationExtension.class)
//public class PrintWindowTest {
//
//    @BeforeAll
//    public static void initJavaFX() {
//        Platform.startup(() -> {});
//    }
//
//    @Test
//    void testConstructor() {
//        Order mockOrder = Mockito.mock(Order.class);
//
//        PrintWindow printWindow = new PrintWindow(mockOrder);
//
//        assertNotNull(printWindow);
//    }
//
//    @Test
//    void testStart() throws ParseException {
//        Order mockOrder = Mockito.mock(Order.class);
//        when(mockOrder.getOrderId()).thenReturn("123");
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date specificDate = dateFormat.parse("2023-10-01");
//        when(mockOrder.getDateCreated()).thenReturn(specificDate);
//
//        Employee mockEmployee = Mockito.mock(Employee.class);
//        when(mockEmployee.getName()).thenReturn("John Doe");
//        when(mockEmployee.getAccessLevel()).thenReturn(1);
//        when(mockOrder.getEmployee()).thenReturn(mockEmployee);
//
//        Book mockBook = Mockito.mock(Book.class);
//        when(mockBook.getName()).thenReturn("Test Book");
//        when(mockBook.getAuthor()).thenReturn("Test Author");
//        when(mockBook.getPrice()).thenReturn(20.0);
//
//        CartItem mockCartItem = Mockito.mock(CartItem.class);
//        when(mockCartItem.getBook()).thenReturn(mockBook); // Ensure getBook() returns the mock Book
//        when(mockCartItem.getQuantity()).thenReturn(2);
//        when(mockCartItem.getSubtotal()).thenReturn(40.0);
//
//        ArrayList<CartItem> cartItems = new ArrayList<>();
//        cartItems.add(mockCartItem);
//        when(mockOrder.getCartItems()).thenReturn(cartItems);
//
//        when(mockOrder.getTotalQuantity()).thenReturn(2);
//        when(mockOrder.getTotalPrice()).thenReturn(40.0);
//
//        Stage mockStage = Mockito.mock(Stage.class);
//        PrintWindow printWindow = new PrintWindow(mockOrder);
//
//        Platform.runLater(() -> printWindow.start(mockStage));
//
//        WaitForAsyncUtils.waitForFxEvents();
//
//        verify(mockStage, times(1)).setTitle("Print Order");
//        verify(mockStage, times(1)).setScene(any());
//    }
//}