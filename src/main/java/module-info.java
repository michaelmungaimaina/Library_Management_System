module com.mich.gwan.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    //requires poi.ooxml;
    requires java.desktop;
    requires gson;
    requires org.apache.poi.ooxml;


    opens com.mich.gwan.librarymanagementsystem to javafx.fxml;
    exports com.mich.gwan.librarymanagementsystem;
    opens com.mich.gwan.librarymanagementsystem.Controller.Login to javafx.fxml;
    exports com.mich.gwan.librarymanagementsystem.Controller.Login;
    opens com.mich.gwan.librarymanagementsystem.Controller.Book to javafx.fxml;
    exports com.mich.gwan.librarymanagementsystem.Controller.Book;
    opens com.mich.gwan.librarymanagementsystem.Controller.Member to javafx.fxml;
    exports com.mich.gwan.librarymanagementsystem.Controller.Member;
    opens com.mich.gwan.librarymanagementsystem.Controller.User to javafx.fxml;
    exports com.mich.gwan.librarymanagementsystem.Controller.User;
    opens com.mich.gwan.librarymanagementsystem.Settings to javafx.fxml;
    exports com.mich.gwan.librarymanagementsystem.Settings;
    opens com.mich.gwan.librarymanagementsystem.Model to javafx.fxml;
    exports com.mich.gwan.librarymanagementsystem.Model;
}