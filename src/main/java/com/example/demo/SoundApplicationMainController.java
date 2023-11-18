package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.web.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.concurrent.Worker.State;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.stage.Stage;

public class SoundApplicationMainController implements Initializable {

    private final SpotifyWebApi spotifyWebApi = new SpotifyWebApi();
    private final SoundApplicationMain soundApplicationMain = new SoundApplicationMain();
    private WebView webView;
    private WebEngine engine;
    private Scene scene;
    private Stage stage;


    // initialize is called when the program is started!
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialized!");
        webView = new WebView();
        engine = webView.getEngine();


        // creates objects needed for window


        VBox vBox = new VBox(webView);
        stage = new Stage();
        scene = new Scene(vBox);
    }



    public void loadSpotiySignInPage() throws IOException {
        System.out.println("sign in clicked!");

        webView.setZoom(0.80);

        Map<String, List<String>> headers = new LinkedHashMap<>();
        headers.put("Set-Cookie", List.of("name=value"));
        java.net.CookieHandler.getDefault().put(spotifyWebApi.getRedirectUri(), headers);


        // loads the uri
        engine.load(spotifyWebApi.getURIString());


        // updates the title of the window
        engine.titleProperty().addListener((ov, oldValue, newValue) -> stage.setTitle(newValue));
        // checks when the redirect uri is visited
        engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {onRedirectURIOpen(newState,oldState);});
        // sets the scene to the webpage and shows it
        soundApplicationMain.setStage(stage);
        soundApplicationMain.getNewStage().setScene(scene);
        soundApplicationMain.showStage();




    }
    private void onRedirectURIOpen(State newState,State oldState) {
        if (newState == State.SUCCEEDED && spotifyWebApi.matchesRedirectURIPage(engine.getLocation())) {
            // when the redirect website is visited it fetches the code from the url and sets the code
            spotifyWebApi.setCodeString(engine.getLocation());
            spotifyWebApi.getAccessToken();
            System.out.println("called");
            engine.load(null);
            stage.close();

        }
    }
    private void openMusicPlayer() throws IOException {

    }

}
