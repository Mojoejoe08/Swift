package com.example.swift;

public class ExamModel {
    private int exam_id;
    private String title;
    private String teacher;
    private String subject;
    private int question_count;
    private int section_id;

    public ExamModel(int exam_id, String title, String teacher, String subject, int question_count, int section_id) {
        this.exam_id = exam_id;
        this.title = title;
        this.teacher = teacher;
        this.subject = subject;
        this.question_count = question_count;
        this.section_id = section_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }
}
