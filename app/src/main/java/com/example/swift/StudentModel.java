package com.example.swift;

public class StudentModel {

    private int student_id, section_id;
    private String student_lastname, student_firstname;

    public StudentModel(int student_id, int section_id, String student_lastname, String student_firstname) {
        this.student_id = student_id;
        this.section_id = section_id;
        this.student_lastname = student_lastname;
        this.student_firstname = student_firstname;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getStudent_lastname() {
        return student_lastname;
    }

    public void setStudent_lastname(String student_lastname) {
        this.student_lastname = student_lastname;
    }

    public String getStudent_firstname() {
        return student_firstname;
    }

    public void setStudent_firstname(String student_firstname) {
        this.student_firstname = student_firstname;
    }
}
