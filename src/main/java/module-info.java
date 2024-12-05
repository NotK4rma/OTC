module com.projet.otc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires jdk.jsobject;

    opens com.projet.otc to javafx.fxml;
    opens com.projet.otc.controllers to javafx.fxml;
    exports com.projet.otc;
    exports com.projet.otc.controllers;
}