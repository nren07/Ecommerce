package com.example.ecomapp;
import java.sql.*;

public class DbConnection {
    private final String dburl="jdbc:mysql://localhost:3306/ecomapp";
    private final String userName="root";

    private final String password="1234";

    private Statement getStatement(){
        try{
            Connection connection=DriverManager.getConnection(dburl,userName,password);
            return connection.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){
        try {
            Statement statement=getStatement();
            return statement.executeQuery(query);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateDb(String query){
        try{
            Statement statement=getStatement();
            return statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}

