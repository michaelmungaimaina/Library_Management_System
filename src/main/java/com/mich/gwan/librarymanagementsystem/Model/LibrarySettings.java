package com.mich.gwan.librarymanagementsystem.Model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarySettings {
    private  int numberOfDaysWithoutFine=14;
    private float finePerDay=50;
    private float registrationFee=500;


    public double getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(float registrationFee) {
        this.registrationFee = registrationFee;
    }

    public int getNumberOfDaysWithoutFine() {
        return numberOfDaysWithoutFine;
    }

    public void setNumberOfDaysWithoutFine(int numberOfDaysWithoutFine) {
        this.numberOfDaysWithoutFine = numberOfDaysWithoutFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }
    public static void initConfig() {
        FileWriter writer = null;
        try {
           LibrarySettings setttings = new LibrarySettings();
            Gson gson = new Gson();
            writer = new FileWriter("settings.txt");
            gson.toJson(setttings, writer);
        } catch (IOException ex) {
            Logger.getLogger(LibrarySettings.class.getName()).log(Level.SEVERE, (String)null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ioException) {
                Logger.getLogger(LibrarySettings.class.getName()).log(Level.SEVERE, (String)null, ioException);
            }

        }

    }

    public static LibrarySettings getPreferences() {
        Gson gson = new Gson();
        LibrarySettings settings=new LibrarySettings();

        try {
          settings = gson.fromJson(new FileReader("settings.txt"), LibrarySettings.class);
        } catch (FileNotFoundException exp) {
            initConfig();
            Logger.getLogger(LibrarySettings.class.getName()).log(Level.SEVERE, (String)null, exp);
        }

        return settings;
    }

    public static void writeSettingsToFile(LibrarySettings settings) {
        FileWriter writer = null;

        try {
            Gson gson = new Gson();
            writer = new FileWriter("settings.txt");
            gson.toJson(settings, writer);
          NotificationAlert notificationAlert=new NotificationAlert();
          notificationAlert.showInformationAlert("Success","Settings saved successfully");
        } catch (IOException exp) {
            Logger.getLogger(LibrarySettings.class.getName()).log(Level.SEVERE, (String)null, exp);
            NotificationAlert notificationAlert=new NotificationAlert();
            notificationAlert.showInformationAlert("Failed","Settings not  saved Try Again");
        } finally {
            try {
                writer.close();
            } catch (IOException var10) {
                Logger.getLogger(LibrarySettings.class.getName()).log(Level.SEVERE, (String)null, var10);
            }

        }

    }
}
