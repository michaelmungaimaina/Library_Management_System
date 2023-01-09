package com.mich.gwan.librarymanagementsystem.Model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IssuedBook extends LibraryMember {
   private int issueBookid;
    private Timestamp dueDate;
    private Timestamp issueDate;
    private Timestamp returnedDate;
    private Timestamp fineDate;
    private Float fineAmount;

    public Float getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Float fineAmount) {
        this.fineAmount = fineAmount;
    }

    public IssuedBook(String bookId, String bookTitle, String firstName, String lastName, Timestamp fineDate) {
        super(bookId, bookTitle, firstName, lastName);
        this.fineDate = fineDate;
    }

    public Timestamp getFineDate() {
        return fineDate;
    }

    public void setFineDate(Timestamp fineDate) {
        this.fineDate = fineDate;
    }

    private  int numberOfDaysWithFine;

    public void setNumberOfDaysWithFine(int numberOfDaysWithFine) {
        this.numberOfDaysWithFine = numberOfDaysWithFine;
    }

    public IssuedBook() {
    }

    public IssuedBook(String bookId, String bookTitle, String bookCategory, String firstName, String lastName, int memberId, String email, String phone, int issueBookid, Timestamp issueDate, Timestamp dueDate, Timestamp returnedDate) {
        super(bookId, bookTitle, bookCategory, firstName, lastName, memberId, email, phone);
        this.issueBookid = issueBookid;
        this.dueDate = dueDate;
        this.issueDate = issueDate;
        this.returnedDate = returnedDate;
    }

    public IssuedBook(String bookId, int memberId) {
        super(bookId, memberId);
    }

    public int getIssueBookid() {
        return issueBookid;
    }

    public void setIssueBookid(int issueBookid) {
        this.issueBookid = issueBookid;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public Timestamp getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Timestamp returnedDate) {
        this.returnedDate = returnedDate;
    }
    public int getNumberOfDaysWithFine(Timestamp issueDate, Timestamp returnedDate){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        try{
            Date startDate=sdf.parse(String.valueOf(issueDate));
            Date endDate=sdf.parse(String.valueOf(returnedDate));
            //calculate time difference in minutes,seconds,hours and years;
            long differentInTime=endDate.getTime()-startDate.getTime();
            long diffenceInSeconds=(differentInTime/1000)%60;
            long  differenceInMinutes=(differentInTime/(1000*60))%60;
            long differenceInHours=(differentInTime/(100*60*60))%24;
            long differenceInYears=(differentInTime/(10001*60*60*24*365));
            long differenceInDays=(differentInTime/(10001*60*60*24*365))%365;
            this.numberOfDaysWithFine=(int)differenceInDays;
            }catch (ParseException e){
            e.getCause();
            e.printStackTrace();
        }
        return numberOfDaysWithFine;
    }
}
