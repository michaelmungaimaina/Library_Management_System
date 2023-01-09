package com.mich.gwan.librarymanagementsystem.Controller.Book;

import com.mich.gwan.librarymanagementsystem.Model.LibraryUtils;
import com.mich.gwan.librarymanagementsystem.Model.NotificationAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController  implements Initializable {
      @FXML
      private AnchorPane libraryRootAnchorPane;
    @FXML
    private Button studentButton;
    @FXML
    private MenuBar userMainWindowMenuBar;

    @FXML
    private Button usersButton,booksButton,exitButton;

    @FXML
    void showLibraryUsersWindow(ActionEvent event) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/User/User.fxml"),"USERS",null);

    }

    @FXML
    void showStudentsWindow(ActionEvent event) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Member/Member.fxml"),"STUDENTS",null);

    }

    @FXML
    void showSubmit_renewBookWindow(ActionEvent event) {

    }

    @FXML
    void updateBook(ActionEvent event) {

}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void closeMainWindow(ActionEvent actionEvent) {
        ((Stage)this.libraryRootAnchorPane.getScene().getWindow()).close();

    }

    public void showBookInformationWindow(ActionEvent actionEvent) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Book/BookInformation.fxml"),"Book Information",null);
    }

    public void showStudentInformationWindow(ActionEvent actionEvent) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Member/MemberInformation.fxml"),"Student Information",null);

    }

    public void showUserInformationWindow(ActionEvent actionEvent) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/User/UserInformation.fxml"),"User Information",null);

    }

    public void showInformationAboutDeveloper(ActionEvent actionEvent) {
    }


    public void showSttingsWindow(ActionEvent actionEvent) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/UserSettings.fxml"),"User Settings",null);
    }

    public void showBooksWindow(ActionEvent actionEvent) {
        LibraryUtils.loadWindow(getClass().getResource("/com/mich/gwan/librarymanagementsystem/View/Book/Book.fxml"), "Library Books Window", null);
    }
    @FXML
    void exitLibraryWindow(ActionEvent event) {
        Alert exitAlert=new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit Library Window");
        exitAlert.setHeaderText(null);
        exitAlert.setContentText("Do you Really Want to Exit This Window");
        if(exitAlert.showAndWait().get()==ButtonType.OK){
            ((Stage)libraryRootAnchorPane.getScene().getWindow()).close();
        }else{
            NotificationAlert alert=new NotificationAlert();
            alert.showInformationAlert("Cancel","You have cancelled Exit Operation");
        }
    }

    public  void disableButtons(Boolean result){
        if(result){
            usersButton.disableProperty().setValue(true);
        }else {
            usersButton.disableProperty().setValue(false);
        }
    }
}
