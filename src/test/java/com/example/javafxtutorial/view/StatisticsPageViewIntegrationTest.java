package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.BookController;
import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.controller.OrderController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
public class StatisticsPageViewIntegrationTest {

    private StatisticsPageView statisticsPageView;
    private BookController bookController;
    private OrderController orderController;
    private EmployeeController employeeController;

    @Start
    public void start(Stage stage) {
        bookController = new BookController();
        orderController = new OrderController();
        employeeController = new EmployeeController();

        statisticsPageView = new StatisticsPageView(orderController, bookController, employeeController);

        ScrollPane statisticsPage = statisticsPageView.createStatisticsPage("Administrator");
        stage.setScene(new javafx.scene.Scene(statisticsPage, 800, 600));
        stage.show();
    }

    @Test
    public void testPageInitialization(FxRobot robot) {
        verifyThat("#searchButton", Node::isVisible);
    }

    @Test
    public void testSearchButtonWithValidDateRange(FxRobot robot) throws InterruptedException {
        DatePicker fromDatePicker = robot.lookup("#fromDatePicker").query();
        DatePicker toDatePicker = robot.lookup("#toDatePicker").query();
        Button searchButton = robot.lookup("#searchButton").query();

        robot.interact(() -> {
            fromDatePicker.setValue(LocalDate.of(2023, 1, 1));
            toDatePicker.setValue(LocalDate.of(2023, 12, 31));
        });

        robot.clickOn(searchButton);
        Thread.sleep(1000);

        verifyThat("#bookStatItem", Node::isVisible);
    }

    @Test
    public void testSearchButtonWithInvalidDateRange(FxRobot robot) throws InterruptedException {
        DatePicker fromDatePicker = robot.lookup("#fromDatePicker").query();
        DatePicker toDatePicker = robot.lookup("#toDatePicker").query();
        Button searchButton = robot.lookup("#searchButton").query();

        robot.interact(() -> {
            fromDatePicker.setValue(LocalDate.of(2023, 12, 31));
            toDatePicker.setValue(LocalDate.of(2023, 1, 1));
        });

        robot.clickOn(searchButton);
        verifyThat(".alert", Node::isVisible);
        Thread.sleep(1000);

    }
}