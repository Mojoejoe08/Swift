package com.example.swift;

public class ExamStudentQuestionModel {
    private int exam_student_question_id;
    private int exam_student_id;
    private int question_id;
    private String correct;

    public ExamStudentQuestionModel(int exam_student_question_id, int exam_student_id, int question_id, String correct) {
        this.exam_student_question_id = exam_student_question_id;
        this.exam_student_id = exam_student_id;
        this.question_id = question_id;
        this.correct = correct;
    }

    public int getExam_student_question_id() {
        return exam_student_question_id;
    }

    public void setExam_student_question_id(int exam_student_question_id) {
        this.exam_student_question_id = exam_student_question_id;
    }

    public int getExam_student_id() {
        return exam_student_id;
    }

    public void setExam_student_id(int exam_student_id) {
        this.exam_student_id = exam_student_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }




}
