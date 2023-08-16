package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowResult extends AppCompatActivity {
    DatabaseHelperSQLite dbhelper;
    int questionCount;
    int section_id, student_id, exam_id;
    String section_name, student_name;
    ArrayList<QuestionModel> questionModels = new ArrayList<>();
    ArrayList<ExamStudentQuestionModel> examStudentQuestionModels = new ArrayList<>();
    TextView txtScore, txtStudent;
    Button btnSaveResult;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Result");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_show_result);

        TableLayout myTableLayout = findViewById(R.id.tableResult);

        TableRow tableRow = new TableRow(this);
        int color = Color.parseColor("#006A38");
        ColorDrawable colorDrawable = new ColorDrawable(color);
        tableRow.setBackground(colorDrawable);
// Create a new TextView for the first column
        TextView numTextView = new TextView(this);
        numTextView.setPadding(5, 10, 0, 10);
        numTextView.setTextSize(20);
        numTextView.setText("No.");
        numTextView.setTextColor(Color.WHITE);
        tableRow.addView(numTextView);

// Create a new TextView for the second column
        TextView keyTextView = new TextView(this);
        keyTextView.setPadding(5, 10, 0, 10);
        keyTextView.setTextSize(20);
        keyTextView.setText("Key");
        keyTextView.setTextColor(Color.WHITE);
        tableRow.addView(keyTextView);

// Create a new TextView for the third column
        TextView answerTextView = new TextView(this);
        answerTextView.setPadding(5, 10, 0, 10);
        answerTextView.setTextSize(20);
        answerTextView.setText("Answer");
        answerTextView.setTextColor(Color.WHITE);
        tableRow.addView(answerTextView);

        TextView remarksTextView = new TextView(this);
        remarksTextView.setPadding(5, 10, 0, 10);
        remarksTextView.setTextSize(20);
        remarksTextView.setText("Remarks");
        remarksTextView.setTextColor(Color.WHITE);
        tableRow.addView(remarksTextView);

// Add the table row to the table layout
        myTableLayout.addView(tableRow);

        dbhelper = new DatabaseHelperSQLite(ShowResult.this);

        section_id = getIntent().getIntExtra("Section_ID", 0);
        section_name = getIntent().getStringExtra("Section_Name");
        student_id = getIntent().getIntExtra("Student_ID", 0);
        student_name = getIntent().getStringExtra("Student_Name");
        exam_id = getIntent().getIntExtra("Exam_ID", 0);
        Bundle extras = getIntent().getExtras();
        String [] answers = extras.getStringArray("answers");
        questionCount = dbhelper.getExamCount(exam_id);
        questionModels = dbhelper.getAllExamQuestion(exam_id);
        for(int ctr = 0; ctr < questionCount; ctr++){

            String letterAnswer = questionModels.get(ctr).getAnswer().substring(questionModels.get(ctr).getAnswer().length() - 1);
            String colorString = "#f2f2f2";
            if(ctr % 2 == 0){
                colorString = "#ffffff";
            }
            tableRow = new TableRow(this);
            color = Color.parseColor(colorString);
            colorDrawable = new ColorDrawable(color);
            tableRow.setBackground(colorDrawable);
// Create a new TextView for the first column
            numTextView = new TextView(this);
            numTextView.setPadding(5, 10, 0, 10);
            numTextView.setTextSize(20);
            numTextView.setText(String.valueOf(ctr + 1));
            tableRow.addView(numTextView);

// Create a new TextView for the second column
            keyTextView = new TextView(this);
            keyTextView.setPadding(5, 10, 0, 10);
            keyTextView.setTextSize(20);
            keyTextView.setText(letterAnswer);
            tableRow.addView(keyTextView);

// Create a new TextView for the third column
            answerTextView = new TextView(this);
            answerTextView.setPadding(5, 10, 0, 10);
            answerTextView.setTextSize(20);
            answerTextView.setText(answers[ctr]);
            tableRow.addView(answerTextView);

            String remarks = "WRONG";
            if(letterAnswer.equals(answers[ctr])){
                remarks = "CORRECT";
                score += 1;
                examStudentQuestionModels.add(new ExamStudentQuestionModel(1, 1, questionModels.get(ctr).getQuestion_id(), "YES"));
            }else{

            }

            remarksTextView = new TextView(this);
            remarksTextView.setPadding(5, 10, 0, 10);
            remarksTextView.setTextSize(20);
            remarksTextView.setText(remarks);
            tableRow.addView(remarksTextView);

// Add the table row to the table layout
            myTableLayout.addView(tableRow);
        }

        txtScore = findViewById(R.id.textScore);
        txtStudent = findViewById(R.id.textStudent);
        txtScore.setText("Score: " + score + " / " + questionCount);
        txtStudent.setText("Student: " + student_name);
        btnSaveResult = findViewById(R.id.btn_save_result);
        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = dbhelper.addExamResult(examStudentQuestionModels,new ExamStudentModel(1, exam_id, student_id, score));
                if(success){
                    Intent i = new Intent(ShowResult.this, ExamStudentList.class);
                    i.putExtra("Section_Name", section_name);
                    i.putExtra("Exam_ID", exam_id);
                    i.putExtra("Section_ID", section_id);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}