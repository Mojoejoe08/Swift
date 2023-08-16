package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ExamList extends AppCompatActivity implements ExamRecyclerViewInterface{
    ArrayList<ExamModel> examModels = new ArrayList<>();
    DatabaseHelperSQLite dbhelper;
    ExamRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Exam List");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_exam_list);

        TextView txt_section_name = findViewById(R.id.txt_section_name);

        int section_id = getIntent().getIntExtra("Section_ID", 0);
        String section_name = getIntent().getStringExtra("Section_Name");
        txt_section_name.setText(section_name);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerViewExam);
        dbhelper = new DatabaseHelperSQLite(ExamList.this);
        examModels = dbhelper.getAllExam(section_id);
        adapter = new ExamRecyclerViewAdapter(this, examModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemClick(int position) {

        String section_name = getIntent().getStringExtra("Section_Name");
        Intent intent = new Intent(ExamList.this, ExamStudentList.class);
        intent.putExtra("Section_ID", examModels.get(position).getSection_id());
        intent.putExtra("Exam_ID", examModels.get(position).getExam_id());
        intent.putExtra("Section_Name", section_name);
        startActivity(intent);
    }
}