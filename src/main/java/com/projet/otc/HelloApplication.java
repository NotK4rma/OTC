package com.projet.otc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("testcart.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("Styles/style1.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
        primaryStage.setTitle("Pharmacy Finder");
        primaryStage.show();

    }
}