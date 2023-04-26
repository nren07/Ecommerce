package com.example.ecomapp;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class UserList {
    public static TableView<User> userTable;


    public static VBox createTable(ObservableList<User> data) {


//        cols
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id.setResizable(true);
//
        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setResizable(true);
//
        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setResizable(true);


        TableColumn mobile=new TableColumn("Mobile No");
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobile.setResizable(true);

        userTable = new TableView<>();

        userTable.getColumns().addAll(id,name,email,mobile);

        userTable.setItems(data);
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userTable.setTableMenuButtonVisible(true);
        // to eliminate extra column and spread column at equal space
//        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        orderTable.setTableMenuButtonVisible(true);

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().add (userTable);
        return vbox;
    }
    public static VBox getAllUsers(){
        ObservableList<User>data=User.getAllUsers();
        return createTable(data);
    }
    public static VBox getAllAdminUsers(){
        ObservableList<User>data=User.getAllAdminUsers();
        return createTable(data);
    }
}
