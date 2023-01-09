package com.mich.gwan.librarymanagementsystem.Model;

import java.sql.Timestamp;

public class Book {
    private   String bookId;
    private String bookTitle;
    private String authorFirstName;
    private  String authorLastName;
    private  String bookPublisher;
    private Integer yearOfPublication;
    private Integer bookNumberOfPages;
    private String bookCategory;
    private String bookStatus;
    private Timestamp issueDate;

    public Book() {
    }

    public Book(String bookId, String bookTitle) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
    }

    public Book(String bookId) {
        this.bookId = bookId;
    }

    public Book(String bookId, String bookTitle, String bookCategory) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookCategory = bookCategory;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public Integer getBookNumberOfPages() {
        return bookNumberOfPages;
    }

    public void setBookNumberOfPages(Integer bookNumberOfPages) {
        this.bookNumberOfPages = bookNumberOfPages;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Book(String bookId, String bookTitle, String authorFirstName, String authorLastName, String bookPublisher, Integer yearOfPublication, Integer bookNumberOfPages, String bookCategory, String bookStatus) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.bookPublisher = bookPublisher;
        this.yearOfPublication=yearOfPublication;
        this.bookNumberOfPages = bookNumberOfPages;
        this.bookCategory = bookCategory;
        this.bookStatus = bookStatus;
    }

}
