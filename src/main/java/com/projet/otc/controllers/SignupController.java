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

public class SignupController implements Initializable {


    @FXML
    private Pane bg2;

    @FXML
    private PasswordField confirmPasswordfield;

    @FXML
    private HBox header;

    @FXML
    private Label headertext;

    @FXML
    private Pane imagebg;

    @FXML
    private Label noment;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;
    private static String ClientName=null;
    private final SceneMethod editor = new SceneMethod();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signUpButton.setOnMouseClicked(e->{
            try {
                signUp();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private void signUp() throws IOException {
        String un = usernameField.getText().trim();
        String pw = passwordField.getText();
        String c_pw = confirmPasswordfield.getText();
        if (un.isBlank() || pw.isBlank() || c_pw.isBlank()) {
            SceneMethod.alertErrorWindow();
        } else {
            if (pw.equals(c_pw)) {
                int res = ClientDAO.saveClient(un, pw);
                switch (res) {
                    case 1:
                        ClientName = SceneMethod.capitalizeFirstLetter(un);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Connected Successfully");
                        alert.setContentText("Welcome, you will be redirected to the main tab!");
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                        alert.showAndWait();
                        Stage currStage  = (Stage)passwordField.getScene().getWindow();
                        editor.PopMain();
                        currStage.close();
                        break;
                    case -1:
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText("Connection Failed!");
                        alert2.setContentText("Couldn't register the Account!\nThis account alreadty exists");
                        Stage alertStage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                        alertStage2.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                        alert2.showAndWait();
                        passwordField.clear();
                        confirmPasswordfield.clear();
                        usernameField.clear();
                        break;
                    case -2:
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setTitle("Error");
                        alert3.setHeaderText("Connection Failed!");
                        alert3.setContentText("An unknown error has occured please try again!");
                        Stage alertStage3 = (Stage) alert3.getDialogPane().getScene().getWindow();
                        alertStage3.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                        alert3.showAndWait();
                        passwordField.clear();
                        confirmPasswordfield.clear();
                        usernameField.clear();
                        break;
                    default:
                        break;

                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect Password");
                alert.setContentText("Please check your password and make sure both are identical");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(getClass().getResource("/com/projet/otc/images/icon.png").toExternalForm()));
                alert.showAndWait();
                passwordField.clear();
                confirmPasswordfield.clear();


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
