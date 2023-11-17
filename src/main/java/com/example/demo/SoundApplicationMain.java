package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import java.io.IOException;
import java.net.URI;



public class SoundApplicationMain extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) {
        // DO STUFF WHEn THE APPLICATION IS OPENED
        // SCENE NEEDS TO BE OPENED WHEN THE BUTTON IS CLICKED
/*
        AuthorizationCodePKCEUriExample authorizationCodePKCEUriExample = new AuthorizationCodePKCEUriExample();
     //   authorizationCodePKCEUriExample.main();
        WebView webView = new WebView();

        webView.getEngine().load(authorizationCodePKCEUriExample.authorizationCodeUri_Sync());



        VBox vBox = new VBox(webView);

        Scene scene = new Scene(vBox);

        stage.setTitle("MUSIC PLAYER PROJECT!");
        stage.setScene(scene);
        stage.show();


*/
         try {
            FXMLLoader fxmlLoader = new FXMLLoader(SoundApplicationMain.class.getResource("MusicPlayer.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("MUSIC PLAYER PROJECT!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void stop()  {
        // DO STUFF WHEN THE APPLICATION IS CLOSED!
        System.exit(0);
    }

    // main() calls lanch to lanch application !

}