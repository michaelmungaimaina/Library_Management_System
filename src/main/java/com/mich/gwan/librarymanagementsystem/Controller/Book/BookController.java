package com.mich.gwan.librarymanagementsystem.Controller.Book;

import com.mich.gwan.librarymanagementsystem.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookController  implements Initializable {
    // connerct database
    DatabaseConnection databaseConnection;
    @FXML
    private Button addBookButton;
    @FXML
    private Button issuedBooksButton;

    @FXML
    private Label authorFirstNameLabel;

    @FXML
    private TextField authorFirstNameText;
    @FXML
    private TextField bookYearOfPublicationText;

    @FXML
    private Label authorLastNameLabel;

    @FXML
    private TextField authorLastNameText;

    @FXML
    private TableColumn<Book,String> bookAuthorFirstNameColumn;

    @FXML
    private TableColumn<Book,String > bookAuthorLastNameColumn;

    @FXML
    private TableColumn<Book, String> bookCategoryColumn;

    @FXML
    private ComboBox<String> bookCategoryCombo;

    @FXML
    private Label bookCategoryLabel;

    @FXML
    private TableColumn<Book, String> bookIdColumn;
    @FXML
    private TableColumn<Book, Integer> yearOfPublication;

    @FXML
    private Label bookIdLabel;

    @FXML
    private TextField bookIdText;

    @FXML
    private TableColumn<Book, Integer> bookNumberOfPagesColumn;

    @FXML
    private Label bookNumberOfPagesLabel;

    @FXML
    private TextField bookNumberOfPagesText;

    @FXML
    private TableColumn<Book, String> bookPublisherColumn;

    @FXML
    private Label bookPublisherLabel;

    @FXML
    private TextField bookPublisherText;

    @FXML
    private TableColumn<Book, String> bookStatusColumn;

    @FXML
    private GridPane bookTabGridPane;

    @FXML
    private TableView<Book> bookTabTableView;

    @FXML
    private TableColumn<Book, String> bookTitleColumn;

    @FXML
    private Label bookTitleLabel;

    @FXML
    private TextField bookTitleText;

    @FXML
    private ComboBox<Integer> bookYearOfPublicationCombo;

    @FXML
    private Label bookYearOfPublicationLabel;

    @FXML
    private Button deleteBookButton;

    @FXML
    private Button issueBookButton;

    @FXML
    private AnchorPane libraryRootAnchorPane;

    @FXML
    private Button refreshBookFieldsButton;

    @FXML
    private Button studentButton;

    @FXML
    private Button submit_RenewBookButton;

    @FXML
    private Button updateBookButton;

    @FXML
    private MenuBar userMainWindowMenuBar;

    @FXML
    private Button usersButton,exportButton,fineListButton;
    Boolean isInEditMode=false;
    @FXML
    void addNewBook(ActionEvent event) {
        String bookId=bookIdText.getText();
        String bookTitle=bookTitleText.getText();
        String authorFirstName=authorFirstNameText.getText();
        String authorLastName=authorLastNameText.getText();
        String bookPublisher=bookPublisherText.getText();
        String yearOfPublication=bookYearOfPublicationText.getText();
        String pages=bookNumberOfPagesText.getText();
        String bookCategory=bookCategoryCombo.getValue();
        String bookStatus="Available" ;
        String query="insert into librarybook values('"+bookId+"','"+bookTitle+"','"+authorFirstName+"','"+authorLastName+"','"+bookPublisher+"','"+yearOfPublication+"','"+pages+"','"+bookCategory+"','"+bookStatus+"')";
        if(isInEditMode){
           updateBook();
           return;
        }
        if(isEmptyBookId()||isEmptyBookTitle()||isEmptyAuthorFirstName()||isEmptyAuthorLastName()||isEmptyBookPublisher()||bookYearOfPublicationText.getText().isEmpty()||isEmptyBookCategory()||isEmptyBookNumberOfPages()){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Null","Fill All the Fields to Add Book");
            return;
        }
        if(isdublicateBook()){
            return;
        }
        if(!isValidBookAuthorFirstName()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Author Name");
            authorFirstNameText.requestFocus();
            return;
        }
        if(!isValidBookAuthorLastName()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Author Name");
            authorLastNameText.requestFocus();
            return;
        }
        if(!isValidBookYearOfPublication()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Year Of Publication");
            bookYearOfPublicationText.requestFocus();
            return;
        }
        if(!isValidBookNumberOfPages()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Book Number Of Pages");
            bookNumberOfPagesText.requestFocus();
            return;
        }
        if(!isValidBookTitle()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Book Title");
            bookTitleText.requestFocus();
            return;
        }
        if(!isValidBookPublisher()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Book Publisher");
            bookPublisherText.requestFocus();
            return;
        }

        if(databaseConnection.execAction(query)){
            refreshTable();
            clearFields();
            NotificationAlert failDelete=new NotificationAlert();
            failDelete.showInformationAlert("Success","New Book was Added Successfully");
        }else {
            System.out.println("Error Occurred");
            NotificationAlert failDelete=new NotificationAlert();
            failDelete.showErrorAlert("Error","Fail To Add Book");
        }

    }

    @FXML
    void deleteBook(ActionEvent event) {
        Book selectedForDelete=bookTabTableView.getSelectionModel().getSelectedItem();
        String bookId=bookIdText.getText();
        String deletequery="delete from librarybook where bookid='"+bookId+"'";
        if(selectedForDelete==null){
            NotificationAlert emptyidAlert=new NotificationAlert();
            emptyidAlert.showWarningAlert("Null","Enter Book Id To delete Book");
            bookIdText.requestFocus();
            return;
        }
       else {
           Alert deleteAlert=new Alert(Alert.AlertType.CONFIRMATION);
           deleteAlert.setTitle("Delete Book");
           deleteAlert.setHeaderText(null);
           deleteAlert.setContentText("Do you want to Delete this Book....?"+selectedForDelete.getBookTitle());
           if(deleteAlert.showAndWait().get()==ButtonType.OK){
               if(databaseConnection.execAction(deletequery)){
                   refreshTable();
                   clearFields();
                   NotificationAlert failDelete=new NotificationAlert();
                   failDelete.showInformationAlert("Success","Book was deleted successfully");
               }else{
                   System.out.println("Book Deletion Failed Try Again");
                   NotificationAlert failDelete=new NotificationAlert();
                   failDelete.showErrorAlert("Error","Fail To Delete Book");
               }
           }else {
               NotificationAlert cancel=new NotificationAlert();
               cancel.showInformationAlert("Cancel","You have cancel Delete Operation");
           }
        }

    }

    @FXML
    void refreshBookFields(ActionEvent event) {
        refreshTable();
        bookIdText.clear();
        bookTitleText.clear();
        authorLastNameText.clear();
        authorFirstNameText.clear();
        bookPublisherText.clear();
        bookYearOfPublicationText.clear();
        bookCategoryCombo.setValue(null);
        bookNumberOfPagesText.clear();
    }

    void showBookInformation() {
        Book bookSelectedForEdit=bookTabTableView.getSelectionModel().getSelectedItem();
        if(bookSelectedForEdit==null){
            NotificationAlert empty=new NotificationAlert();
            empty.showErrorAlert("Empty Selection","Select Book To Update");
        }
        else{
            bookIdText.setText(bookSelectedForEdit.getBookId());
            bookTitleText.setText(bookSelectedForEdit.getBookTitle());
            authorLastNameText.setText(bookSelectedForEdit.getAuthorLastName());
            authorFirstNameText.setText(bookSelectedForEdit.getAuthorFirstName());
            bookPublisherText.setText(bookSelectedForEdit.getBookPublisher());
            bookCategoryCombo.setValue(bookSelectedForEdit.getBookCategory());
            bookNumberOfPagesText.setText(""+bookSelectedForEdit.getBookNumberOfPages());
            isInEditMode=Boolean.TRUE;
        }

    }

    @FXML
    void showIssueBookWindow(ActionEvent event) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Book/IssueBook.fxml"),"ISSUED BOOKS",null);

    }

    @FXML
    void showSubmit_renewBookWindow(ActionEvent event) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Book/Submit_Renew_Book.fxml"),"ISSUED BOOKS",null);

    }


    void updateBook() {
        String bookId=bookIdText.getText();
        String bookTitle=bookTitleText.getText();
        String authorFirstName=authorFirstNameText.getText();
        String authorLastName=authorLastNameText.getText();
        String bookPublisher=bookPublisherText.getText();
        String yearOfPublication=bookYearOfPublicationText.getText();
        String pages=bookNumberOfPagesText.getText();
        String bookCategory=bookCategoryCombo.getValue();
        if(isEmptyBookId()||isEmptyBookTitle()||isEmptyAuthorFirstName()||isEmptyAuthorLastName()||isEmptyBookPublisher()||bookYearOfPublicationText.getText().isEmpty()||isEmptyBookCategory()||isEmptyBookNumberOfPages()){
            NotificationAlert empty=new NotificationAlert();
            empty.showWarningAlert("Null","Fill All the Fields to update Book");
            return;
        }
        if(!isValidBookAuthorFirstName()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Author Name");
            authorFirstNameText.requestFocus();
            return;
        }
        if(!isValidBookAuthorLastName()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Author Name");
            authorLastNameText.requestFocus();
            return;
        }
        if(!isValidBookYearOfPublication()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Year Of Publication");
            bookYearOfPublicationText.requestFocus();
            return;
        }
        if(!isValidBookNumberOfPages()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Book Number Of Pages");
            bookNumberOfPagesText.requestFocus();
            return;
        }
        if(!isValidBookTitle()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Book Title");
            bookTitleText.requestFocus();
            return;
        }
        if(!isValidBookPublisher()){
            NotificationAlert invalidAlert=new NotificationAlert();
            invalidAlert.showWarningAlert("Invalid","Enter Valid Book Publisher");
            bookPublisherText.requestFocus();
            return;
        }

        String updateQuery="update librarybook set booktitle='"+bookTitle+"',authorfirstname='"+authorFirstName+"',authorlastname='"+authorLastName+"',bookpublisher='"+bookPublisher+"',yearofpublication='"+yearOfPublication+"',numberofpages='"+pages+"',bookcategory='"+bookCategory+"' where bookid='"+bookId+"'";
        if(databaseConnection.execAction(updateQuery)){
            refreshTable();
            clearFields();
            NotificationAlert updateAlert=new NotificationAlert();
            updateAlert.showInformationAlert("Success","Book details Updated Successfully");
            System.out.println("Book Has Been Updated");
        }else {
            NotificationAlert updateAlert=new NotificationAlert();
            updateAlert.showErrorAlert("Error","Failed Try Again");
            System.out.println("Book Update Failed");
        }
    }
    ObservableList<Book> bookList= FXCollections.observableArrayList();
    private ObservableList<Book> getBooks(){
        String query="select * from librarybook";
        try {
            Connection conn= DatabaseConnection.ConnectDatabase();
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            Book book;
            while (resultSet.next()){
                String  bookId=resultSet.getString("bookid");
                String bookTitle=resultSet.getString("booktitle");
                String authorFirstName=resultSet.getString("authorfirstname");
                String authorLastName=resultSet.getString("authorlastname");
                String bookPublisher=resultSet.getString("bookpublisher");
                int yearOfPublication=resultSet.getInt("yearofpublication");
                int pages=resultSet.getInt("numberofpages");
                String bookCategory=resultSet.getString("bookcategory");
                String status=resultSet.getString("bookstatus");
                book=new Book(bookId,bookTitle,authorFirstName,authorLastName,bookPublisher,yearOfPublication,pages,bookCategory,status);
                bookList.add(book);
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();

        }
        return bookList;
    }
    private  void showBooks(){
        ObservableList<Book> showBooks=getBooks();
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookAuthorFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorFirstName"));
        bookAuthorLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorLastName"));
        bookPublisherColumn.setCellValueFactory(new PropertyValueFactory<>("bookPublisher"));
        yearOfPublication.setCellValueFactory(new PropertyValueFactory<>("yearOfPublication"));
        bookNumberOfPagesColumn.setCellValueFactory(new PropertyValueFactory<>("bookNumberOfPages"));
        bookCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        bookStatusColumn.setCellValueFactory(new PropertyValueFactory<>("bookStatus"));
        bookTabTableView.setItems(showBooks);
    }
    public void refreshTable(){
        bookList.clear();
        String query="select * from librarybook";
        try {
            Connection conn= DatabaseConnection.ConnectDatabase();
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            Book book;
            while (resultSet.next()){
                String bookId=resultSet.getString("bookid");
                String bookTitle=resultSet.getString("booktitle");
                String authorFirstName=resultSet.getString("authorfirstname");
                String authorLastName=resultSet.getString("authorlastname");
                String bookPublisher=resultSet.getString("bookpublisher");
                int yearOfPublication=resultSet.getInt("yearofpublication");
                int pages=resultSet.getInt("numberofpages");
                String bookCategory=resultSet.getString("bookcategory");
                String status=resultSet.getString("bookstatus");
                book=new Book(bookId,bookTitle,authorFirstName,authorLastName,bookPublisher,yearOfPublication,pages,bookCategory,status);
                bookList.add(book);
            }

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();

        }
    }
    private void clearFields(){
        bookIdText.clear();
        bookTitleText.clear();
        authorLastNameText.clear();
        authorFirstNameText.clear();
        bookPublisherText.clear();
        bookCategoryCombo.setValue(null);
        bookNumberOfPagesText.clear();
        bookYearOfPublicationText.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseConnection=DatabaseConnection.getInstance();
        showBooks();
        ObservableList<String> bookCategory= FXCollections.observableArrayList("Law","Medicine","Engineering","Statistics","ICT");
        bookCategoryCombo.getItems().addAll(bookCategory);
    }

    private boolean isEmptyBookId(){
        String bookId=bookIdText.getText();
        if(bookId.isEmpty()|bookId.isBlank()|bookId.equals("")){
            return true;
        }else {
            return false;
        }

    }
    private boolean isEmptyBookTitle(){
        String bookTitle=bookTitleText.getText();
        if(bookTitle.isEmpty()|bookTitle.isBlank()|bookTitle.equals("")){
            return true;
        }else {
            return false;
        }
    }
    private boolean isEmptyAuthorFirstName(){
        String authorFirstName=authorFirstNameText.getText();
        if(authorFirstName.isEmpty()|authorFirstName.isBlank()|authorFirstName.equals("")){
            return true;
        }else {
            return false;
        }
    }
    private boolean isEmptyAuthorLastName(){
        String authorLastName=authorLastNameText.getText();
        if(authorLastName.isEmpty()|authorLastName.isBlank()|authorLastName.equals("")){
            return true;
        }else {
            return false;
        }
    }
    private boolean isEmptyBookPublisher(){
        String bookPublisher=bookPublisherText.getText();
        if(bookPublisher.isEmpty()|bookPublisher.isBlank()|bookPublisher.equals("")){
            return true;
        }else {
            return false;
        }
    }
    private boolean isEmptyYearOfPublication(){
        String bookYearOfPublication= String.valueOf(bookYearOfPublicationCombo.getValue());
        if(bookYearOfPublication==null){
            return true;
        }else {
            return false;
        }
    }
    private boolean isEmptyBookNumberOfPages(){
        String bookPages=bookNumberOfPagesText.getText();
        if(bookPages.isEmpty()|bookPages.isBlank()|bookPages.equals("")){
            return true;
        }else {
            return false;
        }
    }
    private boolean isEmptyBookCategory(){
        String bookCategory=bookCategoryCombo.getValue();
        if(bookCategory==null){
            return true;
        }else {
            return false;
        }
    }

    private boolean isValidBookTitle(){
        Pattern titlePattern=Pattern.compile("[A-Za-z]") ;
        Matcher patternMatcher=titlePattern.matcher(bookTitleText.getText());
        if(patternMatcher.find()&&patternMatcher.group().matches(bookTitleText.getText())){
            return true;
        }else {
            return true;
        }


    }
    private boolean isValidBookAuthorFirstName(){
        Pattern titlePattern=Pattern.compile("[A-Za-z]") ;
        Matcher patternMatcher=titlePattern.matcher(authorFirstNameText.getText());
        if(patternMatcher.find()&&patternMatcher.group().matches(authorFirstNameText.getText())){
            return true;
        }else {
            return true;
        }


    }
    private boolean isValidBookAuthorLastName(){
        Pattern titlePattern=Pattern.compile("[A-Za-z]") ;
        Matcher patternMatcher=titlePattern.matcher(authorLastNameText.getText());
        if(patternMatcher.find()&&patternMatcher.group().matches(authorLastNameText.getText())){
            return true;
        }else {
            return true;
        }


    }
    private boolean isValidBookPublisher(){
        Pattern titlePattern=Pattern.compile("[A-Za-z]") ;
        Matcher patternMatcher=titlePattern.matcher(bookPublisherText.getText());
        if(patternMatcher.find()&&patternMatcher.group().matches(bookPublisherText.getText())){
            return true;
        }else {
            return true;
        }


    }
    private boolean isValidBookYearOfPublication(){
        Pattern titlePattern=Pattern.compile("[A-Za-z]") ;
        Matcher patternMatcher=titlePattern.matcher(bookTitleText.getText());
        if(patternMatcher.find()&&patternMatcher.group().matches(bookTitleText.getText())){
            return true;
        }else {
            return true;
        }


    }
    private boolean isValidBookNumberOfPages(){
        Pattern titlePattern=Pattern.compile("[1-9]{5}") ;
        Matcher patternMatcher=titlePattern.matcher(bookNumberOfPagesText.getText());
        if(patternMatcher.find()&&patternMatcher.group().matches(bookNumberOfPagesText.getText())){
            return true;
        }else {
            return true;
        }


    }
    private boolean isValidYearOfPublication(){
        Pattern titlePattern=Pattern.compile("[1-9]{4}") ;
        Matcher patternMatcher=titlePattern.matcher(bookYearOfPublicationText.getText());
        if(patternMatcher.find()&&patternMatcher.group().matches(bookYearOfPublicationText.getText())){
            return true;
        }

        return false;
    }
    private boolean isdublicateBook(){
        String query="select * from librarybook where bookid='"+bookIdText.getText()+"'";
        try{
            ResultSet rs;
            rs=databaseConnection.execQuery(query);
            if(rs.next()){
                NotificationAlert alert=new NotificationAlert();
                alert.showWarningAlert("Duplicate","Book Already Exist");
                return true;
            }
        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }

    public void performUpdateOperation(ActionEvent actionEvent) {
        showBookInformation();
    }

    public void exportBooksToExcel(ActionEvent actionEvent) {
        ExportToExcel<Book> bookExportToExcel=new ExportToExcel<>();
        bookExportToExcel.export(bookTabTableView);
    }

    public void showFineListWindow(ActionEvent actionEvent) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Member/MemberFineList.fxml"),"Library Fine",null);
    }
}

