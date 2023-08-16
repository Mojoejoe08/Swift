package com.example.swift;

public class MeritOrderModel {


    String studentName;
    int studentID;
    int score;

    public MeritOrderModel(String studentName, int studentID, int score) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.score = score;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
