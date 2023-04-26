package com.example.ecomapp;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Admin {



    public  VBox productAddPage(){

        Text name = new Text("Name :");
        Text price = new Text("Price :");
        Text quantity = new Text("Quantity :");
        Text description = new Text("Discription :");

        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField quantityField = new TextField();
        TextField descriptionField = new TextField();

        GridPane productUpdate=new GridPane();
        productUpdate.setVgap(20);
        productUpdate.setHgap(20);
        productUpdate.setAlignment(Pos.CENTER);

        productUpdate.add(name,0,0);
        productUpdate.add(price,0,1);
        productUpdate.add(quantity,0,2);
        productUpdate.add(description,0,3);

        productUpdate.add(nameField,1,0);
        productUpdate.add(priceField,1,1);
        productUpdate.add(quantityField,1,2);
        productUpdate.add(descriptionField,1,3);

        Button updateDbBtn=new Button("Add Product");
        productUpdate.add(updateDbBtn,1,4);

        updateDbBtn.setOnAction(actionEvent -> {

            String product_name=nameField.getText();
            Double product_price=Double.parseDouble(priceField.getText());
            int product_quantity=Integer.parseInt(quantityField.getText());
            String product_description=descriptionField.getText();

            String query="INSERT INTO product(name,price,quantity,discription) VALUES ('"+product_name+"',"+product_price+","+product_quantity+",'"+product_description+"');";

            DbConnection con=new DbConnection();
            if(con.updateDb(query)!=0){
                showDialog("Product Added");
                nameField.clear();
                priceField.clear();
                quantityField.clear();
                descriptionField.clear();
            }else{
                showDialog("Not Updated Plz try Again");
            }
        });

        VBox box=new VBox(20);
        box.getChildren().add(productUpdate);
        return box;

    }
    public void updateProduct(){

    }
    public VBox viewOrderPage(){
        return OrderList.getAllOrdersAdmin();
    }
    public VBox newAdminSignUpPage(){

        GridPane signUpPage=new GridPane();
        signUpPage.setHgap(10);
        signUpPage.setVgap(15);

        Text name = new Text("Name :");
        Text email = new Text("Email :");
        Text mobile = new Text("Mobile :");
        Text pass = new Text("Password :");
        Text confirmPass = new Text("Confirm Password :");

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField mobileField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPassField = new PasswordField();

        signUpPage.add(name, 0, 0);
        signUpPage.add(email, 0, 1);
        signUpPage.add(pass, 0, 2);
        signUpPage.add(confirmPass, 0, 3);
        signUpPage.add(mobile, 0, 4);
        signUpPage.add(nameField, 1, 0);
        signUpPage.add(emailField, 1, 1);
        signUpPage.add(passwordField, 1, 2);
        signUpPage.add(confirmPassField, 1, 3);
        signUpPage.add(mobileField, 1, 4);
        HBox hbox = new HBox(80);
        hbox.setAlignment(Pos.CENTER);
        Button confirmBtn = new Button("Confirm");
        hbox.getChildren().add(confirmBtn);

        signUpPage.add(hbox, 1, 5);
        signUpPage.setAlignment(Pos.CENTER);



        confirmBtn.setOnAction(actionEvent1 -> {
            String newUserId = emailField.getText();
            String newMobile = mobileField.getText();
            String newName = nameField.getText();
            String newPass = passwordField.getText();
            String newConfirmPass = confirmPassField.getText();

            if(newUserId.isEmpty() || newMobile.isEmpty() ||newName.isEmpty() || newPass.isEmpty()){
                showDialog("field should not be empty");
                return;
            }
            if (!newPass.equals(newConfirmPass)) {
                showDialog("password does not match with confirm password");
                return;
            }

            Boolean emailCheck = Login.isAlreadyAdminRegistered(newUserId,"email");
            Boolean mobileCheck = Login.isAlreadyAdminRegistered(newMobile,"mobile");

            if (emailCheck && mobileCheck) {
                showDialog("email and mobile is already registered");
            } else if (mobileCheck) {
                showDialog("Mobile already Registered");
            } else if (emailCheck) {
                showDialog("email is already registered");
            } else {
//                    email and mobile no is not registered already so update db for login information
                if (Login.newAdminRegistration(newName, newUserId,newPass,newMobile)) {
                    showDialog("Registration completed");
                } else {
                    showDialog("Data invalid! please enter valid input");
                }
                nameField.clear();
                emailField.clear();
                mobileField.clear();
                passwordField.clear();
                confirmPassField.clear();
            }
        });
        VBox box=new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(signUpPage);
        return box;
    }
    public VBox viewRegisteredUserDetails(){
        VBox box=UserList.getAllUsers();
        return box;
    }
    public VBox viewAdminUserDetails(){
        VBox box=UserList.getAllAdminUsers();
        return box;
    }

    public void showDialog(String msg){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
