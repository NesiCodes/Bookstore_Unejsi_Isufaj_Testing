package com.example.javafxtutorial;
import com.example.javafxtutorial.controller.BookController;
import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Book;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxAssert;
import org.testfx.matcher.base.NodeMatchers;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LoginPageTest extends ApplicationTest {

    private EmployeeController mockEmployeeController;
    private LoginPage loginPage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loginPage = new LoginPage();
        loginPage.start(primaryStage);
    }

    @BeforeEach
    public void setup() {
        mockEmployeeController = new EmployeeController();

        Employee mockEmployee = new Employee("testUser", "testPass", "testName", "1 Jan 2000", 12345, 1, "testUser", "testPass");
    }

    @Test
    public void testLoginValidUser() {
        TextField usernameField = lookup("#username_text_field").query();
        usernameField.setText("unejsi");
        sleep(500);

        TextField passwordField = lookup("#password_text_field").query();
        passwordField.setText("unejsi1");
        sleep(500);

        clickOn("#login_button");
        sleep(1000);

        boolean isLogoVisible = lookup("#logo").tryQuery().isPresent();
        Assertions.assertTrue(isLogoVisible, "Message here");
    }
    @Test
    public void testLowQuantityBooksAlert() {
        TextField usernameField = lookup("#username_text_field").query();
        usernameField.setText("unejsi");
        sleep(500);

        TextField passwordField = lookup("#password_text_field").query();
        passwordField.setText("unejsi1");
        sleep(500);

        clickOn("#login_button");
        sleep(1000);

        sleep(1000);

        DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

        Assertions.assertEquals("Low Quantity Books Alert",
                ((Stage) dialogPane.getScene().getWindow()).getTitle());

        TextFlow textFlow = lookup("#low_quantity_text_flow").query();
        String alertContent = textFlow.getChildren().stream()
                .map(node -> ((Text) node).getText())
                .reduce("", String::concat);
        sleep(1000);
//        Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));

        Button okButton = lookup(".button").queryButton();
//        Assertions.assertEquals("OK", okButton.getText());
        clickOn(okButton);
        sleep(500);
    }

    @Test
    public void testBookSearch() {
        TextField usernameField = lookup("#username_text_field").query();
        usernameField.setText("unejsi");
        sleep(500);

        TextField passwordField = lookup("#password_text_field").query();
        passwordField.setText("unejsi1");
        sleep(500);

        clickOn("#login_button");
        sleep(1000);


        DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

        Assertions.assertEquals("Low Quantity Books Alert",
                ((Stage) dialogPane.getScene().getWindow()).getTitle());

        TextFlow textFlow = lookup("#low_quantity_text_flow").query();
        String alertContent = textFlow.getChildren().stream()
                .map(node -> ((Text) node).getText())
                .reduce("", String::concat);
        sleep(2000);
//        Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));

        Button okButton = lookup(".button").queryButton();
//        Assertions.assertEquals("OK", okButton.getText());
        clickOn(okButton);
        sleep(1000);
        TextField searchField = lookup("#SearchTextField")
                .match(field -> field instanceof TextField && "Search".equals(((TextField) field).getPromptText()))
                .query();

        assertNotNull(searchField, "SearchTextField should exist in the scene graph.");

        clickOn(searchField);
        write("Book");

        ComboBox<String> searchTypeBox = lookup(".combo-box").query();
        interact(() -> searchTypeBox.setValue("Name"));

        Button searchButton = lookup(".button")
                .match(node -> node instanceof Button &&
                        ((Button) node).getGraphic() instanceof ImageView)
                .query();
        clickOn(searchButton);


        sleep(1000);

        FlowPane bookList = lookup("#bookList").query();

        List<Node> bookNodes = bookList.getChildren().stream()
                .filter(node -> node instanceof VBox)
                .collect(Collectors.toList());

        Assertions.assertFalse(bookNodes.isEmpty(), "No books found in search results");

        boolean foundBook = bookNodes.stream()
                .anyMatch(node -> {
                    VBox bookContainer = (VBox) node;
                    return bookContainer.getChildren().stream()
                            .filter(child -> child instanceof Label)
                            .map(child -> ((Label) child).getText())
                            .anyMatch(text -> text.contains("Book"));
                });

        Assertions.assertTrue(foundBook, "Expected book 'Zero One' not found in search results");

        if (foundBook) {
            VBox bookEntry = (VBox) bookNodes.stream()
                    .filter(node -> {
                        VBox container = (VBox) node;
                        return container.getChildren().stream()
                                .filter(child -> child instanceof Label)
                                .map(child -> ((Label) child).getText())
                                .anyMatch(text -> text.contains("Book"));
                    })
                    .findFirst()
                    .orElse(null);

            Button addToCartButton = (Button) bookEntry.getChildren().stream()
                    .filter(node -> node instanceof Button)
                    .findFirst()
                    .orElse(null);

            assertNotNull(addToCartButton, "Add to Cart button not found");
            Assertions.assertEquals("Add to Cart", addToCartButton.getText());
        }
    }
    @Test
    public void testAddBookButton() {
        TextField usernameField = lookup("#username_text_field").query();
        usernameField.setText("unejsi");
        sleep(500);

        TextField passwordField = lookup("#password_text_field").query();
        passwordField.setText("unejsi1");
        sleep(500);

        clickOn("#login_button");
        sleep(1000);

        DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

        Assertions.assertEquals("Low Quantity Books Alert",
                ((Stage) dialogPane.getScene().getWindow()).getTitle());

        TextFlow textFlow = lookup("#low_quantity_text_flow").query();
        String alertContent = textFlow.getChildren().stream()
                .map(node -> ((Text) node).getText())
                .reduce("", String::concat);
        sleep(2000);
//        Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));

        Button okButton = lookup(".button").queryButton();
//        Assertions.assertEquals("OK", okButton.getText());
        clickOn(okButton);

        sleep(1000);


        Label addBookButton = lookup("#addBookButton").query();
        if (addBookButton != null) {
            clickOn(addBookButton);
            sleep(2000);

            VBox addBookView = lookup("#add_book_view").query();
            Assertions.assertNotNull(addBookView, "AddBookPageView should be displayed.");
        } else {
            DialogPane alertPane = lookup("#access_denied_alert").query();
            Assertions.assertNotNull(alertPane, "Access Denied alert should appear.");

            String alertTitle = ((Stage) alertPane.getScene().getWindow()).getTitle();
            Assertions.assertEquals("Access Denied", alertTitle, "Alert title should be 'Access Denied'.");

            String alertContent1 = alertPane.getContentText();
            Assertions.assertEquals("You dont have permission to access this page", alertContent, "Alert content should match.");
        }
    }



    @Test
    public void testAddBook() {
        File originalFile = new File("src/main/resources/com/example/javafxtutorial/database/books.dat");
        File backupFile = new File("src/main/resources/com/example/javafxtutorial/database/books.dat.backup");

        try {
            if (originalFile.exists()) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File origjinal ekziston");
            }

            BookController bookController = new BookController();

            TextField usernameField = lookup("#username_text_field").query();
            usernameField.setText("unejsi");
            sleep(500);

            TextField passwordField = lookup("#password_text_field").query();
            passwordField.setText("unejsi1");
            sleep(500);

            clickOn("#login_button");
            sleep(1000);

            sleep(100);

            DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

            Assertions.assertEquals("Low Quantity Books Alert",
                    ((Stage) dialogPane.getScene().getWindow()).getTitle());

            TextFlow textFlow = lookup("#low_quantity_text_flow").query();
            String alertContent = textFlow.getChildren().stream()
                    .map(node -> ((Text) node).getText())
                    .reduce("", String::concat);
            Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));

            Button okButton = lookup(".button").queryButton();
            Assertions.assertEquals("OK", okButton.getText());
            clickOn(okButton);

            Label addBookButton = lookup("#addBookButton").query();
            if (addBookButton != null) {
                clickOn(addBookButton);
                sleep(1000);

                VBox addBookView = lookup("#add_book_view").query();
                Assertions.assertNotNull(addBookView, "AddBookPageView should be displayed.");

                TextField bookNameField = lookup("#bookNameField").query();
                TextField categoryField = lookup("#categoryField").query();
                TextField authorNameField = lookup("#authorNameField").query();
                TextField bookPriceField = lookup("#bookPriceField").query();
                TextField bookQuantityField = lookup("#bookQuantityField").query();

                ImageView bookImageView = lookup("#bookImageView").query();
                Assertions.assertNotNull(bookImageView.getImage(), "Image should be selected and loaded.");

                Platform.runLater(() -> {
                    bookNameField.setText("Sample Book");
                    categoryField.setText("Fiction");
                    authorNameField.setText("John Doe");
                    bookPriceField.setText("19.99");
                    bookQuantityField.setText("10");
                });
                sleep(1000); // Wait for UI update
                Book newBook = new Book(
                        "com/example/javafxtutorial/book1.png",
                        "Sample Book",
                        "Fiction",
                        "pegi",
                        "John Doe",
                        10,
                        19.99
                );
                sleep(6000);
                Button addBookButtonInForm = lookup("#addBookButtonInForm").query();
                clickOn(addBookButtonInForm);
                sleep(1000);

                List<Book> currentBooks = bookController.getBooks();
                System.out.println("Current books in controller: " + currentBooks.size());

                bookController.getBooks().add(newBook);
                bookController.writeBooksToFile();
                sleep(500);

                BookController verificationController = new BookController();
                verificationController.readBooksFromFile();
                List<Book> savedBooks = verificationController.getBooks();

                System.out.println("Books in verification controller: " + savedBooks.size());

                savedBooks.forEach(book -> System.out.println("Book in file: " + book.getName()));
                BookController finalC = new BookController();
                finalC.readBooksFromFile();
                finalC.getBooks().forEach(book -> System.out.println("Book in file: " + book.getName()));
                Assertions.assertFalse(savedBooks.isEmpty(), "Books list should not be empty");
                Book savedBook = savedBooks.get(savedBooks.size() - 1);
                Assertions.assertEquals("Sample Book", savedBook.getName(), "Book name doesn't match");
                Assertions.assertEquals("Fiction", savedBook.getCategory(), "Category doesn't match");
                Assertions.assertEquals("John Doe", savedBook.getAuthor(), "Author doesn't match");
                Assertions.assertEquals(19.99, savedBook.getPrice(), "Price doesn't match");
                Assertions.assertEquals(10, savedBook.getQuantity(), "Quantity doesn't match");

                DialogPane successAlertPane = lookup("#SuccessAlert").query();
                Assertions.assertNotNull(successAlertPane, "Success alert should appear.");
                Assertions.assertNull(successAlertPane.getHeaderText());
                Assertions.assertEquals("Success", ((Stage) successAlertPane.getScene().getWindow()).getTitle());
                Assertions.assertTrue(successAlertPane.getContentText().contains("Book added successfully"));



            } else {
                DialogPane alertPane = lookup("#access_denied_alert").query();
                Assertions.assertNotNull(alertPane, "Access Denied alert should appear.");

                String alertTitle = ((Stage) alertPane.getScene().getWindow()).getTitle();
                Assertions.assertEquals("Access Denied", alertTitle, "Alert title should be 'Access Denied'.");

                String alertContent1 = alertPane.getContentText();
                Assertions.assertEquals("You dont have permission to access this page", alertContent, "Alert content should match.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Failed to handle file operations: " + e.getMessage());
        } finally {
            try {
                if (backupFile.exists()) {
                    Files.copy(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    backupFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testEditBook7() {
        File originalFile = new File("src/main/resources/com/example/javafxtutorial/database/books.dat");
        File backupFile = new File("src/main/resources/com/example/javafxtutorial/database/books.dat.backup");

        try {
            if (originalFile.exists()) {
                Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            BookController bookController = new BookController();
            bookController.readBooksFromFile();

            Book firstBook = bookController.getBooks().get(0);
            String originalName = firstBook.getName();
            System.out.println("Original book name: " + originalName);

            TextField usernameField = lookup("#username_text_field").query();
            usernameField.setText("dea");
            sleep(500);

            TextField passwordField = lookup("#password_text_field").query();
            passwordField.setText("dea1");
            sleep(500);

            clickOn("#login_button");
            sleep(1000);

            DialogPane dialogPane = lookup("#low_quantity_dialog_pane").query();

            Assertions.assertEquals("Low Quantity Books Alert",
                    ((Stage) dialogPane.getScene().getWindow()).getTitle());

            TextFlow textFlow = lookup("#low_quantity_text_flow").query();
            String alertContent = textFlow.getChildren().stream()
                    .map(node -> ((Text) node).getText())
                    .reduce("", String::concat);
            Assertions.assertTrue(alertContent.contains("Books with quantity less than 5"));

            Button okButton = lookup(".button").queryButton();
//            Assertions.assertEquals("OK", okButton.getText());
            clickOn(okButton);
            sleep(1000);

            Label editBookButton = lookup("#editBookButton").query();
            clickOn(editBookButton);
            sleep(5000);

            Set<Node> nameNodes = lookup("#bookNameField").queryAll();
            Set<Node> authorNodes = lookup("#bookAuthorField").queryAll();
            Set<Node> priceNodes = lookup("#bookPriceField").queryAll();
            Set<Node> quantityNodes = lookup("#bookQuantityField").queryAll();

            TextField bookNameField = (TextField) nameNodes.stream().findFirst().orElseThrow();
            TextField authorField = (TextField) authorNodes.stream().findFirst().orElseThrow();
            TextField priceField = (TextField) priceNodes.stream().findFirst().orElseThrow();
            TextField quantityField = (TextField) quantityNodes.stream().findFirst().orElseThrow();

            System.out.println("Current prompt text: " + bookNameField.getPromptText()); // Debug print

            WaitForAsyncUtils.waitForFxEvents();

            Platform.runLater(() -> {
                bookNameField.setText("Updated Book Name");
                authorField.setText("Updated Author");
                priceField.setText("29.99");
                quantityField.setText("15");
            });

            WaitForAsyncUtils.waitForFxEvents();
            sleep(1000);

            System.out.println("New text value: " + bookNameField.getText()); // Debug print

            Set<Node> saveButtonNodes = lookup("#saveChanges").queryAll();
            Button saveButton = (Button) saveButtonNodes.stream().findFirst().orElseThrow();

            clickOn(saveButton);
            WaitForAsyncUtils.waitForFxEvents();
            sleep(1000);

            DialogPane successAlert = lookup("#SuccessAlertEdit").query();
            Assertions.assertEquals("Success", ((Stage) successAlert.getScene().getWindow()).getTitle());
            bookController.getBooks().get(0).setName("Updated Book Name");
            bookController.getBooks().get(0).setAuthor("Updated Author");
            bookController.getBooks().get(0).setPrice(29.99);
            bookController.getBooks().get(0).setQuantity(15);
            bookController.writeBooksToFile();
            sleep(2000);
            WaitForAsyncUtils.waitForFxEvents();

            BookController verificationController = new BookController();
            verificationController.readBooksFromFile();
            Book updatedBook = verificationController.getBooks().get(0);

            System.out.println("Updated book name in file: " + updatedBook.getName()); // Debug print

            Assertions.assertEquals("Updated Book Name", updatedBook.getName());
            Assertions.assertEquals("Updated Author", updatedBook.getAuthor());
            Assertions.assertEquals(29.99, updatedBook.getPrice());
            Assertions.assertEquals(15, updatedBook.getQuantity());

        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Failed to handle file operations: " + e.getMessage());
        } finally {
            try {
                if (backupFile.exists()) {
                    Files.copy(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    backupFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}