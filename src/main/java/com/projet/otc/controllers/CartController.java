package com.projet.otc.controllers;

import com.projet.otc.DataManagement.MedicationDAO;
import com.projet.otc.MiscTools.SceneMethod;
import com.projet.otc.pharmacie.Medicament;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    @FXML
    private Button b_connect;

    @FXML
    private Button b_connect_2;

    @FXML
    private Button b_logout;

    @FXML
    private Button b_meds;

    @FXML
    private Button b_phar;

    @FXML
    private Button b_search;

    @FXML
    private VBox connectedInt;

    @FXML
    private HBox header;

    @FXML
    private AnchorPane hide;

    @FXML
    private Label l_nomlib;

    @FXML
    private Label l_notconn1;

    @FXML
    private Label l_notconn1_2;

    @FXML
    private Label l_notconn2;

    @FXML
    private Label l_notconn2_2;

    @FXML
    private FlowPane medsContainer;

    @FXML
    private ImageView menuclose;

    @FXML
    private ImageView menuopen;

    @FXML
    private VBox noConnContainer;

    @FXML
    private VBox notconnectedInt;

    @FXML
    private ScrollPane scrollContainer;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane slider;


    private final SceneMethod editor = new SceneMethod();
    private String NomcClient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NomcClient=LoginController.getNameClient();

        if(NomcClient==null){
            NomcClient=SignupController.getNameClient();
        }

        if(NomcClient==null){
            notconnectedInt.setVisible(true);
            connectedInt.setVisible(false);
            scrollContainer.setVisible(false);
            noConnContainer.setVisible(true);
            b_logout.setVisible(false);

        }else{
            notconnectedInt.setVisible(false);
            connectedInt.setVisible(true);
            scrollContainer.setVisible(true);
            noConnContainer.setVisible(false);
            l_nomlib.setText("Welcome, "+NomcClient+"!");
            b_logout.setVisible(true);


        }


        l_notconn2_2.setWrapText(true);
        l_notconn1_2.setWrapText(true);
        l_notconn1.setWrapText(true);
        l_notconn2.setWrapText(true);

        menuopen.setOnMouseClicked(e-> SceneMethod.translationOpen(slider,hide,menuopen,menuclose));
        menuclose.setOnMouseClicked(e-> SceneMethod.translationClose(slider,hide,menuopen,menuclose));


        hide.setOnDragDetected(e-> SceneMethod.translationClose(slider,hide,menuopen,menuclose));

        b_logout.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_logout);
            SceneMethod.addHoverShadowEffect(b_logout);

        });

        b_meds.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_meds);
            SceneMethod.addHoverShadowEffect(b_meds);

        });


        b_connect.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_connect);
            SceneMethod.addHoverShadowEffect(b_connect);

        });


        b_phar.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_phar);
            SceneMethod.addHoverShadowEffect(b_phar);

        });

        b_logout.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_logout));
        b_phar.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_phar));
        b_meds.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_meds));
        b_connect.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_connect));

        b_logout.setOnMouseClicked(e->{
            b_logout.setStyle("-fx-border-color : white white white white ; -fx-border-size : 1px; -fx-text-fill : #EDF2F7; -fx-font-size : 18px; -fx-font-family: Franklin Gothic Demi; -fx-font-weight: 900; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #1B7A46; -fx-font-size: 14px;-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color : white ;");
            SceneMethod.SelectAnimation(b_logout);

        });




        b_phar.setOnMouseClicked(e->{
            b_phar.setStyle("-fx-border-color : white white white white ; -fx-border-size : 1px; -fx-text-fill : #EDF2F7; -fx-font-size : 18px; -fx-font-family: Franklin Gothic Demi; -fx-font-weight: 900; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #1B7A46; -fx-font-size: 14px;");
            SceneMethod.SelectAnimation(b_phar);
            try {
                System.out.println(getClass().getResource("../Styles/style1.cssl"));
                editor.switchScene((Stage)b_phar.getScene().getWindow(),"/com/projet/otc/TEST3.fxml","/com/projet/otc/Styles/style1.css");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



        b_meds.setOnMouseClicked(e->{
            b_meds.setStyle("-fx-border-color : white white white white ; -fx-border-size : 1px; -fx-text-fill : #EDF2F7; -fx-font-size : 18px; -fx-font-family: Franklin Gothic Demi; -fx-font-weight: 900; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #1B7A46; -fx-font-size: 14px;");
            SceneMethod.SelectAnimation(b_meds);
            try {
                System.out.println(getClass().getResource("../Styles/style1.cssl"));
                editor.switchScene((Stage)b_meds.getScene().getWindow(),"/com/projet/otc/testmeds.fxml","/com/projet/otc/Styles/style1.css");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });


        b_connect_2.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_connect_2);
            SceneMethod.addHoverShadowEffect(b_connect_2);
        });

        b_connect_2.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_connect_2));


        if (scrollContainer.isVisible()) {
            displayMedicines(medsContainer);
        }

        b_search.setOnMouseClicked(e->{
            if (scrollContainer.isVisible()) {
                displayMedicinesFromSearch(medsContainer);
            }
        });

        searchField.setOnAction(e->{
            if (scrollContainer.isVisible()) {
                displayMedicinesFromSearch(medsContainer);
            }
        });

        scrollContainer.viewportBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            medsContainer.setPrefHeight(newBounds.getHeight());
        });


        b_connect.setOnMouseClicked(e-> {
            try {

                Stage CurrStage = (Stage)b_connect.getScene().getWindow();
                editor.PopSignIn();
                CurrStage.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        b_logout.setOnMouseClicked(e-> {
            try {
                LoginController.revokeClientName();
                SignupController.revokeClientName();
                Stage CurrStage = (Stage)b_connect.getScene().getWindow();
                editor.PopMain();
                CurrStage.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        b_connect_2.setOnMouseClicked(e-> {
            try {
                Stage CurrStage = (Stage)b_connect.getScene().getWindow();
                editor.PopSignIn();
                CurrStage.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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

        StackPane smallStack= new StackPane();
        smallStack.setPrefHeight(20);
        smallStack.setPrefWidth(20);
        smallStack.setMaxWidth(20);
        smallStack.setMaxHeight(20);
        smallStack.setStyle("-fx-border-radius: 5px;-fx-background-radius: 5px;");

        ImageView plus = new ImageView(new Image(getClass().getResource("/com/projet/otc/images/minus.png").toExternalForm()));
        plus.setFitHeight(10);
        plus.setFitWidth(10);
        plus.setOpacity(0.2);

        Pane background = new Pane();
        background.setPrefHeight(10);
        background.setPrefWidth(10);
        background.setStyle("-fx-background-color : #961c26");
        background.setOpacity(0.13);

        smallStack.getChildren().addAll(background,plus);

        smallStack.setOpacity(0);


        StackPane bigStack = new StackPane();
        bigStack.setPrefWidth(100);
        bigStack.setPrefHeight(100);
        bigStack.getChildren().addAll(imageView,smallStack);
        smallStack.setTranslateX(48);
        smallStack.setTranslateY(40);

        bigStack.setStyle("-fx-border-width : 1px;-fx-border-color: black;-fx-border-style : solid;-fx-border-radius: 5px; -fx-padding: 5px 0 5px 0; -fx-margin : 5px 0 0 0;");



        Text nameText = new Text(medicine.getName());
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameText.setFill(Color.web("#2d3436"));

        Text priceText = new Text( String.format("%.2f", medicine.getPrice())+"Dt");
        priceText.setFont(Font.font("Arial", 12));
        priceText.setFill(Color.web("#27ae60"));

        card.getChildren().addAll(bigStack, nameText, priceText);
        card.setId(String.valueOf(medicine.getId()));
        card.setOnMouseEntered(e->MedicineController.hoverInAnimation(card));
        card.setOnMouseExited(e->MedicineController.hoverOutAnimation(card));
        MedicineController.addHoverShadowEffect(card);
        card.setCursor(Cursor.HAND);
        card.setOnMouseEntered(e->{

            card.setStyle("-fx-border-width : 2px;-fx-background-color: #fff; -fx-border-color: #27ae60; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            smallStack.setVisible(true);
            SceneMethod.FadeStackAnimation(smallStack,0,1,300);
            smallStack.setOnMouseEntered(e2->{
                SceneMethod.FadeStackAnimation(plus,0.2,0.4,200);
                SceneMethod.FadeStackAnimation(background,0.13,0.43,200);
            });
        });


        card.setOnMouseExited(e->{

            card.setStyle("-fx-border-width : 1px;-fx-background-color: #fff; -fx-border-color: #27ae60; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            SceneMethod.FadeStackAnimation(smallStack,1,0,300);
            smallStack.setOnMouseExited(e2->{
                SceneMethod.FadeStackAnimation(plus,0.4,0.2,200);
                SceneMethod.FadeStackAnimation(background,0.43,0.13,200);
            });

        });

        smallStack.setOnMouseClicked(e->{
            int res = MedicationDAO.deleteCommande(NomcClient,medicine.getId());

            if(res==1){
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("Success");
                alert3.setHeaderText("Medication Removed successfully ");
                alert3.setContentText("The medication has been successfully removed from your cart!");
                Stage alertStage3 = (Stage)alert3.getDialogPane().getScene().getWindow();
                alertStage3.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                alert3.showAndWait();
                displayMedicines(medsContainer);
            }
            else{
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("Couldn't remove the medication from the cart");
                alert2.setContentText("An unknown Error has occured! Please try again!");
                Stage alertStage2 = (Stage)alert2.getDialogPane().getScene().getWindow();
                alertStage2.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                alert2.showAndWait();
            }
        });

        return card;
    }



    private void displayMedicines(FlowPane container) {
        container.getChildren().clear();
        List<Medicament> Lmed = MedicationDAO.getClientMeds(NomcClient);

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
        return MedicationDAO.getClientMedsRecherche(NomcClient,search);

    }




}
