package com.example.javafxtutorial.auxiliary;

import java.util.Random;

public class ISBNGenerator {

    public static void main(String[] args) {
        String generatedISBN = generateISBN();
        System.out.println("Generated ISBN: " + generatedISBN);
    }

    public static String generateISBN() {
        String groupIdentifier = "978";

        String uniqueIdentifier = generateRandomNumericString(9);

        int checksum = calculateISBN13Checksum(groupIdentifier + uniqueIdentifier);

        String isbn13 = groupIdentifier + uniqueIdentifier + checksum;

        return isbn13;
    }

    static String generateRandomNumericString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    static int calculateISBN13Checksum(String isbn12) {
        int sum = 0;

        for (int i = 0; i < isbn12.length(); i++) {
            int digit = Character.getNumericValue(isbn12.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checksum = (10 - (sum % 10)) % 10;
        return checksum;
    }
}
