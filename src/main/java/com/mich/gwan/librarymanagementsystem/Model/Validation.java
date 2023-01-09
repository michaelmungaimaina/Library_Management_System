package com.mich.gwan.librarymanagementsystem.Model;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.*;

public class Validation {
    public  boolean isEmptyTextField(TextField textField) {
        if (textField.getText().isEmpty() | textField.getText().isBlank() | textField.getText().equals("")) {
            return true;
        }
        return false;
    }
    public  boolean isEmptyCombo(ComboBox<String> comboBox){
        if(comboBox.getValue()==null){
            return true;
        }
        return false;
    }
    }

