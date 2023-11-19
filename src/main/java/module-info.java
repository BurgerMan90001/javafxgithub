module com.example.demo {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.web;

    requires se.michaelthelin.spotify;
   // requires eu.hansolo.tilesfx;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.commons.codec;
    requires kotlin.stdlib;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}