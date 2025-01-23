package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookController {

    private ArrayList<Book> books;
    private final String filePath = "src/main/resources/com/example/javafxtutorial/database/books.dat";

    public BookController() {
        books = new ArrayList<>();
        readBooksFromFile();
    }

    public void readBooksFromFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            books = (ArrayList<Book>) ois.readObject();
            System.out.println("Books read from file: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeBooksToFile() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/books.dat"))) {
            oos.writeObject(books);
            System.out.println("po fus librat ne file");
            System.out.println("Books written to file: " + "src/main/resources/com/example/javafxtutorial/database/books.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBooksToFile(List<Book> books) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/books.dat"))) {
            oos.writeObject(books);
            System.out.println("Books written to file: " + "src/main/resources/com/example/javafxtutorial/database/books.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book){
        this.books.add(book);
    }


    public void printBooks(){
        for(Book b: books){
            System.out.println(b);
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    //testing
    public static void main(String[] args) {
        BookController bookController = new BookController();
//
        bookController.printBooks();


//        ArrayList<Book> books = new ArrayList<>();
//        books.add(new Book("book1.png", "Book1", "Adventure", "Botimet Pegi","Author1", 5, 20));
//        books.add(new Book("book2.png", "Book2", "Mystery", "PublisherX", "Author2", 7, 15));
//        books.add(new Book("book3.png", "Book3", "Fantasy", "ABC Books", "Author3", 8, 18));
//        books.add(new Book("book4.png", "Book4", "Science Fiction", "XYZ Publications", "Author4", 10, 25));
//        books.add(new Book("book5.png", "Book5", "Romance", "Libra Press", "Author5", 6, 22));
//        books.add(new Book("book6.png", "Book6", "Thriller", "Golden Books", "Author6", 12, 30));
//        books.add(new Book("book7.png", "Book7", "Historical Fiction", "Rainbow Publishers", "Author7", 9, 28));
//        books.add(new Book("book8.png", "Book8", "Comedy", "Sunrise Printing", "Author8", 15, 35));
//        books.add(new Book("book9.png", "Book9", "Drama", "Moonlit Press", "Author9", 11, 26));
//        books.add(new Book("book10.png", "Book10", "Horror", "Starlight Publications", "Author10", 14, 32));
//        books.add(new Book("book11.png", "Book11", "Biography", "Galaxy Books", "Author11", 13, 29));
//        books.add(new Book("book12.png", "Book12", "Self-Help", "Skyline Publishing", "Author12", 16, 40));
//        books.add(new Book("book13.png", "Book13", "Travel", "Voyager Press", "Author13", 0, 38));
//        books.add(new Book("book14.png", "Book14", "Poetry", "Blossom Books", "Author14", 5, 45));
//        books.add(new Book("book15.png", "Book15", "Cookbook", "Tasty Creations", "Author15", 17, 33));
//        books.add(new Book("book16.png", "Book16", "Graphic Novel", "Inkwell Comics", "Author16", 21, 50));
//        books.add(new Book("book17.png", "Book17", "Children's", "Tiny Tots Publishing", "Author17", 19, 36));
//        books.add(new Book("book18.png", "Book18", "Business", "Corporate Insights", "Author18", 0, 55));
//        books.add(new Book("book19.png", "Book19", "Psychology", "Mind Matters", "Author19", 22, 48));
//        books.add(new Book("book20.png", "Book20", "Science", "Tech Knowledge", "Author20", 13, 60));
//
//
//        bookController.writeBooksToFile(books);
    }

}
