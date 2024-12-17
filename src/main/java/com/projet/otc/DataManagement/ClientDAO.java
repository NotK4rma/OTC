package com.projet.otc.DataManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {


    public static int saveClient(String un, String pw){
        String query = "insert into users(Username,Password) values (?,?)";
        try(
                Connection conn =DataBaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ){

            stmt.setString(1,un);
            stmt.setString(2,pw);

            return stmt.executeUpdate();


        }
        catch (SQLException e){
            if(e.getErrorCode() == 1062){
                System.out.println("client existe deja");
                return -1;
            }
            else{
                e.printStackTrace();
                return -2;
            }
        }

    }


    public static boolean getClient(String un, String pw){
        String query = "select count(*) as count from users where Username = ? and BINARY Password = ?";
        int res=0;
        try(
                Connection conn = DataBaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ){
            stmt.setString(1,un);
            stmt.setString(2,pw);
            try( ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    res = rs.getInt("count");
                }
                return res==1;
            }


        }catch(SQLException e){
            e.printStackTrace();;
            return false;
        }


    }


}
