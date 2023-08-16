package com.example.swift;

public class ExamStudentModel {

    private int exam_student_id;
    private int exam_id;
    private int student_id;
    private int score;

    public ExamStudentModel(int exam_student_id, int exam_id, int student_id, int score) {
        this.exam_student_id = exam_student_id;
        this.exam_id = exam_id;
        this.student_id = student_id;
        this.score = score;
    }

    public int getExam_student_id() {
        return exam_student_id;
    }

    public void setExam_student_id(int exam_student_id) {
        this.exam_student_id = exam_student_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }





}
