package com.mich.gwan.librarymanagementsystem.Model;

import javafx.scene.control.Alert;

public class NotificationAlert {
    public void showErrorAlert(String title ,String message){
        Alert errorAlert=new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
    public void showWarningAlert(String title ,String message){
        Alert errorAlert=new Alert(Alert.AlertType.WARNING);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
    public void showInformationAlert(String title ,String message){
        Alert errorAlert=new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
}
