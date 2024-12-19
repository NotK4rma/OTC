package com.projet.otc.controllers;

import com.projet.otc.DataManagement.MedicationDAO;
import com.projet.otc.MiscTools.SceneMethod;
import com.projet.otc.pharmacie.Medicament;
import com.projet.otc.pharmacie.Stock;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MedsInfoController implements Initializable {
    @FXML
    private TableColumn<Stock, String> c_dispo;

    @FXML
    private TableColumn<Stock, String> c_pharma;

    @FXML
    private TableColumn<Stock, String> c_prix;

    @FXML
    private TableColumn<Stock, String> c_qte;

    @FXML
    private HBox header;

    @FXML
    private Label l_desc;

    @FXML
    private Label l_nom;

    @FXML
    private ImageView picmed;

    @FXML
    private ImageView returnArrow;

    @FXML
    private TableView<Stock> t_meds;

    private Medicament med;
    private SceneMethod editor = new SceneMethod();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        med  = MedicineController.getMedication();

        l_nom.setWrapText(true);
        l_desc.setWrapText(true);

        c_pharma.setCellValueFactory(data->
            new SimpleStringProperty(SceneMethod.capitalizeFirstLetter(data.getValue().getNomPharma()))
        );

        c_prix.setCellValueFactory(new PropertyValueFactory<>("prixMed"));

        c_dispo.setCellValueFactory(data->
                new SimpleStringProperty(data.getValue().getQte()>0?"\u2714 Disponible":"\u274C Indisponible")
        );

        c_qte.setCellValueFactory(new PropertyValueFactory<>("qte"));

        c_dispo.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.contains("Disponible")) {
                        setStyle("-fx-text-fill: green;");
                    } else if (item.contains("Indisponible")) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });


        if(med!=null){
            fillAvailableTable(med.getId());
            picmed.setImage(new Image(getClass().getResource(med.getImageUrl()).toExternalForm()));
            l_desc.setText(med.getDesc());
            l_nom.setText(med.getName());

        }

        returnArrow.setOnMouseClicked(e->{
            med=null;
            try {
                editor.switchScene((Stage) returnArrow.getScene().getWindow(),"/com/projet/otc/testmeds.fxml","/com/projet/otc/Styles/style1.css");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });




    }

    private void fillAvailableTable(int id){
        List<Stock> Lstock = MedicationDAO.getInfoMedicine(id);
        ObservableList<Stock> ObsStock = FXCollections.observableArrayList(Lstock);
        t_meds.getItems().addAll(ObsStock);

    }


}
