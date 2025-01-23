package com.example.javafxtutorial.view;
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

class EditBookDetailsPageViewBVTTest {
    @Test
    void testBoundaryNullInput() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity(null)
        );
        assertEquals("Quantity cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testBoundaryEmptyString() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("")
        );
        assertEquals("Quantity cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testBoundarySpaceString() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity(" ")
        );
        assertEquals("Quantity cannot be null or empty", thrown.getMessage());
    }

    @Test
    void testBoundaryValidZero() throws Exception {
        int quantity = new EditBookDetailsPageView().parseQuantity("0");
        assertEquals(0, quantity);
    }

    @Test
    void testBoundaryValidPositive() throws Exception {
        int quantity = new EditBookDetailsPageView().parseQuantity("1");
        assertEquals(1, quantity);
    }

    @Test
    void testBoundaryValidLargeValue() throws Exception {
        int quantity = new EditBookDetailsPageView().parseQuantity(String.valueOf(Integer.MAX_VALUE));
        assertEquals(Integer.MAX_VALUE, quantity);
    }

    @Test
    void testBoundaryNegativeValue() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("-1")
        );
        assertEquals("Quantity cannot be negative", thrown.getMessage());
    }

    @Test
    void testBoundaryInvalidFormat() {
        InvalidQuantityException thrown = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("abc")
        );
        assertTrue(thrown.getMessage().startsWith("Invalid quantity: "));
        assertTrue(thrown.getCause() instanceof NumberFormatException);
    }


}