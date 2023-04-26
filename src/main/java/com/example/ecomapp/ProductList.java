package com.example.ecomapp;


import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    public static TableView<Product> productTable;
    public static VBox createTable(ObservableList<Product> data) {


//        cols
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
//
        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
//
        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn quantity=new TableColumn("Quantity");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

//        dummy data


        productTable = new TableView<>();

        productTable.getColumns().addAll(id,name,price,quantity);

        productTable.setItems(data);
        // to eliminate extra column and spread column at equal space
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        productTable.setTableMenuButtonVisible(true);

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().add (productTable);
        return vbox;
    }


    public static VBox getAllProducts(){
        ObservableList<Product>data=Product.getAllProduct();
        return createTable(data);
    }

    public static Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }

    public static VBox iteamInCart(ObservableList<Product>data){
        return createTable(data);
    }
}
