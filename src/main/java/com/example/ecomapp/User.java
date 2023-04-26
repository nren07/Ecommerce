package com.example.ecomapp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class User {
    public SimpleIntegerProperty id;
    public SimpleStringProperty email;
    public SimpleStringProperty mobile;
    public SimpleStringProperty name;


    public User(int id, String name, String email, String mobile ) {
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        this.mobile = new SimpleStringProperty(mobile);
        this.name = new SimpleStringProperty(name);

    }

    public int getId() {
        return id.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getMobile() {
        return mobile.get();
    }
    public String getName() {
        return name.get();
    }


    public static ObservableList<User> getAllUsers(){
        String selectAllUsers="select * from logininfo;";
        return fatchDetaFromDb(selectAllUsers);
    }
    public static ObservableList<User> getAllAdminUsers(){
        String selectAllAdmins="SELECT * from admintable;";
        return fatchDetaFromDb(selectAllAdmins);
    }
    public static ObservableList<User> fatchDetaFromDb(String query){
        ObservableList<User>data= FXCollections.observableArrayList();
        DbConnection connection=new DbConnection();
        try{
            ResultSet rs=connection.getQueryTable(query);
            while(rs.next()){
                User user=new User(rs.getInt("id"),rs.getString("name"),
                        rs.getString("email"),rs.getString("mobile"));

                data.add(user);
            }
            return data;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
