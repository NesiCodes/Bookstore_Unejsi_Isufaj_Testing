package com.example.javafxtutorial.unit;
//package com.example.javafxtutorial.test;

import com.example.javafxtutorial.auxiliary.InvalidQuantityException;
import com.example.javafxtutorial.view.EditBookDetailsPageView;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.javafxtutorial.auxiliary.InvalidQuantityException;
import com.example.javafxtutorial.view.EditBookDetailsPageView;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class EditBookDetailsPageViewTest {
    @Test
    void testParseQuantityUsingReflection() throws Exception {
        Method method = EditBookDetailsPageView.class.getDeclaredMethod("parseQuantity", String.class);
        method.setAccessible(true);

        String invalidQuantity = "-1";

        InvocationTargetException thrown = assertThrows(
                InvocationTargetException.class,
                () -> method.invoke(new EditBookDetailsPageView(), invalidQuantity)
        );

        Throwable cause = thrown.getCause();
        assertTrue(cause instanceof InvalidQuantityException);
        assertEquals("Quantity cannot be negative", cause.getMessage());
    }
    @Test
    void testParseQuantityThrowsExceptionWhenNull() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity(null)
        );

        assertEquals("Quantity cannot be null or empty", thrown.getMessage());
    }
    @Test
    void testParseQuantityThrowsExceptionWhenEmpty() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("")
        );

        assertEquals("Quantity cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testParseQuantityThrowsExceptionForInvalidNumber() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("abc")
        );

        assertTrue(thrown.getMessage().startsWith("Invalid quantity: "));
        assertTrue(thrown.getCause() instanceof NumberFormatException);
    }
    @Test
    void testParseQuantityReturnsValidQuantity() throws Exception {
        int quantity = new EditBookDetailsPageView().parseQuantity("10");
        assertEquals(10, quantity);
    }
    @Test
    void testParseQuantityThrowsExceptionForNegativeQuantity() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("-1")
        );

        assertEquals("Quantity cannot be negative", thrown.getMessage());
    }


}
