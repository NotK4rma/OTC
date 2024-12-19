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
                med.setId(rs.getInt("id"));
                med.setDesc(rs.getString("description"));
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
        String query = "select * from medications where name like ? or description like ?";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setString(1,"%"+nom+"%");
            stmt.setString(2,"%"+nom+"%");
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    Medicament med = new Medicament(rs.getString("name"),
                            rs.getString("image")
                    );
                    med.setPrice(getAveragePriceMed(rs.getInt("id")));
                    med.setId(rs.getInt("id"));
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

    public static  List<Medicament> getClientMedsRecherche(String username, String nom){
        String query = "select * from medications inner join commande on medications.id = commande.medicament where commande.user = ? and medications.name like ?";
        List<Medicament> Lmed = new ArrayList<>();
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setString(1,username);
            stmt.setString(2,"%"+nom+"%");
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
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
        System.out.println("database succes" + Lmed.size());
        return Lmed;


    }

    public static  List<Medicament> getClientMeds(String username){
        String query = "select * from medications inner join commande on medications.id = commande.medicament where commande.user = ?";
        List<Medicament> Lmed = new ArrayList<>();
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ){
            stmt.setString(1,username);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medicament med = new Medicament(rs.getString("name"),
                            rs.getString("image")
                    );
                    med.setPrice(getAveragePriceMed(rs.getInt("id")));
                    med.setId(rs.getInt("medications.id"));
                    Lmed.add(med);
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("oops");
        }
        System.out.println("database succes" + Lmed.size());
        return Lmed;


    }

    public static int saveCommande(String client , int med){
        String query = "insert into commande(user,medicament) values(?,?)";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,client);
            stmt.setInt(2,med);

            if (CommandeExiste(client,med)!=1) {
                return stmt.executeUpdate();
            }
            else{
                return 0;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }


    }

    public static int CommandeExiste(String client , int med){
        String query = "select count(*) as count from commande where user= ? and medicament = ?";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,client);
            stmt.setInt(2,med);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("count");
                }

                return 0;
            }



        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }


    }

    public static List<Stock> getInfoMedicine(int idmed){
        String query = "select * from stock inner join pharmacies on pharmacy_id=pharmacies.id inner join medications on medication_id = medications.id where medication_id = ?";
        List<Stock> Lstock = new ArrayList<>();
        try(Connection conn= DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,idmed);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Stock stk = new Stock(
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getString("pharmacies.name")
                            );

                    Lstock.add(stk);
                }

            }


        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return Lstock;

    }

    public static int deleteCommande(String client , int med){
        String query = "delete from commande where user = ? and medicament = ?";
        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,client);
            stmt.setInt(2,med);
            System.out.println("Client: " + client + ", Med: " + med);

            int test =  stmt.executeUpdate();
            System.out.println(test);
            return test;

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("erroreeeeeeeeeeeeeeeeeeeeeeee");
            return -1;
        }


    }



}
