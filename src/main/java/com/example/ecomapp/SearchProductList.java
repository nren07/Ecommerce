package com.example.ecomapp;

import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;

public class SearchProductList {

    public static VBox searchProduct(String search){
        String query="select * from product where name like '%"+search+"%';";
        VBox table=ProductList.createTable(Product.fatchDetaFromDb(query));
        return table;
    }

}
