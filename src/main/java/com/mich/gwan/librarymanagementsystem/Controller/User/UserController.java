package com.mich.gwan.librarymanagementsystem.Controller.User;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.NotificationAlert;
import com.mich.gwan.librarymanagementsystem.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserController implements Initializable {
DatabaseConnection dc;
    @FXML
    private Button addUserButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Pane libraryUserTopPane;

    @FXML
    private Button refreshUserFieldsButton;

    @FXML
    private Button updateUserButton;

    @FXML
    private TableColumn<User, String> userEmailColumn;

    @FXML
    private Label userEmailLabel;

    @FXML
    private TextField userEmailText;

    @FXML
    private TableColumn<User, String> userFirstNameColumn;

    @FXML
    private Label userFirstNameLabel;

    @FXML
    private TextField userFirstNameText;

    @FXML
    private TableColumn<User, String> userGenderColumn;

    @FXML
    private ComboBox<String> userGenderCombo;

    @FXML
    private Label userGenderLabel;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private Label userIdLabel;

    @FXML
    private TextField userIdText;

    @FXML
    private TableColumn<User, String> userLastNameColumn;

    @FXML
    private Label userLastNameLabel;

    @FXML
    private TextField userLastNameText;

    @FXML
    private TableColumn<User, String> userPasswordColumn;

    @FXML
    private Label userPasswordLabel;

    @FXML
    private PasswordField userPasswordText;

    @FXML
    private TableColumn<User, String> userPhoneNumberColumn;

    @FXML
    private Label userPhoneNumberLabel;

    @FXML
    private TextField userPhoneNumberText;

    @FXML
    private GridPane userTabGridPane;

    @FXML
    private TableView<User> userTabTableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;

    @FXML
    private AnchorPane usrRootAnchorPane;

    @FXML
    void addnewUser(ActionEvent event) {
  String userId=userIdText.getText();
        String userFirstName=userFirstNameText.getText();
        String userLastName= userLastNameText.getText();
        String userGender= userGenderCombo.getValue();
        String userPhoneNumber= userPhoneNumberText.getText();
        String userEmail= userEmailText.getText();
        String userPassword= userPasswordText.getText();
        String userName=usernameText.getText();
if(userIdText.getText().isEmpty()||userFirstName.isEmpty()|userLastName.isEmpty()|userGender.isEmpty()||userPhoneNumber.isEmpty()||userEmail.isEmpty()||userPassword.isEmpty()||userName.isEmpty()){
    NotificationAlert addAlert=new NotificationAlert();
    addAlert.showWarningAlert("Empty Field(s)","Fill All the Fields To Add User");
    return;
}
if(isduplicateUser()){
    return;
}
String addquery="insert into libraryuser values('"+userId+"','"+userFirstName+"','"+userLastName+"','"+userGender+"','"+userPhoneNumber+"','"+userEmail+"','"+userName+"','"+userPassword+"')";
if(dc.execAction(addquery)){
    refreshTable();
    clearFields();
    NotificationAlert addAlert=new NotificationAlert();
    addAlert.showInformationAlert("Success","New User Was Added");
}else{
    NotificationAlert error=new NotificationAlert();
    error.showErrorAlert("ERROR","Fail To Add User Try Again");
}
    }

    @FXML
    void deleteuser(ActionEvent event) {
        String userId=  userIdText.getText();
        if(userId.isEmpty()){
            NotificationAlert error=new NotificationAlert();
            error.showErrorAlert("ERROR","Enter Valid user Id To delete User");
            userIdText.requestFocus();
            return;
        }
        String deletequery="delete from libraryuser where userid='"+userId+"'";
        Alert delete=new Alert(Alert.AlertType.CONFIRMATION);
        delete.setTitle("Delete User");
        delete.setHeaderText(null);
        delete.setContentText("The User Will Be Deleted, Confirm to delete");
        if(delete.showAndWait().get()==ButtonType.OK){
            if(dc.execAction(deletequery)){
                refreshTable();
                clearFields();
                NotificationAlert deleteAlert=new NotificationAlert();
                deleteAlert.showInformationAlert("Success","User Was Deleted Successfully");
            }else {
                NotificationAlert error=new NotificationAlert();
                error.showErrorAlert("ERROR","Failed to Delete User Try Again");
            }
        }else {
          NotificationAlert cancel=new NotificationAlert();
          cancel.showInformationAlert("CANCEL","Delete Operation Was Cancelled");
        }
    }

    @FXML
    void refreshUserFields(ActionEvent event) {
        userIdText.clear();
        userFirstNameText.clear();
        userLastNameText.clear();
        userGenderCombo.setValue(null);
        userPhoneNumberText.clear();
        userEmailText.clear();
        userPasswordText.clear();
        usernameText.clear();

    }

    @FXML
    void updateUser(ActionEvent event) {
        String userId=  userIdText.getText();
        String userFirstName=userFirstNameText.getText();
        String userLastName= userLastNameText.getText();
        String userGender= userGenderCombo.getValue();
        String userPhoneNumber= userPhoneNumberText.getText();
        String userEmail= userEmailText.getText();
        String userPassword= userPasswordText.getText();
        String userName=usernameText.getText();
        if(userId.isEmpty()||userFirstName.isEmpty()|userLastName.isEmpty()|userGender.isEmpty()||userPhoneNumber.isEmpty()||userEmail.isEmpty()||userPassword.isEmpty()||userName.isEmpty()){
            NotificationAlert addAlert=new NotificationAlert();
            addAlert.showWarningAlert("Empty Field(s)","Fill All the Fields To Update  User");
            return;
        }
        String update="update libraryuser set userfirstname='"+userFirstName+"',userlastname='"+userLastName+"',usergender='"+userGender+"',userphone='"+userPhoneNumber+"',useremail='"+userEmail+"',username='"+userName+"',userpassword='"+userPassword+"' where userid='"+userId+"'";
        if(dc.execAction(update)){
            refreshTable();
            clearFields();
            NotificationAlert addAlert=new NotificationAlert();
            addAlert.showInformationAlert("Success","User Was Updated Successfully");
        }else{
            NotificationAlert error=new NotificationAlert();
            error.showErrorAlert("ERROR","Fail To Update User Try Again");
        }
    }
    ObservableList<User> userList= FXCollections.observableArrayList();
    private ObservableList<User> getUsers(){

        String query="select * from libraryuser";
        try {
            Connection conn= DatabaseConnection.ConnectDatabase();
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
           User user;
            while (resultSet.next()){
                int userId=resultSet.getInt("userid");
                 String firstName=resultSet.getString("userfirstname");
                String lastName=resultSet.getString("userlastname");
                 String gender=resultSet.getString("usergender");
                 String phone=resultSet.getString("userphone");
                 String email=resultSet.getString("useremail");
                 String username=resultSet.getString("username");
                String password=resultSet.getString("userpassword");
                user=new User(userId,firstName,lastName,gender,phone,email,username,password);
                userList.add(user);
            }

        }catch (Exception e){

        }
        return userList;
    }
    private  void showUsers(){
        ObservableList<User> showUsers=getUsers();
       userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
       userGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
       userPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
       usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
       userPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
       userTabTableView.setItems(showUsers);
    }
private void refreshTable(){
    userList.clear();
    String query="select * from libraryuser";
    try {
        Connection conn= DatabaseConnection.ConnectDatabase();
        Statement statement=conn.createStatement();
        ResultSet resultSet=statement.executeQuery(query);
        User user;
        while (resultSet.next()){
            int userId=resultSet.getInt("userid");
            String firstName=resultSet.getString("userfirstname");
            String lastName=resultSet.getString("userlastname");
            String gender=resultSet.getString("usergender");
            String phone=resultSet.getString("userphone");
            String email=resultSet.getString("useremail");
            String username=resultSet.getString("username");
            String password=resultSet.getString("userpassword");
            user=new User(userId,firstName,lastName,gender,phone,email,username,password);
            userList.add(user);
        }

    }catch (Exception e){

    }
}
private void clearFields(){
    userIdText.clear();
    userFirstNameText.clear();
    userLastNameText.clear();
    userGenderCombo.setValue(null);
    userPhoneNumberText.clear();
    userEmailText.clear();
    userPasswordText.clear();
    usernameText.clear();
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dc=DatabaseConnection.getInstance();
        showUsers();
        ObservableList<String> usergender= FXCollections.observableArrayList("Male","Female");
        userGenderCombo.getItems().addAll(usergender);
    }
    private boolean isduplicateUser(){
        String query="select * from libraryuser where userid='"+userIdText.getText()+"'";
        try{
            ResultSet rs;
            rs=dc.execQuery(query);
            if(rs.next()){
                NotificationAlert alert=new NotificationAlert();
                alert.showWarningAlert("Dublicate","User Already Exist");
                return true;
            }
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }
    public void showUserInformation(MouseEvent mouseEvent) {
        User user=userTabTableView.getSelectionModel().getSelectedItem();
        userIdText.setText(""+user.getId());
        userFirstNameText.setText(user.getFirstName());
        userLastNameText.setText(user.getLastName());
        userGenderCombo.setValue(user.getGender());
        userPhoneNumberText.setText(user.getPhone());
        userEmailText.setText(user.getEmail());
        userPasswordText.setText(user.getPassword());
        usernameText.setText(user.getUsername());
    }
}

