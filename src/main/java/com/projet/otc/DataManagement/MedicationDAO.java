package com.projet.otc.DataManagement;

import com.projet.otc.pharmacie.Medicament;
import com.projet.otc.pharmacie.Pharmacie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO {

    public static List<Medicament> getMeds(){
        List<Medicament> Lmed= new ArrayList();
        String query = "select * from medications";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Medicament med = new Medicament(rs.getString("name"),
                        rs.getString("image")
                );
                med.setPrice(getAveragePriceMed(rs.getInt("id")));
                Lmed.add(med);
            }


        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("oops");
        }
        System.out.println("database succes" + Lmed.size());
        return Lmed;

    }



    private static double getAveragePriceMed(int id){
        String query = "select avg(price) as average from stock where medication_id = ?";
        double avgPrice = 0;
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
            ){
            stmt.setInt(1,id);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    avgPrice=rs.getDouble("average");
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return avgPrice;
    }


    public static List<Medicament> SearchMeds(String nom){
        List<Medicament> Lmed= new ArrayList();
        String query = "select * from medications where name like ?";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setString(1,"%"+nom+"%");
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Medicament med = new Medicament(rs.getString("name"),
                            rs.getString("image")
                    );
                    med.setPrice(getAveragePriceMed(rs.getInt("id")));
                    Lmed.add(med);
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("oops");
        }
        System.out.println("database succes");
        return Lmed;

    }



}
