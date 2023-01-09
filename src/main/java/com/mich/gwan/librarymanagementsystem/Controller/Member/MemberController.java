package com.mich.gwan.librarymanagementsystem.Controller.Member;

import com.mich.gwan.librarymanagementsystem.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MemberController implements Initializable {
DatabaseConnection dc;
    @FXML
    private Button addStudentLabel;

    @FXML
    private TableColumn<LibraryMember, String> atudentYearOfStudyColumn;

    @FXML
    private Button deleteStudentButton;

    @FXML
    private Button refreshStudentFieldsButton;

    @FXML
    private Button exportButton;
    @FXML
    private TableColumn<LibraryMember, String> studentDepartmentColumn;

    @FXML
    private ComboBox<String> studentDepartmentCombo;

    @FXML
    private Label studentDepartmentLabel;

    @FXML
    private TableColumn<LibraryMember, String> studentEmailAddressColumn;

    @FXML
    private Label studentEmailLabel;

    @FXML
    private TextField studentEmailText;

    @FXML
    private TableColumn<LibraryMember, String> studentFirstNameColumn;

    @FXML
    private Label studentFirstNameLabel;

    @FXML
    private TextField studentFirstNameText;

    @FXML
    private TableColumn<LibraryMember, String> studentGenderColumn;

    @FXML
    private ComboBox<String> studentGenderCombo;

    @FXML
    private Label studentGenderLabel;
    @FXML
    private Button clearButton,regListButton;
    @FXML
    private TextField serchTextField;
    @FXML
    private TableColumn<LibraryMember, String> studentIdColumn;

    @FXML
    private Label studentIdNumberLabel;

    @FXML
    private TextField studentIdNumberText;

    @FXML
    private TableColumn<LibraryMember, String> studentLastNameColumn;

    @FXML
    private Label studentLastNameLabel;

    @FXML
    private TextField studentLastNameText;

    @FXML
    private TableColumn<LibraryMember, String> studentPhoneNumberColumn;

    @FXML
    private Label studentPhoneNumberLabel;

    @FXML
    private TextField studentPhoneNumberText;

    @FXML
    private TableColumn<LibraryMember, String> studentRegNumberColumn, regStatusColumnColumn;

    @FXML
    private Label studentRegNumberLabel;

    @FXML
    private TextField studentRegNumberText;

    @FXML
    private AnchorPane studentRootAnchorPane;

    @FXML
    private GridPane studentTabGridPane;

    @FXML
    private TableView<LibraryMember> studentTabTableView;

    @FXML
    private Pane studentTopPane;

    @FXML
    private Button updateStudentButton;

    @FXML
    private ComboBox<String> yearOfStudyCombo;

    @FXML
    private Label yearOfStudyLabel;
Validation validation;
Boolean isInEditMode=false;
    @FXML
    void addNewStudent(ActionEvent event) {
       validation=new Validation();
       if(isInEditMode){
           updateStudent();
           return;
       }
        if(validation.isEmptyTextField(studentIdNumberText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Id Number");
            studentIdNumberText.requestFocus();
            return;
        }
        if(memberAlreadyExist(studentIdNumberText.getText())){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Duplicate"," Member Already Exist");
            studentIdNumberText.requestFocus();
            return;
        }
       if(validation.isEmptyTextField(studentFirstNameText)){
         NotificationAlert empty=new NotificationAlert();
         empty.showWarningAlert("Empty","Empty Member First Name");
         studentFirstNameText.requestFocus();
         return;
       }
        if(validation.isEmptyTextField(studentLastNameText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Last Name");
            studentLastNameText.requestFocus();
            return;
        }
        if(validation.isEmptyCombo(studentGenderCombo)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Gender");
            studentGenderCombo.requestFocus();
            return;
        }
        if(validation.isEmptyTextField(studentPhoneNumberText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Phone");
            studentPhoneNumberText.requestFocus();
            return;
        }
        if(validation.isEmptyTextField(studentEmailText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Email");
            studentEmailText.requestFocus();
            return;
        }
        if(validation.isEmptyCombo(studentDepartmentCombo)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Department");
           studentDepartmentCombo.requestFocus();
            return;
        }
       else {
           try {
               String firstName=    studentFirstNameText.getText().toUpperCase();
               String lastName=  studentLastNameText.getText().toUpperCase();
               String gender=   studentGenderCombo.getValue().toUpperCase();
               String id= studentIdNumberText.getText();
               String email=  studentEmailText.getText();
               String phone=  studentPhoneNumberText.getText().toUpperCase();
               String department=   studentDepartmentCombo.getValue().toUpperCase();
               String addquery="insert into librarymember values('"+id+"','"+firstName+"','"+lastName+"','"+gender+"','"+email+"','"+phone+"','"+department+"','Unregistered')";
               if(dc.execAction(addquery)){
                   NotificationAlert success=new NotificationAlert();
                   success.showInformationAlert("Success","Member Was Added Successfully");
                   refreshTable();
               }else {
                   NotificationAlert empty=new NotificationAlert();
                   empty.showErrorAlert("Error","Fail to Add Member");
               }

           }catch (Exception e){
               e.printStackTrace();
               e.getCause();
           }
        }
    }

    @FXML
    void deleteStudent(ActionEvent event) {
       LibraryMember selectedForDelete=studentTabTableView.getSelectionModel().getSelectedItem();
       if(selectedForDelete==null){
           NotificationAlert empty=new NotificationAlert();
           empty.showInformationAlert("Empty","Select Member To Delete");
           return;
       }
       else {
           try {
               String deleteqyery="delete from librarymember where memberid='"+selectedForDelete.getMemberId()+"'";
               Alert confrm=new Alert(Alert.AlertType.CONFIRMATION);
               confrm.setTitle("Confirm Delete");
               confrm.setHeaderText(null);
               confrm.setContentText("Student will be deleted, Confirm to delete");
               if(confrm.showAndWait().get()==ButtonType.OK){
                   if(dc.execAction(deleteqyery)){
                       NotificationAlert success=new NotificationAlert();
                       success.showInformationAlert("Success","User Was deleted Successfully");
                       studentsList.remove(selectedForDelete);
                   }else{
                       NotificationAlert fail=new NotificationAlert();
                       fail.showErrorAlert("Error","Fail to delete Student Try Again");
                   }
               }else {
                   NotificationAlert cancel=new NotificationAlert();
                   cancel.showInformationAlert("cancel","Delete Operation Was cancelled");
               }


           }catch (Exception e){
               e.getCause();
               e.printStackTrace();
           }
       }

    }

    @FXML
    void refreshStudentFields(ActionEvent event) {
      refreshTable();
        clearFields();

    }

    @FXML
    void updateStudent() {
        LibraryMember selectedForEdit=studentTabTableView.getSelectionModel().getSelectedItem();
        validation=new Validation();
        if(validation.isEmptyTextField(studentIdNumberText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Id Number");
            studentIdNumberText.requestFocus();
            return;
        }
        if(validation.isEmptyTextField(studentFirstNameText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member First Name");
            studentFirstNameText.requestFocus();
            return;
        }
        if(validation.isEmptyTextField(studentLastNameText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Last Name");
            studentLastNameText.requestFocus();
            return;
        }
        if(validation.isEmptyCombo(studentGenderCombo)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Gender");
            studentGenderCombo.requestFocus();
            return;
        }
        if(validation.isEmptyTextField(studentPhoneNumberText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Phone");
            studentPhoneNumberText.requestFocus();
            return;
        }
        if(validation.isEmptyTextField(studentEmailText)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Email");
            studentEmailText.requestFocus();
            return;
        }
        if(validation.isEmptyCombo(studentDepartmentCombo)){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Empty","Empty Member Department");
            studentDepartmentCombo.requestFocus();
            return;
        }
        else {

       try{
           String firstName=    studentFirstNameText.getText();
           String lastName=  studentLastNameText.getText();
           String gender=   studentGenderCombo.getValue();
           String id= studentIdNumberText.getText();
           String email=  studentEmailText.getText();
           String phone=  studentPhoneNumberText.getText();
           String department=   studentDepartmentCombo.getValue();
           String updateQuery="update librarymember set memberfirstname='"+firstName+"',memberlastname='"+lastName+"',membergender='"+gender+"',memberemail='"+email+"',memberphone='"+phone+"',memberdepartment='"+department+"' where memberid='"+id+"'";
           if(dc.execAction(updateQuery)){
               NotificationAlert success=new NotificationAlert();
               success.showInformationAlert("Success","User Was Updated Successfully");
               refreshTable();
               clearFields();
           }else {
               NotificationAlert fail=new NotificationAlert();
               fail.showErrorAlert("Error","Fail to Update Student Try Again");
           }
       }catch (Exception e) {
           e.getCause();
           e.printStackTrace();
       }
       }
    }
    private ObservableList<LibraryMember> getStudents(){

        String query="select * from librarymember";
        try {
            //Connect database
            Connection conn= DatabaseConnection.ConnectDatabase();
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery(query);
            LibraryMember libraryMember;
            while (rs.next()){
                libraryMember=new LibraryMember();
                libraryMember.setMemberId(rs.getInt("memberid"));
                libraryMember.setFirstName(rs.getString("memberfirstname"));
                libraryMember.setLastName(rs.getString("memberlastname"));
                libraryMember.setGender(rs.getString("membergender"));
                libraryMember.setEmail(rs.getString("memberemail"));
                libraryMember.setPhone(rs.getString("memberphone"));
                libraryMember.setDepartment(rs.getString("memberdepartment"));
                libraryMember.setRegStatus(rs.getString("registrationstatus"));
                 studentsList.add(libraryMember);


            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();

        }
        return studentsList;
    }
    private  void showStudents(){
        ObservableList<LibraryMember> showStudents=getStudents();
        studentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        studentEmailAddressColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        studentDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        regStatusColumnColumn.setCellValueFactory(new PropertyValueFactory<>("regStatus"));
     studentTabTableView.setItems(showStudents);
     libraryMembersSerchFilter();
    }
    ObservableList<LibraryMember> studentsList= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dc=DatabaseConnection.getInstance();
        showStudents();
        ObservableList<String> gender= FXCollections.observableArrayList("Male","Female");
        studentGenderCombo.getItems().setAll(gender);
        ObservableList<String> department= FXCollections.observableArrayList("ICT","Business","Engineering","Maths and Physics","Pure and Applied Sciences");
        studentDepartmentCombo.getItems().addAll(department);

    }

    public void showStudentInformation() {
        LibraryMember student=studentTabTableView.getSelectionModel().getSelectedItem();
        if(student==null){
            System.out.println("No student selected");
            return;
        }
        else {
            studentFirstNameText.setText(student.getFirstName());
            studentLastNameText.setText(student.getLastName());
            studentGenderCombo.setValue(student.getGender());
            studentIdNumberText.setText(""+student.getMemberId());
            studentEmailText.setText(student.getEmail());
            studentPhoneNumberText.setText(student.getPhone());
            studentDepartmentCombo.setValue(student.getDepartment());
            isInEditMode=Boolean.TRUE;
        }

    }
    private void clearFields(){
        studentFirstNameText.clear();
        studentLastNameText.clear();
        studentGenderCombo.setValue(null);
        studentIdNumberText.clear();
        studentEmailText.clear();
        studentPhoneNumberText.clear();
        studentDepartmentCombo.setValue(null);

    }
    void refreshTable(){
        studentsList.clear();
        String query="select * from librarymember";
        try {
            //Connect database
            Connection conn= DatabaseConnection.ConnectDatabase();
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery(query);
            LibraryMember libraryMember;
            while (rs.next()){
                libraryMember=new LibraryMember();
                libraryMember.setMemberId(rs.getInt("memberid"));
                libraryMember.setFirstName(rs.getString("memberfirstname"));
                libraryMember.setLastName(rs.getString("memberlastname"));
                libraryMember.setGender(rs.getString("membergender"));
                libraryMember.setEmail(rs.getString("memberemail"));
                libraryMember.setPhone(rs.getString("memberphone"));
                libraryMember.setDepartment(rs.getString("memberdepartment"));
                libraryMember.setRegStatus(rs.getString("registrationstatus"));
                studentsList.add(libraryMember);
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();

        }

    }
    private boolean memberAlreadyExist(String memberId){
        String duplicate="select * from librarymember where memberid='"+memberId+"'";
        try {
            Connection conn;
                conn=DatabaseConnection.ConnectDatabase();
                Statement stm=conn.createStatement();
                ResultSet rs=stm.executeQuery(duplicate);
                while (rs.next()){
                    return true;
                }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }
    public void fillUI(){
        showStudentInformation();
    }

    public void registerMember(ActionEvent actionEvent) throws IOException {
LibraryMember selectedForRegistration=studentTabTableView.getSelectionModel().getSelectedItem();
if(selectedForRegistration==null){
    NotificationAlert empty=new NotificationAlert();empty.showInformationAlert("No Selection","Select Member For Registration");
    return;
}
else {
    FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Member/RegistrationFeePayment.fxml"));
    Parent parent=loader.load();
    RegistrationFeePaymentController feePaymentController=(RegistrationFeePaymentController) loader.getController();
    feePaymentController.fiiRegUI(selectedForRegistration);
    Stage stage=new Stage(StageStyle.UNDECORATED);
    stage.setTitle("Library Member Registration");
    stage.setScene(new Scene(parent));
    stage.show();
}
    }
    @FXML
    void clearSerchTextField(ActionEvent event) {
this.serchTextField.clear();
    }
    @FXML
    void exportMembersToExcel(ActionEvent event) {
        ExportToExcel<LibraryMember> libraryMemberExportToExcel=new ExportToExcel<>();
        libraryMemberExportToExcel.export(studentTabTableView);
    }
    private void libraryMembersSerchFilter() {
        //initialize filtered list
        FilteredList<LibraryMember> filtereData = new FilteredList<>(studentsList, e -> true);
       serchTextField.setOnKeyReleased(e -> {


           serchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filtereData.setPredicate((Predicate<? super LibraryMember>) libraryMember -> {

                    if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String toLowerCase = newValue.toLowerCase();
                    if (libraryMember.getFirstName().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else if (libraryMember.getLastName().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else if (Integer.toString(libraryMember.getMemberId()).toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else if (libraryMember.getEmail().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else if (libraryMember.getPhone().toLowerCase().contains(toLowerCase)) {
                        return true;
                    }
                 else if (libraryMember.getDepartment().toLowerCase().contains(toLowerCase)) {
                   return true;
               }
                    else if (libraryMember.getGender().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else

                        return false;
                });
            });
            final SortedList<LibraryMember> libraryMembers = new SortedList<>(filtereData);
            libraryMembers.comparatorProperty().bind(studentTabTableView.comparatorProperty());
           studentTabTableView.setItems(libraryMembers);
        });
    }

    public void showMembeRegWindow(ActionEvent actionEvent) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Member/MemberRegistrationList.fxml"),"Registered Members",null);
    }
}

