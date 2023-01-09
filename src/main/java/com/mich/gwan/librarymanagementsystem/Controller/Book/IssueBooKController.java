package com.mich.gwan.librarymanagementsystem.Controller.Book;

import com.mich.gwan.librarymanagementsystem.Controller.Member.MemberFineController;
import com.mich.gwan.librarymanagementsystem.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class IssueBooKController implements Initializable {
DatabaseConnection databaseConnection;
    @FXML
    private Label bookIdIssueLabel;

    @FXML
    private TextField bookIdIssueText;

    @FXML
    private Button issueBookButton;

    @FXML
    private GridPane issueBookGridPane;
@FXML
TableColumn<IssuedBook, Integer> issueIdColumn;
    @FXML
    private TableColumn<LibraryMember,String> issueBookIdColumn;

    @FXML
    private TableView<IssuedBook> issueBookTableView;


    @FXML
    private TableColumn<LibraryMember,String> issueBookTitleColumn;

    @FXML
    private TableColumn<LibraryMember, java.security.Timestamp> issueDateColumn;

    @FXML
    private TableColumn<LibraryMember, java.security.Timestamp> issueReturnDateColumn;
    @FXML
    private TableColumn<LibraryMember, java.security.Timestamp>  returnedatecolumn;

    @FXML
    private TableColumn<LibraryMember,String> issueStudentDepartmentColumn;

    @FXML
    private TableColumn<LibraryMember,String> issueStudentEmailColumn;

    @FXML
    private TableColumn<LibraryMember,String> issueStudentFirstNameColumn;

    @FXML
    private TableColumn<LibraryMember,String> issueStudentGenderColumn;

    @FXML
    private TableColumn<LibraryMember,String> issueStudentIdColumn;

    @FXML
    private TableColumn<LibraryMember,String> issueStudentLastNameColumn;

    @FXML
    private TableColumn<LibraryMember,String> issueStudentPhoneNoColumn;

    @FXML
    private Button refreshIssueFieldsButton;

    @FXML
    private TextField serchIssueBookText;

    @FXML
    private Label studenIssuetIdLabel;

    @FXML
    private TextField studentIssueIdText;

    @FXML
    void issueBook(ActionEvent event) {
String bookId=bookIdIssueText.getText();
String student=studentIssueIdText.getText();
String bookStatus="Borrowed";
if(bookId.isEmpty()||student.isEmpty()){
    NotificationAlert empty=new NotificationAlert();
    empty.showWarningAlert("Empty","Student or Book Id is Empty");
    return;
}
        if(!isStudentExist()){
            NotificationAlert alert=new NotificationAlert();
            alert.showWarningAlert("Null","Student Doesn't Exist");
            studentIssueIdText.clear();
            return;
        }
if(!isbookExist()){
    NotificationAlert alert=new NotificationAlert();
    alert.showWarningAlert("Null","Book Doesn't Exist");
    bookIdIssueText.clear();
    return;
}

if(isdublicateIssuedBook()){
    return;
}
        LibrarySettings settings=new LibrarySettings();
int noOfDays=settings.getNumberOfDaysWithoutFine();
System.out.println(noOfDays);
String query1="update librarybook set bookstatus='"+bookStatus+"' where bookid='"+bookId+"'";
String query2="insert into issuebook(bookid,memberid,duedate) values('"+bookId+"','"+student+"',timestampadd(DAY,'"+noOfDays+"',current_timestamp))";

        if(databaseConnection.execAction(query1)&&databaseConnection.execAction(query2)){
    NotificationAlert issueAlert=new NotificationAlert();
    issueAlert.showInformationAlert("Success"," Book Was Issued Successfully");
   refreshTable();
    clearfields();
}else {
    NotificationAlert error=new NotificationAlert();
    error.showErrorAlert("Error","Failed Try Again");
}
    }

    private void refreshTable() {
        issuedBooksList.clear();
        try {
            String query="SELECT librarybook.bookid,librarybook.booktitle,librarybook.bookcategory,issuebook.issueid,issuebook.issuedate,issuebook.duedate,librarymember.memberid,librarymember.memberfirstname,librarymember.memberlastname,librarymember.membergender,librarymember.memberemail,librarymember.memberphone,librarymember.memberdepartment FROM librarybook JOIN issuebook ON librarybook.bookid=issuebook.bookid JOIN librarymember ON  librarymember.memberid= issuebook.memberid" ;
            Connection conn= DatabaseConnection.ConnectDatabase();
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            IssuedBook issuedBook;
            while (resultSet.next()){
                issuedBook=new IssuedBook();
                issuedBook.setBookId(resultSet.getString("bookid"));
               issuedBook.setBookTitle(resultSet.getString("booktitle"));
               issuedBook.setBookCategory(resultSet.getString("bookcategory"));
                issuedBook.setIssueDate(resultSet.getTimestamp("issuedate"));
                issuedBook.setDueDate(resultSet.getTimestamp("duedate"));
                issuedBook.setReturnedDate(resultSet.getTimestamp("returnedate"));
                issuedBook.setMemberId(resultSet.getInt("memberid"));
                issuedBook.setFirstName(resultSet.getString("memberfirstname"));
                issuedBook.setLastName(resultSet.getString("memberlastname"));
                issuedBook.setGender(resultSet.getString("membergender"));
                issuedBook.setEmail(resultSet.getString("memberemail"));
                issuedBook.setPhone(resultSet.getString("memberphone"));
                issuedBook.setDepartment(resultSet.getString("memberdepartment"));
                issuedBooksList.add(issuedBook);
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();

        }
    }

    private boolean isdublicateIssuedBook(){
        String bookStatus="Borrowed";
        String query="select * from librarybook where bookstatus='"+bookStatus+"'";
        try{
            ResultSet rs;
            rs=databaseConnection.execQuery(query);
            if(rs.next()){
                NotificationAlert alert=new NotificationAlert();
                alert.showWarningAlert("Duplicate","Book Already Issued");
                return true;
            }
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }
    @FXML
    void refreshIssueBookFields(ActionEvent event) {
clearfields();
    }
    private void clearfields(){
        this.bookIdIssueText.clear();
        this.studentIssueIdText.clear();
    }

    @FXML
    void serchIsssuedBook(ActionEvent event) {

    }
    ObservableList<IssuedBook> issuedBooksList= FXCollections.observableArrayList();
    private ObservableList<IssuedBook> getIssuedBooks(){

        try {
            String query="SELECT librarybook.bookid,librarybook.booktitle,librarybook.bookcategory,issuebook.issueid,issuebook.issuedate,issuebook.duedate,issuebook.returnedate,librarymember.memberid,librarymember.memberfirstname,librarymember.memberlastname,librarymember.membergender,librarymember.memberemail,librarymember.memberphone,librarymember.memberdepartment FROM librarybook JOIN issuebook ON librarybook.bookid=issuebook.bookid JOIN librarymember ON  librarymember.memberid= issuebook.memberid" ;
            Connection conn= DatabaseConnection.ConnectDatabase();
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
          IssuedBook issuedBook;
            while (resultSet.next()){
                issuedBook=new IssuedBook();
                issuedBook=new IssuedBook();
                issuedBook.setBookId(resultSet.getString("bookid"));
                issuedBook.setBookTitle(resultSet.getString("booktitle"));
                issuedBook.setBookCategory(resultSet.getString("bookcategory"));
                issuedBook.setIssueBookid(resultSet.getInt("issueid"));
                issuedBook.setIssueDate(resultSet.getTimestamp("issuedate"));
                issuedBook.setDueDate(resultSet.getTimestamp("duedate"));
                issuedBook.setReturnedDate(resultSet.getTimestamp("returnedate"));
                issuedBook.setMemberId(resultSet.getInt("memberid"));
                issuedBook.setFirstName(resultSet.getString("memberfirstname"));
                issuedBook.setLastName(resultSet.getString("memberlastname"));
                issuedBook.setGender(resultSet.getString("membergender"));
                issuedBook.setEmail(resultSet.getString("memberemail"));
                issuedBook.setPhone(resultSet.getString("memberphone"));
                issuedBook.setDepartment(resultSet.getString("memberdepartment"));
              issuedBooksList.add(issuedBook);
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();

        }
        return issuedBooksList;
    }
    private  void showIssuedBooks(){
        ObservableList<IssuedBook> showIssuedBooks=getIssuedBooks();
        issueBookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        issueIdColumn.setCellValueFactory(new PropertyValueFactory<>("issueBookid"));
        issueBookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
       issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
       issueReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
       returnedatecolumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        issueStudentIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        issueStudentEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        issueStudentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        issueStudentLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
       issueStudentGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        issueStudentPhoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
       issueStudentDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
       issueBookTableView.setItems(showIssuedBooks);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection=DatabaseConnection.getInstance();
        showIssuedBooks();
    }
    private boolean isbookExist(){
        String bookid=bookIdIssueText.getText();
        String query="select * from librarybook where bookid='"+bookid+"'";
        try {
            ResultSet rs;
            rs=databaseConnection.execQuery(query);
            if (rs.next()){
                return true;
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }
    private boolean isStudentExist(){
        String memberRegNumber=studentIssueIdText.getText();
        String query="select * from librarymember where memberid='"+memberRegNumber+"'";
        try {
            ResultSet rs;
            rs=databaseConnection.execQuery(query);
            if (rs.next()){
                return true;
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }

    public void fineMember(ActionEvent actionEvent) throws IOException {
        IssuedBook selectedForFine = issueBookTableView.getSelectionModel().getSelectedItem();
        if(selectedForFine==null){
            NotificationAlert empty=new NotificationAlert();
            empty.showInformationAlert("Empty","No member Selected for Fine");
        }
        else{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Member/MemberFine.fxml"));
            Parent parent=loader.load();
            MemberFineController memberFineController=(MemberFineController) loader.getController();
            memberFineController.fillFineUI(selectedForFine);
            Stage stage=new Stage(StageStyle.DECORATED);
            stage.setTitle("Member Fine Window");
            stage.setScene(new Scene(parent));
            stage.show();

        }
    }
}

