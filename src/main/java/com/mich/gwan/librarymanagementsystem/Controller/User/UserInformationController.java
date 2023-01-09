package com.mich.gwan.librarymanagementsystem.Controller.User;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.NotificationAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class UserInformationController implements Initializable {
DatabaseConnection databaseConnection;
    @FXML
    private TextField userInfoText;

    @FXML
    private ListView<String> userInformationListView;

    @FXML
    void displayUserInfomation(ActionEvent event) {
        ObservableList<String> userInfo= FXCollections.observableArrayList();
        if(this.userInfoText.getText().isEmpty()){
            NotificationAlert empty=new NotificationAlert();
            empty.showInformationAlert("Empty","Enter User Id To view User Info");
            return;
        }
        String userId=userInfoText.getText();
        String query="select * from libraryuser where userid='"+userId+"'";

        try {
            ResultSet rs=databaseConnection.execQuery(query);
            while (rs.next()){
                userInfo.add("User Id:   "+rs.getString("userid"));
                userInfo.add("User Name:   "+rs.getString("userfirstname")+"      "+rs.getString("userlastname"));
                userInfo.add("User Gender:   "+rs.getString("usergender"));
                userInfo.add("User Email:   "+rs.getString("useremail"));
                userInfo.add("User Phone Number :   "+rs.getString("userphone"));
                userInfo.add("Username:   "+rs.getString("username"));
                userInfo.add("User Password:   "+rs.getString("userpassword"));
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();


        }
       userInformationListView.getItems().setAll(userInfo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       databaseConnection=DatabaseConnection.getInstance() ;
    }
}

