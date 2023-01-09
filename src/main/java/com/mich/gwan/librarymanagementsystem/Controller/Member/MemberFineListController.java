package com.mich.gwan.librarymanagementsystem.Controller.Member;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.ExportToExcel;
import com.mich.gwan.librarymanagementsystem.Model.IssuedBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MemberFineListController implements Initializable {

    @FXML
    private TableColumn<IssuedBook, Float> bookIdColumn;

    @FXML
    private TableColumn<IssuedBook, String> bookTitleColumn;

    @FXML
    private Button clearButton;

    @FXML
    private Button exportButton;

    @FXML
    private TableColumn<IssuedBook, Timestamp> fineDateColumn;

    @FXML
    private TableView<IssuedBook> fineTableView;

    @FXML
    private TableColumn<IssuedBook, String> firstNameColumn;

    @FXML
    private TableColumn<IssuedBook, Integer> idColumn;

    @FXML
    private TableColumn<IssuedBook,String> lastNameColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField serchTextField;

    @FXML
    private VBox tableVBox;

    @FXML
    private HBox topHBox;

    @FXML
    void clearSerchTextField(ActionEvent event) {
serchTextField.clear();
    }

    @FXML
    void exportFineList(ActionEvent event) {
        ExportToExcel<IssuedBook> fine=new ExportToExcel<>();
        fine.export(fineTableView);
        try{
            String fineQuery="select librarymember.memberfirstname,lybrarymember.memberlastname,librarybook.booktittle,bookfine.fineamount,bookfine.finedate from librarymember join issuebook on librarymember.memberid=issuebook.memberid join librarybook on librarybook.bookid join bookfine on bookfine.issueid=issuebook.issueid";
            ResultSet rs= dc.execQuery(fineQuery);
            while (rs.next()){
                IssuedBook issuedBook=new IssuedBook();
                issuedBook.setFirstName(rs.getString("memberfirstname"));
                issuedBook.setLastName(rs.getString("memberlastname"));
                issuedBook.setBookTitle(rs.getString("booktitle"));
                issuedBook.setFineDate(rs.getTimestamp("finedate"));
                issuedBook.setFineAmount(rs.getFloat("fineamount"));
                bookFineList.add(issuedBook);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void refreshTable(ActionEvent event) {
bookFineList.clear();
    }
    ObservableList<IssuedBook> bookFineList= FXCollections.observableArrayList();
    ObservableList<IssuedBook> getFineList(){
        try{
            String fineQuery="select librarymember.memberfirstname,lybrarymember.memberlastname,librarybook.booktittle,bookfine.fineamount,bookfine.finedate from librarymember join issuebook on librarymember.memberid=issuebook.memberid join librarybook on librarybook.bookid join bookfine on bookfine.issueid=issuebook.issueid";
            ResultSet rs= dc.execQuery(fineQuery);
            while (rs.next()){
                IssuedBook issuedBook=new IssuedBook();
                issuedBook.setFirstName(rs.getString("memberfirstname"));
                issuedBook.setLastName(rs.getString("memberlastname"));
                issuedBook.setBookTitle(rs.getString("booktitle"));
                issuedBook.setFineDate(rs.getTimestamp("finedate"));
                issuedBook.setFineAmount(rs.getFloat("fineamount"));
               bookFineList.add(issuedBook);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return bookFineList;
    }
    private void showFineList(){
        ObservableList<IssuedBook> showFineList=getFineList();
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<IssuedBook,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<IssuedBook,String>("lastName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<IssuedBook,Float>("fineAmount"));
        idColumn.setCellValueFactory(new PropertyValueFactory<IssuedBook,Integer>("memberId"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<IssuedBook,String>("bookTitle"));
        fineDateColumn.setCellValueFactory(new PropertyValueFactory<IssuedBook,Timestamp>("fineDate"));
       fineTableView.setItems(showFineList);
        //libraryMembersRegistrationSearchFilter();
    }
    DatabaseConnection dc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
dc=DatabaseConnection.getInstance();
showFineList();
    }
    private void libraryMembersRegistrationSearchFilter() {
        //initialize filtered list
        FilteredList<IssuedBook> filtereData = new FilteredList<>(bookFineList, e -> true);
        serchTextField.setOnKeyReleased(e -> {


            serchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filtereData.setPredicate((Predicate<? super IssuedBook>) fine -> {

                    if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String toLowerCase = newValue.toLowerCase();
                    if (fine.getFirstName().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else if (fine.getLastName().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else if (Integer.toString(fine.getMemberId()).toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else if(Float.toString(fine.getFineAmount()).toLowerCase().contains(toLowerCase)){
                        return true;
                    }
                    else if (fine.getBookTitle().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else

                        return false;
                });
            });
            final SortedList<IssuedBook> fine = new SortedList<>(filtereData);
            fine.comparatorProperty().bind(fineTableView.comparatorProperty());
            fineTableView.setItems(fine);
        });
    }

}

