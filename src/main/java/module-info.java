module com.example.javafxtutorial {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires java.desktop;
    requires java.logging;


    opens com.example.javafxtutorial to javafx.fxml;
    exports com.example.javafxtutorial;
    exports com.example.javafxtutorial.auxiliary;
    opens com.example.javafxtutorial.auxiliary to javafx.fxml;
}