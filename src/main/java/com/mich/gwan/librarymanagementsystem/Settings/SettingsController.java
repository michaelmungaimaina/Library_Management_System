package com.mich.gwan.librarymanagementsystem.Settings;

import com.mich.gwan.librarymanagementsystem.Model.LibrarySettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private GridPane controlsGridPane;

    @FXML
    private Label finePerDayLabel;

    @FXML
    private TextField finePerDayTextField;

    @FXML
    private Label noOfDaysWithoutFineLabel;

    @FXML
    private TextField noOfDaysWithoutFineTextField;

    @FXML
    private Label registrationFeeLabel;

    @FXML
    private TextField registrationFeeTextField;

    @FXML
    private AnchorPane rootAnchorpane;

    @FXML
    private Button saveButton;

    @FXML
    private Label settingsLabel;

    @FXML
    private VBox settingsVBox;

    @FXML
    void cancelSettings(ActionEvent event) {

    }

    @FXML
    void saveSettings(ActionEvent event) {

      float registrationFee= Float.parseFloat(registrationFeeTextField.getText());
      float finePerDay= Float.parseFloat(finePerDayTextField.getText());
      int daysWithoutFine=Integer.parseInt(noOfDaysWithoutFineTextField.getText());
        LibrarySettings settings= LibrarySettings.getPreferences();
        settings.setNumberOfDaysWithoutFine(daysWithoutFine);
        settings.setFinePerDay(finePerDay);
        settings.setRegistrationFee(registrationFee);
       LibrarySettings.writeSettingsToFile(settings);
    }
    private void initDefaultValues() {
        LibrarySettings settings= LibrarySettings.getPreferences();
        this.registrationFeeTextField.setText(String.valueOf(settings.getRegistrationFee()));
        this.noOfDaysWithoutFineTextField.setText(String.valueOf(settings.getNumberOfDaysWithoutFine()));
        this.finePerDayTextField.setText(String.valueOf(settings.getFinePerDay()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initDefaultValues();
    }
}
