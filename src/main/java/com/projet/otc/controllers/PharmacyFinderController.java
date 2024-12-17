package com.projet.otc.controllers;

import com.projet.otc.DataManagement.PharmacieDAO;
import com.projet.otc.MiscTools.SceneMethod;
import com.projet.otc.pharmacie.Medicament;
import com.projet.otc.pharmacie.Pharmacie;
import com.projet.otc.pharmacie.Stock;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PharmacyFinderController {

    @FXML
    private ListView<String> pharmacyList;

    @FXML
    private TableView<Pharmacie> tPharma;

    @FXML
    private TableView<Stock> tMedic;

    @FXML
    private TableColumn<Pharmacie, String> c_dist;

    @FXML
    private TableColumn<Stock, String> c_med;

    @FXML
    private TableColumn<Pharmacie, String> c_pharma;

    @FXML
    private TableColumn<Stock, String> c_prix;

    @FXML
    private WebView mapWebView;

    @FXML
    private Button refreshButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button b_table;

    @FXML
    private Button b_down;

    @FXML
    private Button b_up;

    @FXML
    private TableColumn<Stock, String> c_desc;

    @FXML
    private TableColumn<Stock, String> c_qte;

    @FXML
    private Label refresh;

    @FXML
    private Label rayonCirc;

    @FXML
    private Label l_notconn1;

    @FXML
    private Label l_notconn2;

    @FXML
    private AnchorPane hide;

    @FXML
    private AnchorPane slider;

    @FXML
    private ImageView menuclose;

    @FXML
    private ImageView menuopen;

    @FXML
    private Button b_cart;

    @FXML
    private Button b_connect;


    @FXML
    private Button b_logout;

    @FXML
    private Button b_meds;

    @FXML
    private VBox connectedInt;

    @FXML
    private VBox notconnectedInt;

    @FXML
    private Label l_nomlib;

    private List<Pharmacie> lphar = new ArrayList<>();

    private int Radius=300000;
    private final int Radius_init=300000;

    private static Timeline timeline;
    private boolean changed=false;
    private int durationForCheckPos=100;
    private SceneMethod editor = new SceneMethod();
    private String NomcClient;





    @FXML
    public void initialize() {

        NomcClient=LoginController.getNameClient();

        if(NomcClient==null){
            NomcClient=SignupController.getNameClient();
        }

        if(NomcClient==null){
            notconnectedInt.setVisible(true);
            connectedInt.setVisible(false);


        }else{
            notconnectedInt.setVisible(false);
            connectedInt.setVisible(true);
            l_nomlib.setText("Welcome, "+NomcClient+"!");

        }


        l_notconn1.setWrapText(true);
        l_notconn2.setWrapText(true);


        c_pharma.setCellValueFactory(data->
            new SimpleStringProperty(data.getValue().getName())
        );

        c_dist.setCellValueFactory(data->{
            String script = String.format("calculerDistance(%f, %f);",data.getValue().getLat(),data.getValue().getLng());
            return new SimpleStringProperty(String.valueOf(mapWebView.getEngine().executeScript(script)));
        });

        c_med.setCellValueFactory(new PropertyValueFactory<>("MedName"));

        c_prix.setCellValueFactory(new PropertyValueFactory<>("prixMed"));

        c_desc.setCellValueFactory(new PropertyValueFactory<>("MedDesc"));

        c_qte.setCellValueFactory(new PropertyValueFactory<>("qte"));


        menuopen.setOnMouseClicked(e-> SceneMethod.translationOpen(slider,hide,menuopen,menuclose));
        menuclose.setOnMouseClicked(e-> SceneMethod.translationClose(slider,hide,menuopen,menuclose));

        hide.setOnDragDetected(e-> SceneMethod.translationClose(slider,hide,menuopen,menuclose));

        b_logout.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_logout);
            SceneMethod.addHoverShadowEffect(b_logout);

        });

        b_cart.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_cart);
            SceneMethod.addHoverShadowEffect(b_cart);

        });


        b_connect.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_connect);
            SceneMethod.addHoverShadowEffect(b_connect);

        });


        b_meds.setOnMouseEntered(e->{
            SceneMethod.hoverInAnimation(b_meds);
            SceneMethod.addHoverShadowEffect(b_meds);

        });

        b_logout.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_logout));
        b_meds.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_meds));
        b_cart.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_cart));
        b_connect.setOnMouseExited(e->SceneMethod.hoverOutAnimation(b_connect));

        b_logout.setOnMouseClicked(e->{
            b_logout.setStyle("-fx-border-color : white white white white ; -fx-border-size : 1px; -fx-text-fill : #EDF2F7; -fx-font-size : 18px; -fx-font-family: Franklin Gothic Demi; -fx-font-weight: 900; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #1B7A46; -fx-font-size: 14px;-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-color : white ;");
            SceneMethod.SelectAnimation(b_logout);

        });

        b_cart.setOnMouseClicked(e->{
            b_cart.setStyle("-fx-border-color : white white white white ; -fx-border-size : 1px; -fx-text-fill : #EDF2F7; -fx-font-size : 18px; -fx-font-family: Franklin Gothic Demi; -fx-font-weight: 900; -fx-border-radius: 0; -fx-background-radius: 0; -fx-background-color: #1B7A46; -fx-font-size: 14px;");
            SceneMethod.SelectAnimation(b_cart);
            try {
                System.out.println(getClass().getResource("../Styles/style1.cssl"));
                editor.switchScene((Stage)b_cart.getScene().getWindow(),"/com/projet/otc/testcart.fxml","/com/projet/otc/Styles/style1.css");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                timeline.pause();
            } catch (NullPointerException ex) {
                Timeline StopInitTimeline = new Timeline(new KeyFrame(Duration.seconds(2),ev->{
                    timeline.pause();
                }));
                StopInitTimeline.setCycleCount(1);
                StopInitTimeline.play();
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
            try {
                timeline.pause();
            } catch (NullPointerException ex) {
                Timeline StopInitTimeline = new Timeline(new KeyFrame(Duration.seconds(2),ev->{
                    timeline.pause();
                }));
                StopInitTimeline.setCycleCount(1);
                StopInitTimeline.play();
                throw new RuntimeException(ex);
            }

        });






        loadMapAndExecuteChainedTasks();









        Timeline timelineFirstIteration = new Timeline(new KeyFrame(Duration.seconds(3),e->{
            timeline = new Timeline(new KeyFrame(Duration.millis(durationForCheckPos), event -> {
                System.out.println("Checking if position changed...");
                changed=checkMarkerPositionChange();
                System.out.println(changed);
                if (changed==true){
                    tPharma.getItems().clear();
                    tMedic.getItems().clear();
                    placeMarkersAndShowPharmacies();
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }));
        timelineFirstIteration.setCycleCount(1);
        timelineFirstIteration.play();






















        /*loadMap();

        exitButton.setOnAction(event -> {

            lphar=markPharmaciesOnMap();

        });

        b_table.setOnMouseClicked(e->afficherListePharmaDisop(lphar));

        refreshButton.setOnAction(event -> {
            System.out.println("ok");

        });*/

        Platform.runLater(() -> {

            rayonCirc.setText(String.valueOf(Radius));

            refresh.setOnMouseClicked(e->{
                System.out.println("hhhh");
                loadMapAndExecuteChainedTasks();
                Radius=Radius_init;
                rayonCirc.setText(String.valueOf(Radius));
                tPharma.getItems().clear();
                tMedic.getItems().clear();

            });


        });

        b_up.setOnMouseClicked(e->{
            Radius+=3000000;
            mapWebView.getEngine().executeScript(String.format("changeRadius(%d)",Radius));
            rayonCirc.setText(String.valueOf(mapWebView.getEngine().executeScript("rayon")));
            placeMarkersAndShowPharmacies();

        });
        b_down.setOnMouseClicked(e->{
            Radius-=3000000;
            mapWebView.getEngine().executeScript(String.format("changeRadius(%d)",Radius));
            rayonCirc.setText(String.valueOf(mapWebView.getEngine().executeScript("rayon")));
            placeMarkersAndShowPharmacies();

        });


        tPharma.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherTableauMedicament(newValue.getId());
                mapWebView.getEngine().executeScript(String.format("addRedMarkerSelectedPharmacy(%f,%f)", newValue.getLat(), newValue.getLng()));

            }
        });

        exitButton.setOnMouseClicked(e->System.exit(0));

        b_connect.setOnMouseClicked(e-> {
            try {

                Stage CurrStage = (Stage)b_connect.getScene().getWindow();
                editor.PopSignIn();
                CurrStage.close();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                timeline.pause();
            } catch (NullPointerException ex) {
                Timeline StopInitTimeline = new Timeline(new KeyFrame(Duration.seconds(2),ev->{
                    timeline.pause();
                }));
                StopInitTimeline.setCycleCount(1);
                StopInitTimeline.play();
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
            try {
                timeline.pause();
            } catch (NullPointerException ex) {
                Timeline StopInitTimeline = new Timeline(new KeyFrame(Duration.seconds(2),ev->{
                    timeline.pause();
                }));
                StopInitTimeline.setCycleCount(1);
                StopInitTimeline.play();
                throw new RuntimeException(ex);
            }
        });







    }

    private void loadMap() {
        String mapHtml = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <title>OpenStreetMap</title>
            <style>
                #map {
                    width: 100%;
                    height: 100vh;
                    margin: 0;
                    padding: 0;
                }
            </style>
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
            <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        </head>
        <body>
            <div id="map"></div>
                <script>
                        var map = L.map('map').setView([36.8065, 10.1815], 12); // Default: Tunis, Tunisia
                        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                            maxZoom: 19
                        }).addTo(map);
                        
                        navigator.geolocation.watchPosition(succes, error);
                        //*********Declarations
                        
                        let greenIcon = L.icon({
                            iconUrl: 'https://files.catbox.moe/0gmm4h.png', 
                            iconSize: [35, 41], // Width, Height
                            iconAnchor: [12, 41], // Position relative to the point
                            popupAnchor: [1, -34],
                            shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
                            shadowSize: [41, 41]
                        });
                        
                        let redIcon = L.icon({
                            iconUrl: 'https://files.catbox.moe/8hi4pg.png', 
                            iconSize: [35, 41], // Width, Height
                            iconAnchor: [12, 41], // Position relative to the point
                            popupAnchor: [1, -34],
                            shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
                            shadowSize: [41, 41]
                        });
                        
                        let marker; 
                        let circle; 
                        let markerGroup = L.layerGroup().addTo(map);
                        var rayon = 300000;
                        let coords_markerInit;
                        let name_lastPharm;
                        let selectedPharmMarker;  
                        
                        
                        function addNewMarker(lat, lon) {
                        if (marker) {
                            map.removeLayer(marker); 
                        }
                        marker = L.marker([lat, lon]).addTo(map).bindPopup("New Location").openPopup();
                        addCircle(lat,lon,rayon);
                        
                        }
                        
                       
                        map.on('contextmenu', function (e) {
                            let lat = e.latlng.lat;
                            let lon = e.latlng.lng;
                            addNewMarker(lat, lon); 
                            markerGroup.clearLayers();
                            if(selectedPharmMarker){
                                //addMarkerFromJava(selectedPharmMarker.getLatLng().lat, selectedPharmMarker.getLatLng().lng, name_lastPharm);
                                selectedPharmMarker.remove();
                            }
                        });
                        
                        function succes(pos){
                            const lat = pos.coords.latitude;
                            const lng = pos.coords.longitude;
                            const acc = pos.coords.accuracy;
                            marker=L.marker([lat, lng]).addTo(map).bindPopup("Extracted Position").openPopup();
                            addCircle(lat, lng, rayon)
                            coords_markerInit = marker.getLatLng();
                            
                        }

                        function error(err){
                            if(err.code === 1){
                                alert("Please Allow geolocation access");
                            }
                            else{
                                alert("Can not get current location");
                            }
                            console.log("erreur");
                            marker=L.marker([36.8065, 10.1815]).addTo(map).bindPopup("Default Location: Tunis").openPopup();
                            addCircle(36.8065, 10.1815, rayon);
                            coords_markerInit = marker.getLatLng();
                            
                        }
                        
                        
                        //**************************************Circle
                        
                        function addCircle(lat, lon, radius) {
                        if (circle) {
                            map.removeLayer(circle); // Remove the previous circle if it exists
                        }
                        circle = L.circle([lat, lon], {
                            radius: radius,  
                            color: 'blue',    
                            fillColor: '#10f',
                            fillOpacity: 0.1
                            }).addTo(map);
                        }

                        //********************Add marker
                        function addMarkerFromJava(lat, lon, name) {
                                        console.log("Adding marker at", lat, lon);
                                        markerGroup.addLayer(L.marker([lat, lon], { icon: greenIcon }).bindPopup(name));
                        }
                        
                        function addRedMarkerSelectedPharmacy(lat,lng,name){
                            if(selectedPharmMarker){
                                addMarkerFromJava(selectedPharmMarker.getLatLng().lat, selectedPharmMarker.getLatLng().lng, name_lastPharm);
                                selectedPharmMarker.remove();
                            }
                            name_lastPharm=name;
                            selectedPharmMarker = L.marker([lat, lng], { icon: redIcon }).bindPopup("Here").addTo(map);
                            removeMarkerByCoordinatesFromLayer(markerGroup,lat,lng);

                        }
                        
                        function removeMarkerByCoordinatesFromLayer(layerGroup, lat, lng) {
                            layerGroup.eachLayer(function (layer) {
                                if (layer.getLatLng) { // Ensure the layer is a marker or has LatLng
                                                const markerLatLng = layer.getLatLng();
                                    if (markerLatLng.lat === lat && markerLatLng.lng === lng) {
                                         layerGroup.removeLayer(layer); // Remove the marker
                                         console.log("Marker removed at:", lat, lng);
                                    }
                                }
                            });
                        }
                
                        
                        function isCoordinateInsideCircle(lat, lon) {
                            if (circle) {
                                const distance = map.distance(circle.getLatLng(), L.latLng(lat, lon));
                                return distance <= circle.getRadius(); 
                            }
                            return false;
                        }
                        
                        function calculerDistance(lat,lng){
                            let posPharmacie = L.latLng(lat,lng);
                            return (posPharmacie.distanceTo(marker.getLatLng())/1000).toFixed(2);
                        }
                        
                        function changeRadius(newRay){
                            rayon=newRay;
                            circle.setRadius(rayon);
                            markerGroup.clearLayers();
                            if(selectedPharmMarker){
                                if(!(isCoordinateInsideCircle(selectedPharmMarker.getLatLng().lat, selectedPharmMarker.getLatLng().lng))){
                                    selectedPharmMarker.remove();
                                }
                            }
                        }
                        
                        function MarkerChanged(){
                            if(marker.getLatLng().lat != coords_markerInit.lat || marker.getLatLng().lng != coords_markerInit.lng){
                                coords_markerInit=marker.getLatLng();
                                return true;
                             
                            }
                            else{
                                return false;
                            }
                        }
                        

                        
                        

                    </script>
        </body>
        </html>
    """;


        mapWebView.getEngine().loadContent(mapHtml);
        System.out.println("laoded");
        mapWebView.getEngine().setOnError(event -> {
            System.out.println("WebView Error: " + event.getMessage());
        });

    }

    private List<Pharmacie> markPharmaciesOnMap() {

        List<Pharmacie> Lpharma = PharmacieDAO.afficherPharmacie();
        List<Pharmacie> LpharmaDispo = new ArrayList<>();

        for (Pharmacie phar  : Lpharma) {
            String script1 = String.format("isCoordinateInsideCircle(%f, %f);", phar.getLat(), phar.getLng());
            boolean inside = (boolean) mapWebView.getEngine().executeScript(script1);
            if(inside){
                LpharmaDispo.add(new Pharmacie(phar.getId(),phar.getLng(),phar.getLat(),phar.getName()));
                String script = String.format("addMarkerFromJava(%f, %f, '%s');", phar.getLat(), phar.getLng(), phar.getName());
                mapWebView.getEngine().executeScript(script);
            }
        }


        System.out.println("finished marking");

        return LpharmaDispo;
    }

    private void afficherListePharmaDisop(List<Pharmacie> lphar){
        ObservableList<Pharmacie> LpharObs=FXCollections.observableArrayList(lphar);
        System.out.println("affihcer dans table");

        tPharma.setItems(LpharObs);
    }

    private void afficherTableauMedicament(int id){
        List<Stock> Lmed = PharmacieDAO.afficherStockPharmacie(id);
        ObservableList<Stock> ObsLmed = FXCollections.observableArrayList(Lmed);

        tMedic.setItems(ObsLmed);

    }



    private void loadMapAndExecuteChainedTasks() {
        Platform.runLater(() -> {
            System.out.println("Running loadMap()");

            loadMap();

            mapWebView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                    System.out.println("WebView finished loading!");

                    Platform.runLater(() -> {
                        System.out.println("Running markPharmaciesOnMap()");

                        lphar = markPharmaciesOnMap();

                        Platform.runLater(() -> {
                            System.out.println("Running afficherListePharmaDisop()");
                            afficherListePharmaDisop(lphar);
                        });
                    });
                }
            });
        });
    }

    private void placeMarkersAndShowPharmacies(){


                Platform.runLater(() -> {
                    System.out.println("Running markPharmaciesOnMap()");

                    lphar = markPharmaciesOnMap();

                    Platform.runLater(() -> {
                        System.out.println("Running afficherListePharmaDisop()");
                        afficherListePharmaDisop(lphar);
                    });
                });

    }

    private boolean checkMarkerPositionChange(){
        return (boolean)mapWebView.getEngine().executeScript("MarkerChanged()");

    }

    public static void resumeTimeline(){
        timeline.play();
    }



}