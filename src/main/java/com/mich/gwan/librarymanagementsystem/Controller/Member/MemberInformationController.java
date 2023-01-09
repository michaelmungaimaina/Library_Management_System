package com.mich.gwan.librarymanagementsystem.Controller.Member;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.NotificationAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MemberInformationController implements Initializable {
DatabaseConnection databaseConnection;
    @FXML
    private ListView<String> studentInfoListView;

    @FXML
    private AnchorPane studentInformationRootAnchorPane;

    @FXML
    private TextField studentInformationTextFiel;

    @FXML
    void showStudentInformation(ActionEvent event) {
        ObservableList<String> studentInfo= FXCollections.observableArrayList();
if(studentInformationTextFiel.getText().isEmpty()){
    NotificationAlert empty=new NotificationAlert();
    empty.showInformationAlert("Empty input","Enter Library Member Id");
    studentInformationTextFiel.requestFocus();
    return;
}
String studentId=studentInformationTextFiel.getText();
String query="select * from librarymember where memberid='"+studentId+"'";

        try {
            ResultSet rs=databaseConnection.execQuery(query);
            while (rs.next()){
                studentInfo.add("Member Id Number:   "+rs.getString("memberid"));
                studentInfo.add("Member  Name:   "+rs.getString("memberfirstname")+"      "+rs.getString("memberlastname"));
                studentInfo.add("Member Gender:   "+rs.getString("membergender"));
                studentInfo.add("Member  Email Address:   "+rs.getString("memberemail"));
                studentInfo.add("Member Phone Number:   "+rs.getString("memberphone"));
                studentInfo.add("Member  Department:   "+rs.getString("memberdepartment"));

            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
studentInfoListView.getItems().setAll(studentInfo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection=DatabaseConnection.getInstance();
    }
}
