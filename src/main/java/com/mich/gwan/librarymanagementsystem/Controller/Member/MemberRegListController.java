package com.mich.gwan.librarymanagementsystem.Controller.Member;

import com.mich.gwan.librarymanagementsystem.Model.DatabaseConnection;
import com.mich.gwan.librarymanagementsystem.Model.LibraryMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.security.auth.callback.Callback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberRegListController implements Initializable {

    @FXML
    private Button clearButton;

    @FXML
    private Button exportButton;

    @FXML
    private TableColumn<LibraryMember, String> firstNameColumn;

    @FXML
    private TableColumn<LibraryMember, String> genderColumn;

    @FXML
    private TableColumn<LibraryMember, Integer> idColumn;

    @FXML
    private TableColumn<LibraryMember, String> lastNameColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private TableColumn<LibraryMember, Float> regAmountColumn;

    @FXML
    private TableColumn<LibraryMember, Timestamp> regDateColumn;

    @FXML
    private TableView<LibraryMember> registrationTableView;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField serchTextField;

    @FXML
    private VBox tableVBox;

    @FXML
    private HBox topHBox;

    @FXML
    void ExportRegistrationList(ActionEvent event) {
        //workbook object
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //SpreadSheet Object
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("sheet 1");
        //Creating a Row
        XSSFRow firstRow = xssfSheet.createRow(0);
        //set titles to the columns
        for (int i = 0; i <registrationTableView.getColumns().size(); i++) {
            firstRow.createCell(i).setCellValue(registrationTableView.getColumns().get(i).getText());
        }
        for (int row = 0; row < registrationTableView.getItems().size(); row++) {
            XSSFRow hssfRow = xssfSheet.createRow(row + 1);

            for (int col = 0; col <registrationTableView.getColumns().size(); col++) {
                Object cellValue = registrationTableView.getColumns().get(col).getCellObservableValue(row).getValue();
                try {
                    if (cellValue != null & Double.parseDouble(cellValue.toString()) != 0.0) {
                        hssfRow.createCell(col).setCellValue(Double.parseDouble(cellValue.toString()));
                    }
                } catch (NumberFormatException ex) {
                    hssfRow.createCell(col).setCellValue(cellValue.toString());
                }
            }
        }
        //Save Excel File and Close WorkBook
        try {
            Stage stage=new Stage();
            File file;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Attachee Information");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extFilter);
            file = fileChooser.showSaveDialog(stage);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss ddMMyyyy");
            String fileName = (dtf.format(LocalDateTime.now()) + " Data.xlsx");
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath() + ".xlsx");
            if (file == null) {
                return;
            }
            xssfWorkbook.write(out);
            xssfWorkbook.close();

        } catch (
                IOException e) {
            e.printStackTrace();
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("Success");
    }


    @FXML
    void clearSerchTextField(ActionEvent event) {
serchTextField.clear();
    }

    @FXML
    void refreshTable(ActionEvent event) {
registrationList.clear();
        try{
            String regQuery="select librarymember.memberfirstname,librarymember.memberlastname,librarymember.membergender,librarymember.memberid,memberegistration.regamount,memberegistration.regdate from librarymember join memberegistration on librarymember.memberid=memberegistration.memberid";
            ResultSet rs= dc.execQuery(regQuery);
            while (rs.next()){
                LibraryMember libraryMember=new LibraryMember();
                libraryMember.setFirstName(rs.getString("memberfirstname"));
                libraryMember.setLastName(rs.getString("memberlastname"));
                libraryMember.setGender(rs.getString("membergender"));
                libraryMember.setMemberId(rs.getInt("memberid"));
                libraryMember.setRegistrationAmount(rs.getFloat("regamount"));
                libraryMember.setRegistrationDate(rs.getTimestamp("regdate"));
                registrationList.add(libraryMember);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
ObservableList<LibraryMember> registrationList= FXCollections.observableArrayList();
    ObservableList<LibraryMember> getRegistrationList(){
        try{
            String regQuery="select librarymember.memberfirstname,librarymember.memberlastname,librarymember.membergender,librarymember.memberid,memberegistration.regamount,memberegistration.regdate from librarymember join memberegistration on librarymember.memberid=memberegistration.memberid";
            ResultSet rs= dc.execQuery(regQuery);
            while (rs.next()){
                LibraryMember libraryMember=new LibraryMember();
                libraryMember.setFirstName(rs.getString("memberfirstname"));
                libraryMember.setLastName(rs.getString("memberlastname"));
                libraryMember.setGender(rs.getString("membergender"));
                libraryMember.setMemberId(rs.getInt("memberid"));
                libraryMember.setRegistrationAmount(rs.getFloat("regamount"));
                libraryMember.setRegistrationDate(rs.getTimestamp("regdate"));
                registrationList.add(libraryMember);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return registrationList;
    }
    private void showRegistrationList(){
        ObservableList<LibraryMember> showRegistrationList=getRegistrationList();
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<LibraryMember,String>("firstName"));
       lastNameColumn.setCellValueFactory(new PropertyValueFactory<LibraryMember,String>("lastName"));
       genderColumn.setCellValueFactory(new PropertyValueFactory<LibraryMember,String>("gender"));
       idColumn.setCellValueFactory(new PropertyValueFactory<LibraryMember,Integer>("memberId"));
       regAmountColumn.setCellValueFactory(new PropertyValueFactory<LibraryMember,Float>("registrationAmount"));
        regDateColumn.setCellValueFactory(new PropertyValueFactory<LibraryMember,Timestamp>("registrationDate"));
        registrationTableView.setItems(showRegistrationList);
        libraryMembersRegistrationSearchFilter();
    }
    DatabaseConnection dc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dc=DatabaseConnection.getInstance();
        showRegistrationList();
    }
    private void libraryMembersRegistrationSearchFilter() {
        //initialize filtered list
        FilteredList<LibraryMember> filtereData = new FilteredList<>(registrationList, e -> true);
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
                    } else if(Float.toString(libraryMember.getRegistrationAmount()).toLowerCase().contains(toLowerCase)){
                        return true;
                    }
                    else if (libraryMember.getGender().toLowerCase().contains(toLowerCase)) {
                        return true;
                    } else

                        return false;
                });
            });
            final SortedList<LibraryMember> attachee = new SortedList<>(filtereData);
            attachee.comparatorProperty().bind(registrationTableView.comparatorProperty());
           registrationTableView.setItems(attachee);
        });
    }

}

