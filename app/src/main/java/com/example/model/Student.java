package com.example.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Student implements Serializable {
    private String studentID;
    private String studentName;
    private String studentAddress;
    private boolean studentGender;

    public Student() {
    }

    public Student(String studentID, String studentName, String studentAddress, boolean studentGender) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentAddress = studentAddress;
        this.studentGender = studentGender;
    }

    public Student(String studentName, String studentAddress, boolean studentGender) {
        this.studentName = studentName;
        this.studentAddress = studentAddress;
        this.studentGender = studentGender;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public boolean getStudentGender() {
        return studentGender;
    }

    public boolean isMale() {
        if (getStudentGender() == true) {
            return true;
        } else {
            return false;
        }
    }

    public void setStudentGender(boolean studentGender) {
        this.studentGender = studentGender;
    }

    @NonNull
    @Override
    public String toString() {
        return this.studentID + " - " + this.studentName;
    }
}
