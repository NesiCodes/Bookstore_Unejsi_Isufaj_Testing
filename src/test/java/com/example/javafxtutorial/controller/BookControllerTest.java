//package com.example.javafxtutorial.controller;
//
//import com.example.javafxtutorial.model.Book;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BookControllerTest {
//
//    private BookController bookController;
//    private File mockFile;
//    private ObjectInputStream mockObjectInputStream;
//    private ObjectOutputStream mockObjectOutputStream;
//
//    @BeforeEach
//    void setUp() {
//        // Mock the file and streams
//        mockFile = mock(File.class);
//        mockObjectInputStream = mock(ObjectInputStream.class);
//        mockObjectOutputStream = mock(ObjectOutputStream.class);
//
//        // Create a new BookController instance
//        bookController = new BookController();
//    }
//
//
//
//
//
//
//    @Test
//    void testAddBook() {
//        // Arrange
//        Book newBook = new Book("book1.png", "Book1", "Adventure", "Botimet Pegi", "Author1", 5, 20);
//
//        // Act
//        bookController.addBook(newBook);
//
//        // Assert
//        assertTrue(bookController.getBooks().contains(newBook));
//    }
//
//    @Test
//    void testGetBooks() {
//        // Arrange
//        ArrayList<Book> mockBooks = new ArrayList<>();
//        mockBooks.add(new Book("book1.png", "Book1", "Adventure", "Botimet Pegi", "Author1", 5, 20));
//        bookController.setBooks(mockBooks);
//
//        // Act
//        List<Book> books = bookController.getBooks();
//
//        // Assert
//        assertEquals(mockBooks, books);
//    }
//
//    @Test
//    void testSetBooks() {
//        // Arrange
//        ArrayList<Book> mockBooks = new ArrayList<>();
//        mockBooks.add(new Book("book1.png", "Book1", "Adventure", "Botimet Pegi", "Author1", 5, 20));
//
//        // Act
//        bookController.setBooks(mockBooks);
//
//        // Assert
//        assertEquals(mockBooks, bookController.getBooks());
//    }
//
//    // Helper method to inject a mock file path into the BookController
//    private void injectMockFilePath() {
//        try {
//            // Use reflection to set the private filePath field
//            java.lang.reflect.Field field = BookController.class.getDeclaredField("filePath");
//            field.setAccessible(true);
//            field.set(bookController, "src/test/resources/com/example/javafxtutorial/database/books.dat");
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to inject mock file path", e);
//        }
//    }
//}