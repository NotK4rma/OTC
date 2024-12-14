package com.projet.otc.MiscTools;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SceneMethod {

    public void switchScene(Stage stage, String url, String css)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene= new Scene(root);
        scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void translationClose(AnchorPane p, AnchorPane rect, ImageView open, ImageView close){
        TranslateTransition slide = new TranslateTransition();
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setDuration(Duration.millis(300));
        fadeOut.setNode(rect);
        fadeOut.setFromValue(0.8);
        slide.setDuration(Duration.millis(300));
        slide.setNode(p);
        slide.setToX(-282);
        fadeOut.setToValue(0);
        TranslateTransition slideRect = new TranslateTransition();
       /* slideRect.setNode(rect);
        slideRect.setDuration(Duration.millis(300));
        slideRect.setToX(3840);*/


        slide.play();
        //slideRect.play();
        fadeOut.play();
        p.setTranslateX(0);
        //rect.setTranslateX(0);
        slide.setOnFinished(e->{
            open.setVisible(true);
            close.setVisible(false);
            rect.setVisible(false);
            p.setVisible(false);

        });

    }

    public static void translationOpen(AnchorPane p, AnchorPane rect, ImageView open, ImageView close){
        rect.setVisible(true);
        p.setVisible(true);

        TranslateTransition slide = new TranslateTransition();
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.millis(300));
        fadeIn.setNode(rect);
        fadeIn.setFromValue(0);

        slide.setDuration(Duration.millis(300));
        slide.setNode(p);
        slide.setToX(0);
        fadeIn.setToValue(0.8);

        /*TranslateTransition slideRect = new TranslateTransition();
        slideRect.setNode(rect);
        slideRect.setDuration(Duration.millis(300));
        slideRect.setToX(3840);*/

        fadeIn.play();
        slide.play();
        //rect.setTranslateX(3840);
        //slideRect.play();
        p.setTranslateX(-282);
        slide.setOnFinished(e->{
            open.setVisible(false);
            close.setVisible(true);
        });

    }



    public static void hoverInAnimation(Button b){
        ScaleTransition up = new ScaleTransition();
        up.setDuration(Duration.millis(300));
        up.setNode(b);
        up.setToX(1.05);
        up.setToY(1.05);
        up.play();

    }
    public static void hoverOutAnimation(Button b){
        ScaleTransition down = new ScaleTransition();
        down.setDuration(Duration.millis(300));
        down.setNode(b);
        down.setToX(1);
        down.setToY(1);
        down.play();

    }


    public static void SelectAnimation(Button b){
        ScaleTransition down = new ScaleTransition();
        down.setDuration(Duration.millis(300));
        down.setNode(b);
        down.setToX(0.95);
        down.setToY(0.95);
        down.play();

    }

    public static void addHoverShadowEffect(Button b) {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(0);
        shadow.setColor(Color.GRAY);
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);

        b.setEffect(shadow);

        Timeline hoverIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(shadow.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(shadow.radiusProperty(), 40))
        );

        Timeline hoverOut = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(shadow.radiusProperty(), 40)),
                new KeyFrame(Duration.millis(200), new KeyValue(shadow.radiusProperty(), 0))
        );


        b.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> hoverIn.play());
        b.addEventHandler(MouseEvent.MOUSE_EXITED, event -> hoverOut.play());
    }

}
