module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires se.michaelthelin.spotify;
   // requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.commons.codec;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}