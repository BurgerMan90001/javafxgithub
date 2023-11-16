package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SoundApplicationMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // DO STUFF WHEn THE APPLICATION IS OPENED
        FXMLLoader fxmlLoader = new FXMLLoader(SoundApplicationMain.class.getResource("MusicPlayer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop()  {
        // DO STUFF WHEN THE APPLICATION IS CLOSED!
    }

    // main() calls lanch to lanch application !
    public static void main(String[] args) {
        launch();
    }
}