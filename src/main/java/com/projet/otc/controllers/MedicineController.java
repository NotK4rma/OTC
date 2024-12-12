package com.projet.otc.controllers;

import com.projet.otc.DataManagement.MedicationDAO;
import com.projet.otc.pharmacie.Medicament;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MedicineController implements Initializable {
    @FXML
    private Button b_search;

    @FXML
    private HBox header_meds;

    @FXML
    private TextField searchField;

    @FXML
    private FlowPane container;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnAction(e->{
            displayMedicinesFromSearch(container);
        });

        b_search.setOnMouseClicked(e->{
            displayMedicinesFromSearch(container);
        });

        Platform.runLater(() -> {
            displayMedicines(container);
        });



    }


    private VBox createMedicineCard(Medicament medicine) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setPrefSize(150, 220);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #fff; -fx-border-color: #27ae60; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ImageView imageView = new ImageView(new Image(getClass().getResource(medicine.getImageUrl()).toExternalForm()));
        System.out.println(medicine.getImageUrl());
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Text nameText = new Text(medicine.getName());
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameText.setFill(Color.web("#2d3436"));

        Text priceText = new Text("$" + String.format("%.2f", medicine.getPrice()));
        priceText.setFont(Font.font("Arial", 12));
        priceText.setFill(Color.web("#27ae60"));

        card.getChildren().addAll(imageView, nameText, priceText);
        card.setOnMouseEntered(e->hoverInAnimation(card));
        card.setOnMouseExited(e->hoverOutAnimation(card));
        addHoverShadowEffect(card);
        card.setCursor(Cursor.HAND);
        card.setOnMouseEntered(e->card.setStyle("-fx-border-width : 2px;-fx-background-color: #fff; -fx-border-color: #27ae60; -fx-border-radius: 10px; -fx-background-radius: 10px;"));
        card.setOnMouseExited(e->card.setStyle("-fx-border-width : 1px;-fx-background-color: #fff; -fx-border-color: #27ae60; -fx-border-radius: 10px; -fx-background-radius: 10px;"));
        return card;
    }

    private void displayMedicines(FlowPane container) {
        container.getChildren().clear();
        List<Medicament> Lmed = MedicationDAO.getMeds();

        for (Medicament med : Lmed) {
            VBox card = createMedicineCard(med);
            container.getChildren().add(card);
        }
    }


    private void displayMedicinesFromSearch(FlowPane container) {
        container.getChildren().clear();

        List<Medicament> Lmed = SearchList();

        for (Medicament med : Lmed) {
            VBox card = createMedicineCard(med);
            container.getChildren().add(card);
        }
    }

    private List<Medicament> SearchList(){
        String search = searchField.getText().trim();
        return MedicationDAO.SearchMeds(search);

    }

    private void hoverInAnimation(Pane p){
        ScaleTransition up = new ScaleTransition();
        up.setDuration(Duration.millis(300));
        up.setNode(p);
        up.setToX(1.05);
        up.setToY(1.05);
        up.play();

    }
    private void hoverOutAnimation(Pane p){
        ScaleTransition down = new ScaleTransition();
        down.setDuration(Duration.millis(300));
        down.setNode(p);
        down.setToX(1);
        down.setToY(1);
        down.play();

    }


    public static void addHoverShadowEffect(Pane pane) {
        // Create a DropShadow effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(0); // Start with no shadow
        shadow.setColor(Color.GRAY);
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);

        // Apply the shadow effect to the Pane
        pane.setEffect(shadow);

        // Create Timeline for hover-in animation
        Timeline hoverIn = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(shadow.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(shadow.radiusProperty(), 40))
        );

        // Create Timeline for hover-out animation
        Timeline hoverOut = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(shadow.radiusProperty(), 40)),
                new KeyFrame(Duration.millis(200), new KeyValue(shadow.radiusProperty(), 0))
        );

        // Add event listeners for hover effects
        pane.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> hoverIn.play());
        pane.addEventHandler(MouseEvent.MOUSE_EXITED, event -> hoverOut.play());
    }




}
