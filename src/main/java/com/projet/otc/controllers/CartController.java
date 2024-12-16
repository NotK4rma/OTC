package com.projet.otc.controllers;

import com.projet.otc.MiscTools.SceneMethod;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    @FXML
    private Button b_cart;

    @FXML
    private Button b_connect;

    @FXML
    private Button b_connect_2;

    @FXML
    private Button b_logout;

    @FXML
    private Button b_phar;

    @FXML
    private Button b_search;

    @FXML
    private VBox connectedInt;

    @FXML
    private FlowPane container;

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
    private ImageView menuclose;

    @FXML
    private ImageView menuopen;

    @FXML
    private VBox notconnectedInt;

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane slider;

    @FXML
    private Button b_meds;

    @FXML
    private VBox noConnContainer;


    private final SceneMethod editor = new SceneMethod();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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









    }




}
