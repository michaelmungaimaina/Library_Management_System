package com.mich.gwan.librarymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       /**FXMLLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));*/
            Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Login/Login.fxml"));
            stage.setTitle("Library Management Login");
            stage.setScene(new Scene(root));
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}