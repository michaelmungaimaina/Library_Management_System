package com.mich.gwan.librarymanagementsystem.Model;

import java.sql.Timestamp;

public class LibraryMember extends Book{
    public LibraryMember(String bookId, int memberId) {
        super(bookId);
        this.memberId = memberId;
    }
private String regStatus;

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    private String firstName;
    private String lastName;
    private String gender;
    private int memberId;
    private String email;
    private String phone;
    private String department;
    private Timestamp registrationDate;
    private float registrationAmount;

    public float getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(float registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public LibraryMember(String bookId, String bookTitle, String firstName, String lastName) {
        super(bookId, bookTitle);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public LibraryMember(String firstName, String lastName, String gender, int memberId, float registrationAmount, Timestamp registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.memberId = memberId;
        this.registrationAmount=registrationAmount;
        this.registrationDate = registrationDate;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LibraryMember(String bookId, String bookTitle, String bookCategory, String firstName, String lastName, int memberId, String email, String phone) {
        super(bookId, bookTitle, bookCategory);
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberId = memberId;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LibraryMember() {
    }

    public LibraryMember(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LibraryMember(int memberId, String firstName, String lastName, String gender, String email, String phone, String department,String regStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.memberId=memberId;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.regStatus=regStatus;
    }
}
