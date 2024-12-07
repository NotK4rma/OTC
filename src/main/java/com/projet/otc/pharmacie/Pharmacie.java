package com.projet.otc.pharmacie;

public class Pharmacie {
    private int id;
    private String name;
    private double lat;
    private double lng;


    public Pharmacie(double lng, double lat, String name) {
        this.lng = lng;
        this.lat = lat;
        this.name = name;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
