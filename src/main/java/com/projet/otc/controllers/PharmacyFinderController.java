package com.projet.otc.controllers;

import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;


import java.util.List;

public class PharmacyFinderController {

    @FXML
    private ListView<String> pharmacyList;

    @FXML
    private ListView<String> medicineList;

    @FXML
    private WebView mapWebView;

    @FXML
    private Button refreshButton;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        // Load the OpenStreetMap HTML in the WebView
        loadMap();

        // Populate example data
        pharmacyList.getItems().addAll("Pharmacy 1", "Pharmacy 2", "Pharmacy 3");

        // Set actions
        refreshButton.setOnAction(event -> {
            System.out.println("ok");
            loadMap();
        });
        exitButton.setOnAction(event -> markPharmaciesOnMap());
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
                        let marker; // To store the current marker

                        function addNewMarker(lat, lon) {
                        if (marker) {
                            map.removeLayer(marker); 
                        }
                        marker = L.marker([lat, lon]).addTo(map).bindPopup("New Location").openPopup();
                        }
                        
                        
                        map.on('contextmenu', function (e) {
                            let lat = e.latlng.lat;
                            let lon = e.latlng.lng;
                            addNewMarker(lat, lon); // Add a new marker at the clicked location
                

                        });
                        
                        function succes(pos){
                            const lat = pos.coords.latitude;
                            const lng = pos.coords.longitude;
                            const acc = pos.coords.accuracy;
                            marker=L.marker([lat, lng]).addTo(map).bindPopup("Extracted Position").openPopup();
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
                        }


                        function addMarkerFromJava(lat, lon, name) {
                                        console.log("Adding marker at", lat, lon);
                                        L.marker([lat, lon]).addTo(map).bindPopup(name).openPopup();
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

    private void markPharmaciesOnMap() {
        List<double[]> coordinates = List.of(
                new double[]{36.8065, 10.1815},
                new double[]{36.8165, 10.1915},                                     //get from db
                new double[]{36.8265, 10.1925}

        );
        String name = "Pharmacie";

        for (double[] coord : coordinates) {
            String script = String.format("addMarkerFromJava(%f, %f, '%s');", coord[0], coord[1], name);
            mapWebView.getEngine().executeScript(script);
        }


        /*mapWebView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) mapWebView.getEngine().executeScript("window");
                window.setMember("javaConnector", new JavaConnector());

                // Add your marker-adding logic here, for example:

            }
        });*/


    }

}