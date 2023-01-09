package com.mich.gwan.librarymanagementsystem.Controller.Book;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.NotificationAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class  SubmitRenewBookController implements Initializable {
DatabaseConnection databaseConnection;
public boolean isreadyforsubmission=false;
    @FXML
    private Button renewBookButton;

    @FXML
    private TextField submitBookIdText;

    @FXML
    private ListView<String> submitBookInfoListView;

    @FXML
    private Button submitButton;

    @FXML
    private AnchorPane submitRootAnchorPane;

    @FXML
    void renewBook(ActionEvent event) {
        if(!isreadyforsubmission) {
            NotificationAlert submit = new NotificationAlert();
            submit.showWarningAlert("Invlid", "Select Book Id To Renew");
            return;
        }
        Alert submit=new Alert(Alert.AlertType.CONFIRMATION);
        if(submit.showAndWait().get()== ButtonType.OK){
            String bookId=submitBookIdText.getText();
            String submitQuery="update issuebook set issuedate=CURRENT_TIMESTAMP,renewcount=renewcount+1 where bookid='"+bookId+"' ";
            if(databaseConnection.execAction(submitQuery)){
                NotificationAlert success=new NotificationAlert();
                success.showInformationAlert("Success","The book Has been Renewed");
            }else{
                NotificationAlert fail=new NotificationAlert();
                fail.showErrorAlert("Failed","Book Renewal Failed");
            }
        }else {
            NotificationAlert cancel=new NotificationAlert();
            cancel.showInformationAlert("Cancel","Renew Operation was cancelled");
        }
    }

    @FXML
    void showIssuedBookInformtion(ActionEvent event) {
        ObservableList<String> issuedata= FXCollections.observableArrayList();
        isreadyforsubmission=false;
        String bookid=submitBookIdText.getText();
        String issuedetails="select * from issuebook where bookid='"+bookid+"'";
        ResultSet rs;
        if(!isIssuedBook()){
            NotificationAlert alert=new NotificationAlert();
            alert.showWarningAlert("Null","The book isn't Issued");
            submitBookIdText.clear();
            return;
        }
        try {
          rs= databaseConnection.execQuery(issuedetails);
            while (rs.next()){
                int issueId=rs.getInt("issueid");
                String bookId=bookid;
                String studentId=rs.getString("memberid");
                Timestamp issueDate=rs.getTimestamp("issuedate");
                Timestamp dueDate=rs.getTimestamp("duedate");
                int renewCount=rs.getInt("renewcount");
                issuedata.add("Issued id   :"+issueId);
                issuedata.add("Book Id   :"+bookId);
                issuedata.add("Student Id    :"+studentId);
                issuedata.add("Issue Date      :"+issueDate);
                issuedata.add("Due  Date      :"+dueDate);
                issuedata.add("Renew Count    :"+renewCount);
           String    bookdetails="select * from librarybook where bookid='"+bookId+"'";
            ResultSet    r2=databaseConnection.execQuery(bookdetails);
                while (r2.next()){
                    String bookTitle=r2.getString("booktitle");
                    String bookPublisher=r2.getString("bookpublisher");
                    String bookCategory=r2.getString("bookcategory");
                    issuedata.add("Book Information");
                    issuedata.add("Book Title  :"+bookTitle);
                    issuedata.add("Book Publisher  :"+bookPublisher);
                    issuedata.add("Book Category:  "+bookCategory);
                } String studentdetails="select * from librarymember where memberid='"+studentId+"'";
            ResultSet    r3=databaseConnection.execQuery(studentdetails);
                while (r3.next()){
                    String studentFname=r3.getString("memberfirstname");
                    String studentLname=r3.getString("memberlastname");
                    String studentEmail=r3.getString("memberemail");
                    String studentPhone=r3.getString("memberphone");
                    String studentDepartment=r3.getString("memberdepartment");
                    issuedata.add("Student Information:");
                    issuedata.add("Student Name:"+studentFname+"   "+studentLname);
                    issuedata.add("Student Email   :"+studentEmail);
                    issuedata.add("Student Department  :"+studentDepartment);
                    issuedata.add("Student Phone Number  :"+studentPhone);

            }
                isreadyforsubmission=true;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        submitBookInfoListView.getItems().setAll(issuedata);
    }

    @FXML
    void submitBook(ActionEvent event) {
        if(!isreadyforsubmission){
            NotificationAlert submit=new NotificationAlert();
            submit.showWarningAlert("Invlid","Select Book Id To Submit");
            return;
        }else {
            String bookId=submitBookIdText.getText();
            String bookStatus="Available";
            String query1="update issuebook set returnedate=current_timestamp where bookid='"+bookId+"'";
            String query2="update librarybook set bookstatus='"+bookStatus+"' where bookid='"+bookId+"'";
            if(databaseConnection.execAction(query1)&&databaseConnection.execAction(query2)){
                NotificationAlert submitAlert=new NotificationAlert();
                submitAlert.showInformationAlert("Success","Book Was Submitted Successfully");
            }
            else {
                    NotificationAlert submitAlert=new NotificationAlert();
                    submitAlert.showErrorAlert("Failed","Failed to Submit the book");
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection=DatabaseConnection.getInstance();
    }
    private boolean isIssuedBook(){
        String query="select * from issuebook where bookid='"+submitBookIdText.getText()+"'";
        try{
            ResultSet rs;
            rs=databaseConnection.execQuery(query);
            if(rs.next()){
                return true;
            }
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }
}

