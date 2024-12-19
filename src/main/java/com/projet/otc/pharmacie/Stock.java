package com.projet.otc.pharmacie;

public class Stock {
    private int id;
    private String MedName;
    private String MedDesc;
    private Double prixMed;
    private int qte;
    private String nomPharma;


    public Stock(int id, String medName, String medDesc, Double prixMed, int qte) {
        this.id = id;
        MedName = medName;
        MedDesc = medDesc;
        this.prixMed = prixMed;
        this.qte = qte;
    }

    public Stock(String medName, String medDesc, Double prixMed, int qte) {
        MedName = medName;
        MedDesc = medDesc;
        this.prixMed = prixMed;
        this.qte = qte;
    }

    public Stock( Double prixMed, int qte,String nomPharma) {
        this.prixMed = prixMed;
        this.qte = qte;
        this.nomPharma=nomPharma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedName() {
        return MedName;
    }

    public void setMedName(String medName) {
        MedName = medName;
    }

    public String getMedDesc() {
        return MedDesc;
    }

    public void setMedDesc(String medDesc) {
        MedDesc = medDesc;
    }

    public Double getPrixMed() {
        return prixMed;
    }

    public void setPrixMed(Double prixMed) {
        this.prixMed = prixMed;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getNomPharma() {
        return nomPharma;
    }

    public void setNomPharma(String nomPharma) {
        this.nomPharma = nomPharma;
    }
}
