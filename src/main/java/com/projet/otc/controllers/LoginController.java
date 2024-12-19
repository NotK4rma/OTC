package com.projet.otc.controllers;

import com.projet.otc.DataManagement.ClientDAO;
import com.projet.otc.MiscTools.SceneMethod;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Pane bg2;

    @FXML
    private HBox header;

    @FXML
    private Label headertext;

    @FXML
    private Hyperlink hlink;

    @FXML
    private Pane imagebg;

    @FXML
    private Label noment;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Label suptext;

    @FXML
    private TextField usernameField;

    private static String ClientName=null;

    private final SceneMethod editor = new SceneMethod();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        hlink.setOnMouseClicked(e->{
            try {
                editor.switchScene((Stage)signUpButton.getScene().getWindow(),"/com/projet/otc/signup.fxml", "/com/projet/otc/Styles/signStyles.css");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        signUpButton.setOnMouseClicked(e-> {
            try {
                logIn();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        usernameField.setOnAction(e-> {
            try {
                logIn();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        passwordField.setOnAction(e-> {
            try {
                logIn();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });







    }


    private void logIn() throws IOException {
        String un = usernameField.getText().trim();
        String pw = passwordField.getText();
        if(un.isBlank() || pw.isBlank() ){
            editor.alertErrorWindow();
        }
        else {
            boolean res = ClientDAO.getClient(un,pw);
            if (!res){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText("Connection failed!");
                alert.setContentText("An error occured while trying to connect!\nCheck that all provided information is correct");
                Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                alert.showAndWait();
                passwordField.clear();
                usernameField.clear();

            }
            else{
                ClientName=SceneMethod.capitalizeFirstLetter(un);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Successfully Connected!");
                alert.setContentText("Connection established with success!\nYou will be redirected to the Main tab.");
                Stage alertStage = (Stage)alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                alert.showAndWait();
                Stage currStage  = (Stage)passwordField.getScene().getWindow();
                editor.PopMain();
                currStage.close();

            }

        }


    }

    public static String getNameClient () {
        return ClientName;
    }

    public static void revokeClientName(){
        ClientName=null;
    }





}
