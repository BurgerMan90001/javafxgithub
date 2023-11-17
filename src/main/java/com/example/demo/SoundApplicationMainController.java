package com.example.demo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SoundApplicationMainController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO
        System.out.println("dsaasd");
        loadSpotiySignInPage();

    }



    public void loadSpotiySignInPage() {
        System.out.println("dsasd");
    }




}
    /*
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        loadSpotiySignInPage();

    }


    public void loadSpotiySignInPage() {


      //  AuthorizationCodePKCEUriExample authorizationCodePKCEUriExample = new AuthorizationCodePKCEUriExample();
        //   authorizationCodePKCEUriExample.main();
       // engine.load(authorizationCodePKCEUriExample.authorizationCodeUri_Sync());

    }

*/
