package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CreateExam extends AppCompatActivity {
    ArrayList<SectionModel> sectionModels = new ArrayList<>();
    DatabaseHelperSQLite dbhelper;
    public int section_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Exam");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_create_exam);

        TextInputLayout txtTeacher = findViewById(R.id.txtTeacher);
        TextInputLayout txtSubject = findViewById(R.id.txtSubject);
        TextInputLayout txtExamTitle = findViewById(R.id.txtExamTitle);

        //for dropdown content
        String[] resources  = getResources().getStringArray(R.array.number_of_questions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, resources);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.questionTextView);
        autoCompleteTextView.setAdapter(adapter);

        dbhelper = new DatabaseHelperSQLite(CreateExam.this);
        sectionModels = dbhelper.getAllSections("");
        ArrayAdapter<SectionModel> adapter2 = new ArrayAdapter<SectionModel>(
                this, R.layout.dropdown_item, sectionModels);
        AutoCompleteTextView autoCompleteTextViewSection = findViewById(R.id.sectionTextView);
        autoCompleteTextViewSection.setAdapter(adapter2);

        autoCompleteTextViewSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                SectionModel selected = (SectionModel) arg0.getAdapter().getItem(arg2);
                section_id = selected.getSection_id();
            }
        });

        Button btnExamBack = findViewById(R.id.btn_exam_back);
        btnExamBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnContinue = findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences settings = getSharedPreferences("shared preferences", MODE_PRIVATE);
                settings.edit().remove("questions").commit();
                settings.edit().putString("NumQuestions", String.valueOf(autoCompleteTextView.getText()))
                        .putString("TeacherName", String.valueOf(txtTeacher.getEditText().getText()))
                        .putString("ExamTitle", String.valueOf(txtExamTitle.getEditText().getText()))
                        .putString("SubjectName", String.valueOf(txtSubject.getEditText().getText()))
                        .putString("SectionID", String.valueOf(section_id))
                        .apply();
                Intent i = new Intent(CreateExam.this, CreateExamQuestions.class);
                /*i.putExtra("Teacher Name", txtTeacher.getEditText().getText());
                i.putExtra("Subject", txtSubject.getEditText().getText());
                i.putExtra("Exam Title", txtExamTitle.getEditText().getText());
                i.putExtra("Section ID", section_id);
                i.putExtra("Section Name", section_name);
                i.putExtra("Question Count", Integer.valueOf(String.valueOf(autoCompleteTextView.getText())));*/
                startActivity(i);


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String valueCreated = settings.getString("Exam Created",null);
        Log.wtf("CREATED: ", valueCreated);
        if (valueCreated == null) {
            // the key does not exist
        } else {
            settings.edit().remove("Exam Created").commit();
            Intent i = new Intent(CreateExam.this, CreateExam.class);
            finish();
            overridePendingTransition(0, 0);
            startActivity(i);
            overridePendingTransition(0, 0);
        }
    }
}