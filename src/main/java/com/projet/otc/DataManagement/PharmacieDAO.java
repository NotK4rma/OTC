package com.projet.otc.DataManagement;

import com.projet.otc.pharmacie.Medicament;
import com.projet.otc.pharmacie.Pharmacie;
import com.projet.otc.pharmacie.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PharmacieDAO {

    public static List<Pharmacie> afficherPharmacie(){
        List<Pharmacie> Lphar= new ArrayList();
        String query = "select * from pharmacies";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Pharmacie pha = new Pharmacie(rs.getInt("id"),
                        rs.getDouble("longitude"),
                        rs.getDouble("latitude"),
                        rs.getString("name")
                );

                Lphar.add(pha);
                System.out.println("ajout +1");
            }


        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("oops");
        }
        return Lphar;

    }


    public static List<Stock> afficherStockPharmacie(int id){
        String query="select * from medications inner join stock on medications.id=stock.medication_id where stock.pharmacy_id = ?";
        List<Stock> Lmed = new ArrayList<>();
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setInt(1,id);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Stock med = new Stock(
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("quantity")
                    );
                    Lmed.add(med);
                }
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
        return Lmed;
    }













}




