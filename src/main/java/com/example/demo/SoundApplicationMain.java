package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class SoundApplicationMain extends Application {
    private Stage newStage;

    public static void main(String[] args) {
        launch();
    }
    public void setStage(Stage stage) {
        newStage = stage;
    }
    public Stage getNewStage() {
        return newStage;
    }
    public void showStage() {
        String musicPlayerTitle = "MUSIC PLAYER";
        newStage.setTitle(musicPlayerTitle);
        newStage.show();
    }


    @Override
    public void start(Stage primaryStage) {

        // DO STUFF WHEn THE APPLICATION IS OPENED
        // SCENE NEEDS TO BE OPENED WHEN THE BUTTON IS CLICKED

         try {
            Parent root = FXMLLoader.load(getClass().getResource("SignInPage.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            setStage(primaryStage);
            showStage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void stop()  {
        // DO STUFF WHEN THE APPLICATION IS CLOSED!
        System.exit(0);
    }
}







/*

     //   authorizationCodePKCEUriExample.main();
        WebView webView = new WebView();

        webView.getEngine().load(authorizationCodePKCEUriExample.authorizationCodeUri_Sync());



        VBox vBox = new VBox(webView);

        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.show();


*/