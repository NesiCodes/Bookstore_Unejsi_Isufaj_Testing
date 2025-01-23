package com.example.javafxtutorial.unit;

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

class EditBookDetailsPageViewCETest {
    @Test
    void testClassNullOrEmptyStrings() {
        InvalidQuantityException nullException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity(null)
        );
        assertEquals("Quantity cannot be null or empty", nullException.getMessage());

        InvalidQuantityException emptyException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("")
        );
        assertEquals("Quantity cannot be null or empty", emptyException.getMessage());

        InvalidQuantityException spaceException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity(" ")
        );
        assertEquals("Quantity cannot be null or empty", spaceException.getMessage());
    }

    @Test
    void testClassValidIntegerStrings() throws Exception {
        assertEquals(0, new EditBookDetailsPageView().parseQuantity("0"));
        assertEquals(1, new EditBookDetailsPageView().parseQuantity("1"));
        assertEquals(1000, new EditBookDetailsPageView().parseQuantity("1000"));
        assertEquals(Integer.MAX_VALUE, new EditBookDetailsPageView().parseQuantity(String.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    void testClassInvalidStrings() {
        InvalidQuantityException negativeException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("-1")
        );
        assertEquals("Quantity cannot be negative", negativeException.getMessage());

        InvalidQuantityException nonIntegerException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("abc")
        );
        assertTrue(nonIntegerException.getMessage().startsWith("Invalid quantity: "));
        assertTrue(nonIntegerException.getCause() instanceof NumberFormatException);

        InvalidQuantityException decimalException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("1.5")
        );
        assertTrue(decimalException.getMessage().startsWith("Invalid quantity: "));
    }

    @Test
    void testClassWhitespaceHandling() throws Exception {
        assertEquals(10, new EditBookDetailsPageView().parseQuantity(" 10 "));
        InvalidQuantityException negativeWhitespaceException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity("  -1 ")
        );
        assertEquals("Quantity cannot be negative", negativeWhitespaceException.getMessage());

        InvalidQuantityException invalidWhitespaceException = assertThrows(
                InvalidQuantityException.class,
                () -> new EditBookDetailsPageView().parseQuantity(" abc ")
        );
        assertTrue(invalidWhitespaceException.getMessage().startsWith("Invalid quantity: "));
    }



}