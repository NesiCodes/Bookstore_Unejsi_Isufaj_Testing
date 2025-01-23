package com.example.javafxtutorial;

import java.time.LocalDate;

public class Tester {
    public static void main(String[] args) {
        LocalDate l1 = LocalDate.of(2022, 1, 15);
        LocalDate l2 = LocalDate.of(2022, 1, 15);
        LocalDate l3 = LocalDate.of(2022, 1, 31);

        if (isBetweenInclusive(l1, l2, l3)) {
            System.out.println(l1 + " is between " + l2 + " and " + l3 + " (inclusive).");
        } else {
            System.out.println(l1 + " is not between " + l2 + " and " + l3 + " (inclusive).");
        }
    }

    public static boolean isBetweenInclusive(LocalDate l1, LocalDate l2, LocalDate l3) {
        return (l1.isEqual(l2) ||  l1.isEqual(l3) || l1.isAfter(l2)) && (l1.isBefore(l3));
    }
}


//    Date date = new Date();
//
//    Instant instant = date.toInstant();
//
//    LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//        System.out.println("Date: " + date);
//                System.out.println("LocalDate: " + localDate);
