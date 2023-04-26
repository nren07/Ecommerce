package com.example.ecomapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserInterface {

    HBox headerBar,footerBar, welcomeBox;
    GridPane loginPage,signUpPage;
    VBox body,productPage,orderPage,searchPage;
    Button signInButton,loggedOutButton,backBtn,cartBtn;
    Button productAddBtn,viewOrdersBtn,newAdminSignUpBtn,viewRegisteredUser,viewAdminUserDetails;
    Label msg,welcomeLabel;
    Boolean isSign = false;
    ObservableList<Product> items = FXCollections.observableArrayList();
    public static User loggedInCustomer;
    public static User loggedInAdmin;
    TextField searchText=new TextField();

    public UserInterface() {
        createContent();
        headerPage();
        footerPage();
        createLoginPage();
        createSignUpPage();
        createAdminPage();
        cartListPage();

    }

    public BorderPane createContent() {

        backBtn=new Button("Go back");
        body = new VBox();
        body.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setPrefSize(800, 600);

        productPage = ProductList.getAllProducts();
        productPage.setAlignment(Pos.CENTER);

        body.getChildren().add(productPage);

        root.setTop(headerBar);
        root.setCenter(body);
        root.setBottom(footerBar);

        return root;

    }
    public void headerPage() {

        headerBar = new HBox(20);
        headerBar.setPrefHeight(100);
        headerBar.setAlignment(Pos.CENTER);

        Button homeBtn=new Button();
        Image imgHome = new Image("C:\\Users\\Nren\\IdeaProjects\\EcomApp\\src\\main\\java\\com\\example\\HomeIcon.png");
        ImageView homeImgView = new ImageView(imgHome);
        homeImgView.setFitHeight(15);
        homeImgView.setFitWidth(15);
        homeBtn.setGraphic(homeImgView);

        searchText = new TextField();
        searchText.setPromptText("search here");

        Button searchBtn=new Button("Search");

        signInButton = new Button("Sign In");

        cartBtn=new Button();

        Button orderBtn=new Button("Order");
        msg=new Label();

        Image cartImage = new Image("C:\\Users\\Nren\\IdeaProjects\\EcomApp\\src\\main\\java\\com\\example\\cartIcon.png");
        ImageView imageView = new ImageView(cartImage);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        cartBtn.setGraphic(imageView);

        loggedOutButton = new Button("Log Out");

        headerBar.getChildren().addAll(homeBtn, msg, searchText, searchBtn, signInButton, cartBtn, orderBtn);

        loggedOutButton.setOnAction(actionEvent -> {
            headerBar.getChildren().clear();
            headerBar.getChildren().addAll(homeBtn, msg, searchText, searchBtn, signInButton, cartBtn, orderBtn);
            msg.setText("");
            body.getChildren().clear();
            body.getChildren().add(productPage);
            isSign = false;
            OrderList.isAdmin=false;
            OrderList.isCustomer=false;
            footerBar.setVisible(true);
        });
        homeBtn.setOnAction(actionEvent -> {
            body.getChildren().clear();
            body.getChildren().add(productPage);
            footerBar.setVisible(true);
            headerBar.setVisible(true);
        });
        backBtn.setOnAction(actionEvent1 -> {
            body.getChildren().clear();
            body.getChildren().add(productPage);
            headerBar.setVisible(true);
            footerBar.setVisible(true);
        });

        signInButton.setOnAction(actionEvent -> {

            body.getChildren().clear();
            body.getChildren().addAll(loginPage);
            headerBar.setVisible(false);
            footerBar.setVisible(false);

        });

        orderBtn.setOnAction(actionEvent -> {
            if(isSign && Order.getAllOrders()!=null){
                    orderPage=OrderList.getAllOrders();
                    orderPage.setAlignment(Pos.CENTER);
                    body.getChildren().clear();
                    body.getChildren().add(orderPage);
                    footerBar.setVisible(false);
            }else{
                Label label=new Label("Does Not have any order History");
                body.getChildren().clear();
                body.getChildren().add(label);
                footerBar.setVisible(false);
            }

        });

        searchBtn.setOnAction(actionEvent -> {
            String search=searchText.getText();
            searchPage = SearchProductList.searchProduct(search);

            body.getChildren().clear();
            body.getChildren().add(searchPage);
            footerBar.setVisible(true);
        });

    }
    public void footerPage() {

        footerBar = new HBox(20);
        footerBar.setPrefHeight(50);
        footerBar.setAlignment(Pos.CENTER);

        Button addToCartBtn = new Button("Add To Cart");

        Button buyButton = new Button("Buy Now");

        buyButton.setStyle("-fx-font-weight: bold; -fx-text-fill : #0000FF");

        footerBar.getChildren().addAll(buyButton, addToCartBtn);

        buyButton.setOnAction(actionEvent -> {
            if (isSign) {
                Product product = ProductList.getSelectedProduct();
                if (product == null) {
//                    please select a product first!
                    showDialog("please select a product first!");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, product);
                if (status) {
                    showDialog("Order Placed");
                } else showDialog("Order Failed!");

            } else {
                showDialog("login first to order product");
            }
        });

        addToCartBtn.setOnAction(actionEvent -> {
            Product product = ProductList.getSelectedProduct();
            if (product != null) {
                items.add(product);
                showDialog("iteam added successfully into cart");
            } else {
                showDialog("select iteam first");
            }

        });

    }
    public void createLoginPage() {

        loginPage = new GridPane();
        loginPage.setVgap(10);
        loginPage.setHgap(20);
        loginPage.setAlignment(Pos.CENTER);

        Button loginButton=new Button("Login");
        Button signUpBtn=new Button("Sign Up");
        Button adminLoginBtn=new Button("Admin Login");

        //      text represent like label of username ans password
        Text useNameText = new Text("User Name");
        Text passwordText = new Text("Password");
//      textField is used for text input
        TextField userName = new TextField();
        userName.setPromptText("type your username here");

//      password field is used to take password without showing digitof password
        PasswordField password = new PasswordField();
        password.setPromptText("type your password here");

        loginPage.add(useNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1, 1);
        HBox hbox = new HBox(50);
        hbox.setPrefHeight(10);
        hbox.setAlignment(Pos.CENTER);


        hbox.getChildren().addAll(loginButton, signUpBtn,adminLoginBtn);
        loginPage.add(hbox, 1, 2);

        loginButton.setOnAction(actionEvent -> {

            Login login = new Login();

            String userId = userName.getText();
            String pass = password.getText();

            loggedInCustomer = login.customerLogin(userId, pass);

            if (loggedInCustomer != null) {

                msg.setText("Welcome : " + loggedInCustomer.getName());
                isSign = true;
                body.getChildren().clear();
                body.getChildren().add(productPage);
                headerBar.setVisible(true);
                headerBar.getChildren().remove(signInButton);
                headerBar.getChildren().add(loggedOutButton);
                footerBar.setVisible(true);

            } else {
                userName.clear();
                password.clear();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Incorrect username and password");
                alert.show();
            }

        });

        adminLoginBtn.setOnAction(actionEvent -> {

            Login login = new Login();

            String userId = userName.getText();
            String pass = password.getText();

            loggedInAdmin = login.adminLogin(userId, pass);

            if (loggedInAdmin != null) {

                msg.setText("Welcome : " + loggedInAdmin.getName());
                isSign = true;
                body.getChildren().clear();
                body.getChildren().add(msg);
                headerBar.getChildren().clear();
                headerBar.setVisible(true);
                headerBar.getChildren().addAll(productAddBtn,loggedOutButton,viewOrdersBtn,newAdminSignUpBtn,viewRegisteredUser,viewAdminUserDetails);

            } else {
                userName.clear();
                password.clear();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Incorrect username and password");
                alert.show();
            }

        });

        welcomeBox = new HBox();
        welcomeLabel = new Label("Welcome to My Ecom Store");

        welcomeLabel.setStyle("-fx-font-size : 30pt; -fx-font-weight : bold; -fx-text-fill : #8B088B");
        welcomeBox.getChildren().add(welcomeLabel);
        welcomeBox.setAlignment(Pos.TOP_CENTER);

        signUpBtn.setOnAction(actionEvent -> {
            headerBar.setVisible(false);
            footerBar.setVisible(false);
            body.getChildren().clear();
            body.getChildren().addAll(welcomeBox, signUpPage);
        });
    }
    public void createAdminPage(){
        Admin admin=new Admin();
        productAddBtn=new Button("Add New Product");
        viewOrdersBtn=new Button("Placed Order Detail");
        newAdminSignUpBtn=new Button("Admin Sign Up");
        viewRegisteredUser=new Button("User Details");
        viewAdminUserDetails=new Button("Admins Details");

        productAddBtn.setOnAction(actionEvent -> {
            body.getChildren().clear();
            VBox box=admin.productAddPage();
            body.getChildren().add(box);
        });

        viewOrdersBtn.setOnAction(actionEvent -> {
            body.getChildren().clear();
            VBox box=admin.viewOrderPage();
            body.getChildren().add(box);
        });
        newAdminSignUpBtn.setOnAction(actionEvent -> {
            body.getChildren().clear();
            VBox box=admin.newAdminSignUpPage();
            body.getChildren().add(box);
        });
        viewRegisteredUser.setOnAction(actionEvent -> {
            body.getChildren().clear();
            VBox box=admin.viewRegisteredUserDetails();
            body.getChildren().add(box);
        });
        viewAdminUserDetails.setOnAction(actionEvent -> {
            body.getChildren().clear();
            VBox box=admin.viewAdminUserDetails();
            body.getChildren().add(box);

        });

    }
    public void createSignUpPage() {

        signUpPage=new GridPane();
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
        hbox.getChildren().addAll(confirmBtn, backBtn);

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

            Boolean emailCheck = Login.isAlreadyRegistered(newUserId,"email");
            Boolean mobileCheck = Login.isAlreadyRegistered(newMobile,"mobile");

            if (emailCheck && mobileCheck) {
                showDialog("email and mobile is already registered");
            } else if (mobileCheck) {
                showDialog("Mobile already Registered");
            } else if (emailCheck) {
                showDialog("email is already registered");
            } else {
//                    email and mobile no is not registered already so update db for login information
                if (Login.newCustomerRegistration(newName, newUserId, newMobile, newPass)) {
                    showDialog("Registration completed");
                } else {
                    showDialog("Registration Failed");
                }

                body.getChildren().clear();
                body.getChildren().add(loginPage);
            }
        });
    }
    public void cartListPage(){

        VBox cartBox = ProductList.iteamInCart(items);
        cartBox.setPadding(new Insets(10));
        cartBox.setSpacing(5);

        Button placeOrderBtn=new Button("Place Order");
        Button clearBtn = new Button("Clear All");
        Button deleteBtn = new Button("Delete");

        placeOrderBtn.setOnAction(actionEvent -> {
            if (isSign) {
                int count = Order.placeMultipleOrder(loggedInCustomer, items);
                if (count == 0) {
                    showDialog("Select item first");
                } else showDialog(count + " order Placed!");
            } else showDialog("please login first");
        });

        clearBtn.setOnAction(actionEvent1 -> items.clear());

        deleteBtn.setOnAction(actionEvent1 -> {
            Product product = ProductList.getSelectedProduct();
            if (product != null) {
                items.remove(product);
                showDialog("iteam removed successfully from cart");
            } else {
                showDialog("select iteam first");
            }
        });

        HBox hbox = new HBox(60);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(placeOrderBtn, clearBtn, deleteBtn);
        cartBox.getChildren().add(hbox);
        cartBox.setAlignment(Pos.CENTER);

        cartBtn.setOnAction(actionEvent -> {
            body.getChildren().clear();
            body.getChildren().add(cartBox);
            footerBar.setVisible(false);
        });
    }
    public void showDialog(String msg) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(msg);
        alert.show();
    }


}




