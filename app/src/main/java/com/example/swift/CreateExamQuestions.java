package com.example.swift;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CreateExamQuestions extends AppCompatActivity implements ItemRecyclerViewInterface {

    ArrayList<QuestionModel> questionModels;
    DatabaseHelperSQLite dbhelper;
    TextView txtNumOfQuestion;
    Button btnCreate, btnSave;
    QuestionsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Exam Items");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_create_exam_questions);

        loadData();

        RecyclerView recyclerView = findViewById(R.id.mRecyclerViewExamItems);
        adapter = new QuestionsRecyclerViewAdapter(this, questionModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnSave = findViewById(R.id.btn_save_exam);
        btnCreate = findViewById(R.id.btn_add_from_item_bank);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateExamQuestions.this, CreateExamQuestionsItemBank.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String numOfQuestions = sharedPreferences.getString("NumQuestions", null);

        Intent intent = getIntent();
        QuestionModel questionModel = null;
        if ((questionModel = getIntent().getParcelableExtra("Item")) != null) {
            boolean exists = false;
            for (QuestionModel questionToAdd : questionModels){
                if (questionToAdd.getQuestion_id() == questionModel.getQuestion_id()){
                    exists = true;
                }
            }
            if(!exists){
                questionModels.add(questionModel);
                saveData();
                Toast.makeText(CreateExamQuestions.this, "Item added to the exam", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(CreateExamQuestions.this, "Item selected is already in the exam", Toast.LENGTH_SHORT).show();
            }

        }

        if(questionModels.size() == Integer.valueOf(numOfQuestions)){
            btnCreate.setEnabled(false);
            btnSave.setEnabled(true);
        }

        txtNumOfQuestion = findViewById(R.id.textView2);
        txtNumOfQuestion.setText(questionModels.size() + " of " + numOfQuestions);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper = new DatabaseHelperSQLite(CreateExamQuestions.this);
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                int question_count = Integer.valueOf(sharedPreferences.getString("NumQuestions", null));
                String teacher_name = sharedPreferences.getString("TeacherName", null);
                String exam_title = sharedPreferences.getString("ExamTitle", null);
                String subject_name = sharedPreferences.getString("SubjectName", null);
                int section_id = Integer.valueOf(sharedPreferences.getString("SectionID", null));

                boolean success = dbhelper.addExam(questionModels, question_count, teacher_name, exam_title, subject_name, section_id);
                if (success){
                    sharedPreferences.edit().putString("Exam Created", "Created").apply();
                    String valueCreated = sharedPreferences.getString("Exam Created",null);
                    Log.wtf("CREATED: ", valueCreated);
                    Toast.makeText(CreateExamQuestions.this, "Exam successfully created", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification").setMessage("Remove this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                        String numOfQuestions = sharedPreferences.getString("NumQuestions", null);
                            questionModels.remove(position);
                            saveData();
                            txtNumOfQuestion = findViewById(R.id.textView2);
                            txtNumOfQuestion.setText(questionModels.size() + " of " + numOfQuestions);
                            adapter.notifyDataSetChanged();
                            btnCreate = findViewById(R.id.btn_add_from_item_bank);
                            btnCreate.setEnabled(true);
                            btnSave.setEnabled(false);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();


        adapter.notifyDataSetChanged();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("questions", null);
        Type type = new TypeToken<ArrayList<QuestionModel>>() {}.getType();
        questionModels = gson.fromJson(json, type);
        if (questionModels == null) {
            // if the array list is empty
            // creating a new array list.
            questionModels = new ArrayList<>();
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(questionModels);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("questions", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.
        //Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }
}