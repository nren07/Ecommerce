package com.example.ecomapp;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {

    SimpleIntegerProperty id;
    SimpleIntegerProperty customerId;

    SimpleStringProperty name;

    SimpleDoubleProperty price;
    SimpleIntegerProperty quantity;
    SimpleStringProperty order_date;
    SimpleStringProperty order_status;



    public Order (int id,  String name, double price,int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.price = new SimpleDoubleProperty(price);
        this.name = new SimpleStringProperty(name);
        this.quantity=new SimpleIntegerProperty(quantity);

    }
    public Order (int id,int customerId,  String name, double price,String order_date,String order_status) {
        this.id = new SimpleIntegerProperty(id);
        this.customerId=new SimpleIntegerProperty(customerId);

        this.price = new SimpleDoubleProperty(price);
        this.name = new SimpleStringProperty(name);
        this.order_date=new SimpleStringProperty(order_date);
        this.order_status=new SimpleStringProperty(order_status);

    }
    public static ObservableList<Order> getAllOrders(){
        String selectAllOrders="select product.id,name,sum(product.price),count(orders.quantity) from orders join product on orders.product_id=product.id where orders.customer_id="+UserInterface.loggedInCustomer.getId()+" group by product.id;";
        return fatchDetaFromDb(selectAllOrders);
    }
    public static ObservableList<Order> getAllOrdersAdmin(){
        String selectAllOrders="select orders.id,orders.customer_id,product.name,product.price,orders.quantity,orders.order_date,orders.order_status from orders join product on orders.product_id=product.id;";
        return fatchDetaFromDbForAdmin(selectAllOrders);
    }
    public static ObservableList<Order> fatchDetaFromDbForAdmin(String query){
        ObservableList<Order>data= FXCollections.observableArrayList();
        DbConnection connection=new DbConnection();
        try{
            ResultSet rs=connection.getQueryTable(query);
            while(rs.next()){
                Order order=new Order(rs.getInt("id"),rs.getInt("customer_id"),rs.getString("name"),
                        rs.getDouble("price"),rs.getString("order_date"),rs.getString("order_status")
                );

                data.add(order);
            }
            return data;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    public static ObservableList<Order> fatchDetaFromDb(String query){
        ObservableList<Order>data= FXCollections.observableArrayList();
        DbConnection connection=new DbConnection();
        try{
            ResultSet rs=connection.getQueryTable(query);
            while(rs.next()){
                Order order=new Order(rs.getInt("id"),rs.getString("name"),
                        rs.getDouble("sum(product.price)"),rs.getInt("count(orders.quantity)")
                );

                data.add(order);
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

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public String getOrder_date() {
        return order_date.get();
    }

    public String getOrder_status() {
        return order_status.get();
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public static boolean placeOrder(User customer, Product product){
        String groupOrderId="SELECT max(group_order_id)+1 id from orders;";
        DbConnection con=new DbConnection();
        try{
            ResultSet rs=con.getQueryTable(groupOrderId);
            if(rs.next()){
                String placeOrder="INSERT INTO ORDERS(group_order_id,customer_id,product_id,product_name) VALUES " +
                        "("+rs.getInt("id")+","+customer.getId()+","+product.getId()+",'"+product.getName()+"');";
                return con.updateDb(placeOrder)!=0;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static int placeMultipleOrder(User customer, ObservableList<Product>productList){
        String groupOrderId="SELECT max(group_order_id)+1 id from orders;";
        DbConnection con=new DbConnection();
        int count=0;
        try{
            ResultSet rs=con.getQueryTable(groupOrderId);
            if(rs.next()){
                for(Product product : productList){
                    String placeOrder="INSERT INTO ORDERS(group_order_id,customer_id,product_id,product_name) VALUES " +
                            "("+rs.getInt("id")+","+customer.getId()+","+product.getId()+",'"+product.getName()+"');";
                    count+=con.updateDb(placeOrder);
                }
                return count;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }



}

