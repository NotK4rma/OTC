package com.projet.otc.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;

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
        exitButton.setOnAction(event -> System.exit(0));
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

                // Add example marker
                L.marker([36.8065, 10.1815]).addTo(map).bindPopup("Default Location: Tunis").openPopup();
            </script>
        </body>
        </html>
    """;


        System.out.println("Map HTML content: " + mapHtml);
        mapWebView.getEngine().loadContent(mapHtml);
        mapWebView.getEngine().setOnError(event -> {
            System.out.println("WebView Error: " + event.getMessage());
        });
    }
}