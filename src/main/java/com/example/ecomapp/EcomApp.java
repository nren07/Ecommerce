package com.example.ecomapp;

import javafx.application.Application;

import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class EcomApp extends Application {
    private final UserInterface ui=new UserInterface();
    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(ui.createContent());
        stage.setTitle("Ecommerce!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}