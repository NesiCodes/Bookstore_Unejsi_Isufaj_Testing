package com.example.javafxtutorial.auxiliary;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class ISBNGeneratorTest {

    @Test
    void testGenerateISBN() {
        String isbn = ISBNGenerator.generateISBN();

        assertNotNull(isbn);
        assertEquals(13, isbn.length());
        assertTrue(isbn.startsWith("978"));
        assertTrue(isValidISBN13(isbn));
    }

    @Test
    void testGenerateRandomNumericString() {
        int length = 9;

        String randomString = ISBNGenerator.generateRandomNumericString(length);

        assertNotNull(randomString);
        assertEquals(length, randomString.length());
        assertTrue(randomString.matches("\\d+"));
    }

    @Test
    void testCalculateISBN13Checksum() {
        // Arrange
        String isbn12 = "978030640615";

        // Act
        int checksum = ISBNGenerator.calculateISBN13Checksum(isbn12);

        // Assert
        assertEquals(7, checksum);
    }

    @Test
    void testCalculateISBN13Checksum_EdgeCases() {
        String isbn12Case1 = "978000000000";
        int checksumCase1 = ISBNGenerator.calculateISBN13Checksum(isbn12Case1);
        assertEquals(2, checksumCase1);

        String isbn12Case2 = "978123456789";
        int checksumCase2 = ISBNGenerator.calculateISBN13Checksum(isbn12Case2);
        assertEquals(7, checksumCase2);
    }

    private boolean isValidISBN13(String isbn13) {
        int sum = 0;
        for (int i = 0; i < isbn13.length(); i++) {
            int digit = Character.getNumericValue(isbn13.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum % 10 == 0;
    }
}