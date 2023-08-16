package com.example.swift;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class QuestionModel implements Parcelable {

    private int question_id;
    private String question;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;
    private String answer;

    public QuestionModel(int question_id, String question, String option_1, String option_2, String option_3, String option_4, String answer) {
        this.question_id = question_id;
        this.question = question;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
        this.answer = answer;
    }

    public QuestionModel() {

    }

    protected QuestionModel(Parcel in) {
        question_id = in.readInt();
        question = in.readString();
        option_1 = in.readString();
        option_2 = in.readString();
        option_3 = in.readString();
        option_4 = in.readString();
        answer = in.readString();
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public String getOption_4() {
        return option_4;
    }

    public void setOption_4(String option_4) {
        this.option_4 = option_4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(question_id);
        parcel.writeString(question);
        parcel.writeString(option_1);
        parcel.writeString(option_2);
        parcel.writeString(option_3);
        parcel.writeString(option_4);
        parcel.writeString(answer);
    }
}
