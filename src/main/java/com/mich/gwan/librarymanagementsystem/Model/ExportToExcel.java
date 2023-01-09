package com.mich.gwan.librarymanagementsystem.Model;

import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportToExcel<T> {
    public void export(TableView<T> tableView)  throws  NullPointerException{
        //workbook object
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        //SpreadSheet Object
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("sheet 1");
        //Creating a Row
        XSSFRow firstRow = xssfSheet.createRow(0);
        //set titles to the columns
        for (int i = 0; i <tableView.getColumns().size(); i++) {
            firstRow.createCell(i).setCellValue(tableView.getColumns().get(i).getText());
        }
        for (int row = 0; row < tableView.getItems().size(); row++) {
            XSSFRow hssfRow = xssfSheet.createRow(row + 1);

            for (int col = 0; col <tableView.getColumns().size(); col++) {
                Object cellValue = tableView.getColumns().get(col).getCellObservableValue(row).getValue();
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
}
