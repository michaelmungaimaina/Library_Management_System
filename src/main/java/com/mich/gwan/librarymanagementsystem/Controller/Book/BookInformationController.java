package com.mich.gwan.librarymanagementsystem.Controller.Book;

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

public class BookInformationController implements Initializable {
DatabaseConnection dc;
    @FXML
    private TextField bookInfoIdText;

    @FXML
    private ListView<String> bookInfoListView;

    @FXML
    private AnchorPane bookinfoAnchorPane;

    @FXML
    void loadBookInformation(ActionEvent event) {
        ObservableList<String> bookInfo= FXCollections.observableArrayList();
        if(this.bookInfoIdText.getText().isEmpty()){
            NotificationAlert empty=new NotificationAlert();
            empty.showInformationAlert("Empty","Enter Book Id To view Book Info");
            bookInfoIdText.requestFocus();
            return;
        }
        String bookId=bookInfoIdText.getText();
        String query="select * from librarybook where bookid='"+bookId+"'";

        try {
            ResultSet rs=dc.execQuery(query);
            while (rs.next()){
                bookInfo.add("Book Id:   "+rs.getString("bookid"));
                bookInfo.add("Book Title:   "+rs.getString("booktitle"));
                bookInfo.add("Author Name:   "+rs.getString("authorfirstname")+"      "+rs.getString("authorlastname"));
                bookInfo.add("Book Publisher:   "+rs.getString("bookpublisher"));
                bookInfo.add("Book Category:   "+rs.getString("bookcategory"));
                bookInfo.add("Book Status:   "+rs.getString("bookstatus"));
                bookInfo.add(" No of Pages:   "+rs.getInt("numberofpages"));
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();


        }
       bookInfoListView.getItems().setAll(bookInfo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dc=DatabaseConnection.getInstance();
    }
}
