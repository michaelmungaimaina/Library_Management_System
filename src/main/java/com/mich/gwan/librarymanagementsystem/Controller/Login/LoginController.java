package com.mich.gwan.librarymanagementsystem.Controller.Login;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.NotificationAlert;
import com.mich.gwan.librarymanagementsystem.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginWarningLabel;

    @FXML
    private Button passwordResetButton;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label systemNameLabel;

    @FXML
    private Label userLoginLabel;

    @FXML
    private PasswordField userPasswordField;

    @FXML
    private TextField userUsernameTextField;

    @FXML
    void cancelLogin(ActionEvent event)throws Exception {
        Alert cancel =new Alert(Alert.AlertType.CONFIRMATION);
        cancel.setTitle("Cancel");
        cancel.setHeaderText(null);
        cancel.setContentText("Do You Really Want to cancel Login Operation");
        if(cancel.showAndWait().get()==ButtonType.OK){
            Parent root= FXMLLoader.load(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Book/MainWindow.fxml"));
            Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene Scene=new Scene(root);
            stage.setScene(Scene);
            stage.close();

        }
        else {
            NotificationAlert notificationAlert=new NotificationAlert();
            notificationAlert.showInformationAlert("Cancel","Cancel Operation was Terminated");
        }
    }

    @FXML
    void resetPassword(ActionEvent event) {

    }

    @FXML
    void showMainWindow(ActionEvent event) throws IOException {
        String username=userUsernameTextField.getText();
        String password=userPasswordField.getText();
        String query="select * from libraryuser where username='"+username+"' and userpassword='"+password+"'";
        if(username.isEmpty()){
            NotificationAlert alert=new NotificationAlert();
            alert.showWarningAlert("Nulll","Enter username to login");
            userUsernameTextField.clear();
            userUsernameTextField.requestFocus();
            return;
        }
        if(password.isEmpty()){
            NotificationAlert alert=new NotificationAlert();
            alert.showWarningAlert("Nulll","Enter Password to login");
            userPasswordField.clear();
            userPasswordField.requestFocus();
            return;
        }
        try {
            Connection conn= DatabaseConnection.ConnectDatabase();
            ResultSet rs= libraryconn.execQuery(query);
            if (rs.next()){
                User user=new User();
                user.setUsername(rs.getString("username"));
                if(user.getUsername().equals("Admin")) {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Book/MainWindow.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene Scene = new Scene(root);
                    stage.setScene(Scene);
                    stage.show();
                    stage.centerOnScreen();
                }
            }
            else {
                NotificationAlert alert=new NotificationAlert();
                alert.showWarningAlert("Invalid","Your Password or Username is Invalid");
                userPasswordField.clear();
                userUsernameTextField.clear();
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
            e.getCause();
        }

    }
DatabaseConnection libraryconn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        libraryconn=DatabaseConnection.getInstance();

    }
}
