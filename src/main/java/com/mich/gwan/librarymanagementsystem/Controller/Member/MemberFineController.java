package com.mich.gwan.librarymanagementsystem.Controller.Member;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.IssuedBook;
import com.mich.gwan.librarymanagementsystem.Model.LibrarySettings;
import com.mich.gwan.librarymanagementsystem.Model.NotificationAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class MemberFineController implements Initializable {
private DatabaseConnection dc;
    @FXML
    private Label bookFineLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Label fineAmountLabel;

    @FXML
    private TextField fineAmountTextField,totalFineAmountTextField;

    @FXML
    private GridPane fineGridPane;

    @FXML
    private HBox fineHBox;

    @FXML
    private VBox fineVBox;

    @FXML
    private Label issuedIdLabel;

    @FXML
    private TextField issuedIdTextField;

    @FXML
    private Label numberOfDaysWithFineLabel;

    @FXML
    private TextField numberOfDaysWithFineText;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Button saveButton;

    @FXML
    void cancelFineOperation(ActionEvent event) {
        ((Stage)this.rootAnchorPane.getScene().getWindow()).close();
    }

    @FXML
    void saveFineDetails(ActionEvent event) {
//check if number of days with fine is 0
        if (numberOfDaysWithFineText.getText().equals("0")) {
            NotificationAlert alert = new NotificationAlert();
            alert.showErrorAlert("Not Eligible", "The Member is not Eligible for a fine");
            return;
        }
        if (isAlreadyFined()) {
            NotificationAlert alert = new NotificationAlert();
            alert.showErrorAlert("Not Eligible", "The Member has Already been fined ");
            return;
        } else {
try{
    String issueId=issuedIdTextField.getText();
    String fineDays=numberOfDaysWithFineText.getText();
    String FinePerDay=fineAmountTextField.getText();
    String totalFine=totalFineAmountTextField.getText();
    String fineQuery="insert into bookfine(issueid,fineamount) values('"+issueId+"','"+totalFine+"'";
    if(dc.execAction(fineQuery)){
        NotificationAlert alert = new NotificationAlert();
        alert.showErrorAlert("Success", "The Member was Fined Successfully ");
    }else{
        NotificationAlert alert = new NotificationAlert();
        alert.showErrorAlert("Failed", "Fine Operation Failed ");
    }

}catch (Exception ex){
    ex.printStackTrace();
    ex.printStackTrace();
}
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
dc=DatabaseConnection.getInstance();
    }
    public void fillFineUI(IssuedBook issuedBook){
        Timestamp startDate=issuedBook.getDueDate();
        Timestamp returnDate=issuedBook.getDueDate();
       int days= issuedBook.getNumberOfDaysWithFine(returnDate,startDate);
        issuedIdTextField.setText(String.valueOf(issuedBook.getIssueBookid()));
        LibrarySettings librarySettings=LibrarySettings.getPreferences();
       float finePerDay=librarySettings.getFinePerDay();
       fineAmountTextField.setText(String.valueOf(finePerDay));
       if(days>0){
           totalFineAmountTextField.setText(String.valueOf(days*finePerDay));
       }else{
           numberOfDaysWithFineText.setText("0");
       }
        issuedIdTextField.setEditable(false);
        numberOfDaysWithFineText.setEditable(false);
        fineAmountTextField.setEditable(false);
        totalFineAmountTextField.setEditable(false);

    }
    public boolean isAlreadyFined(){
        String issueId=issuedIdTextField.getText();
        try{
            String query="select * from bookfine where issueid='"+issueId+"'";
            ResultSet rs=dc.execQuery(query);
            while (rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }

}
