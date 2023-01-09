package com.mich.gwan.librarymanagementsystem.Model;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public final class DatabaseConnection {
    Statement stm;
    ResultSet rs;
    Connection conn;

    private static DatabaseConnection databaseConnection=null;
    //Database Connection Constructor
    //private so as no class can create direct database object
    private DatabaseConnection() {
        ConnectDatabase();
    }
    public static DatabaseConnection getInstance(){
        if(databaseConnection==null){
            databaseConnection=new DatabaseConnection();
        }
        return databaseConnection;
    }
    public static Connection ConnectDatabase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/librarydb", "root",
                    "13579QEtuo.");//$A3SM1+h70$#@37141538
            System.out.println("Database connected");
            return connection;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            e.printStackTrace();
            e.getCause();


        }
        return null;
    }
    //use to execute Select Queries
    public ResultSet execQuery(String query){
        ResultSet rs;
        try{
            conn=DatabaseConnection.ConnectDatabase();
            stm=conn.createStatement();
            rs=stm.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {

        }
        return rs;
    }
    //updating table,deleting record,inserting records
    public boolean execAction(String query){
        try {
            stm=conn.createStatement();
            stm.execute(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR"+e.getMessage(),"ERROR-OCCURED,",JOptionPane.ERROR_MESSAGE);
        }finally {

        }
        return false;
    }
}





