package com.example.ecomapp;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    SimpleIntegerProperty id;

    SimpleDoubleProperty price;
    SimpleStringProperty name;
    SimpleIntegerProperty quantity;

    public Product(int id, double price, String name,int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.price = new SimpleDoubleProperty(price);
        this.name = new SimpleStringProperty(name);
        this.quantity=new SimpleIntegerProperty(quantity);
    }

    public static ObservableList<Product> getAllProduct(){
        String selectAllProduct="select id,name,price,quantity from product";
        return fatchDetaFromDb(selectAllProduct);
    }
    public static ObservableList<Product> fatchDetaFromDb(String query){
        ObservableList<Product>data= FXCollections.observableArrayList();
        DbConnection connection=new DbConnection();
        try{
            ResultSet rs=connection.getQueryTable(query);
            while(rs.next()){
                Product product=new Product(rs.getInt("id"),rs.getDouble("price"),rs.getString("name"),rs.getInt("quantity"));
                data.add(product);
            }
            return data;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    public int getId() {
        return id.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }


    public String getName() {
        return name.get();
    }
}
