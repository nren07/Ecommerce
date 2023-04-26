package com.example.ecomapp;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class OrderList {
    public static TableView<Order> orderTable;
    static boolean isAdmin=false;
    static boolean isCustomer=false;

    public static VBox createTable(ObservableList<Order> data) {

//        cols
        TableColumn id=new TableColumn("Product ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn customerId=new TableColumn("Customer ID");
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        //
        TableColumn price = new TableColumn("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
//
        TableColumn name = new TableColumn("Product Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn quantity=new TableColumn("QTY");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn order_date=new TableColumn("ORDER DATE");
        order_date.setCellValueFactory(new PropertyValueFactory<>("order_date"));

        TableColumn order_status=new TableColumn("ORDER STATUS");
        order_status.setCellValueFactory(new PropertyValueFactory<>("order_status"));

        orderTable = new TableView<>();

        if(isCustomer) orderTable.getColumns().addAll(id,name,price,quantity);
        if(isAdmin) orderTable.getColumns().addAll(id,customerId,name,price,order_date,order_status);

        orderTable.setItems(data);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderTable.setTableMenuButtonVisible(true);
        // to eliminate extra column and spread column at equal space
//        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        orderTable.setTableMenuButtonVisible(true);

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().add (orderTable);
        return vbox;
    }
    public static VBox getAllOrders(){
        isCustomer=true;
        ObservableList<Order>data=Order.getAllOrders();
        return createTable(data);
    }
    public static VBox getAllOrdersAdmin(){
        isAdmin=true;
        ObservableList<Order>data=Order.getAllOrdersAdmin();
        return createTable(data);
    }

    public static Order getSelectedOrders(){
        return orderTable.getSelectionModel().getSelectedItem();
    }
}


