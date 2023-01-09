package com.mich.gwan.librarymanagementsystem.Controller.Member;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.LibraryMember;
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
import java.util.ResourceBundle;

public class RegistrationFeePaymentController implements Initializable {

    @FXML
    private Label bookFineLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private GridPane fineGridPane;

    @FXML
    private HBox fineHBox;

    @FXML
    private TextField issuedIdTextFiememberIdTextFieldd;

    @FXML
    private Label memberIdLabel;

    @FXML
    private Label numberOregistrationAmountLabelDaysWithFineLabel;

    @FXML
    private TextField registrationAmountTextField;

    @FXML
    private VBox registrationVBox;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Button saveButton;

    @FXML
    void cancelMemberRegistration(ActionEvent event) {
        ((Stage)rootAnchorPane.getScene().getWindow()).close();
    }

    @FXML
    void registerMember(ActionEvent event) {
String memberId=issuedIdTextFiememberIdTextFieldd.getText();
Float regAmount= Float.valueOf(registrationAmountTextField.getText());
String regStatus="Registered";
String regQuery="insert into memberegistration(memberid,regamount) values('"+memberId+"','"+regAmount+"')";
String regUpdate="update librarymember set registrationstatus='"+regStatus+"' where memberid='"+memberId+"'";
if(isRegisteredMember()){
    return;
}
if(dc.execAction(regQuery)&&dc.execAction(regUpdate)){
    NotificationAlert alert=new NotificationAlert();
    alert.showInformationAlert("Success","Member Registered Successfully");

}else {
    NotificationAlert alert=new NotificationAlert();
    alert.showErrorAlert("Error","Member Registration Failed...!!");
}
    }
DatabaseConnection dc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
dc=DatabaseConnection.getInstance();
    }

    public void fiiRegUI(LibraryMember selectedForRegistration) {
        issuedIdTextFiememberIdTextFieldd.setText(String.valueOf(selectedForRegistration.getMemberId()));
        LibrarySettings librarySettings=LibrarySettings.getPreferences();
        registrationAmountTextField.setText(String.valueOf(librarySettings.getRegistrationFee()));
        registrationAmountTextField.setEditable(false);
        issuedIdTextFiememberIdTextFieldd.setEditable(false);
    }
    public boolean isRegisteredMember(){
        String memberId=   issuedIdTextFiememberIdTextFieldd.getText();
        String duplicateQuery=" select * from memberegistration where memberid='"+memberId+"'";
        try{
            ResultSet rs=dc.execQuery(duplicateQuery);
            while (rs.next()){
                NotificationAlert duplicate=new NotificationAlert();
                duplicate.showErrorAlert("Duplicate","Member Already Registered");
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
