package com.projet.otc.pharmacie;

public class Medicament {
    private int id;
    private String name;
    private String desc;
    private String ImageUrl;
    private double price;



    public Medicament(String name, String desc,String ImageUrl) {
        this.name = name;
        this.desc = desc;
        this.ImageUrl=ImageUrl;
    }

    public Medicament(String name,String ImageUrl) {
        this.name = name;
        this.ImageUrl=ImageUrl;
    }

    public Medicament(int id,String name, String desc,String ImageUrl){
        this.id=id;
        this.name = name;
        this.desc = desc;
        this.ImageUrl=ImageUrl;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
