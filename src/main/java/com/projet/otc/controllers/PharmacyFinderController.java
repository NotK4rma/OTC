package com.projet.otc.controllers;

import com.projet.otc.DataManagement.PharmacieDAO;
import com.projet.otc.pharmacie.Medicament;
import com.projet.otc.pharmacie.Pharmacie;
import com.projet.otc.pharmacie.Stock;
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
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;


import java.util.ArrayList;
import java.util.List;

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

    private List<Pharmacie> lphar = new ArrayList<>();

    private int Radius=300000;
    private final int Radius_init=300000;

    @FXML
    public void initialize() {

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



        loadMap();
        exitButton.setOnAction(event -> {

            lphar=markPharmaciesOnMap();

        });

        b_table.setOnMouseClicked(e->afficherListePharmaDisop(lphar));

        refreshButton.setOnAction(event -> {
            System.out.println("ok");
            loadMap();
        });

        Platform.runLater(() -> {
            rayonCirc.setText(String.valueOf(Radius));

            refresh.setOnMouseClicked(e->{loadMap() ;
                Radius=Radius_init;
                rayonCirc.setText(String.valueOf(Radius));

            });


        });

        b_up.setOnMouseClicked(e->{
            Radius+=3000000;
            mapWebView.getEngine().executeScript(String.format("changeRadius(%d)",Radius));
            rayonCirc.setText(String.valueOf(mapWebView.getEngine().executeScript("rayon")));

            //loadMap();
        });
        b_down.setOnMouseClicked(e->{
            Radius-=3000000;
            mapWebView.getEngine().executeScript(String.format("changeRadius(%d)",Radius));
            rayonCirc.setText(String.valueOf(mapWebView.getEngine().executeScript("rayon")));

        });


        tPharma.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherTableauMedicament(newValue.getId());

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
                        
                        let marker; 
                        let circle; 
                        let markerGroup = L.layerGroup().addTo(map);
                        var rayon = 300000;
                        function addNewMarker(lat, lon) {
                        if (marker) {
                            map.removeLayer(marker); 
                        }
                        marker = L.marker([lat, lon]).addTo(map).bindPopup("New Location").openPopup();
                        addCircle(lat,lon,rayon);
                        
                        }
                        
                        //**************Set default position
                        map.on('contextmenu', function (e) {
                            let lat = e.latlng.lat;
                            let lon = e.latlng.lng;
                            addNewMarker(lat, lon); 
                            markerGroup.clearLayers();
                        });
                        
                        function succes(pos){
                            const lat = pos.coords.latitude;
                            const lng = pos.coords.longitude;
                            const acc = pos.coords.accuracy;
                            marker=L.marker([lat, lng]).addTo(map).bindPopup("Extracted Position").openPopup();
                            addCircle(lat, lng, rayon)
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
                        }
                        
                        

                    </script>
        </body>
        </html>
    """;


       // System.out.println("Map HTML content: " + mapHtml);
        mapWebView.getEngine().loadContent(mapHtml);
        mapWebView.getEngine().setOnError(event -> {
            System.out.println("WebView Error: " + event.getMessage());
        });

    }

    private List<Pharmacie> markPharmaciesOnMap() {
        /*List<double[]> coordinates = List.of(
                new double[]{36.8065, 10.1815},
                new double[]{36.8165, 10.1915},
                new double[]{36.9165, 10.1915},
                new double[]{37.8165, 10.1715},
                new double[]{36.8165, 10.2915},
                new double[]{36.8265, 10.1925}

        );
        String name = "Pharmacie";*/

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


        /*mapWebView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) mapWebView.getEngine().executeScript("window");
                window.setMember("javaConnector", new JavaConnector());

                // Add your marker-adding logic here, for example:

            }
        });*/

        return LpharmaDispo;
    }

    private void afficherListePharmaDisop(List<Pharmacie> lphar){
        ObservableList<Pharmacie> LpharObs=FXCollections.observableArrayList(lphar);
        tPharma.setItems(LpharObs);
    }

    private void afficherTableauMedicament(int id){
        List<Stock> Lmed = PharmacieDAO.afficherStockPharmacie(id);
        ObservableList<Stock> ObsLmed = FXCollections.observableArrayList(Lmed);

        tMedic.setItems(ObsLmed);

    }



}