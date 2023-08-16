package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {
    ArrayList<StudentModel> studentModels = new ArrayList<>();
    DatabaseHelperSQLite dbhelper;
    StudentsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Section > Student List");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_student_list);

        int section_id = getIntent().getIntExtra("Section_ID", 0);
        String section_name = getIntent().getStringExtra("Section_Name");
        RecyclerView recyclerView = findViewById(R.id.mRecyclerViewStudents);
        dbhelper = new DatabaseHelperSQLite(StudentList.this);
        studentModels = dbhelper.getAllStudents(section_id);
        adapter = new StudentsRecyclerViewAdapter(this, studentModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        TextView txtSectionName = findViewById(R.id.txt_section_name);
        txtSectionName.setText(String.valueOf(section_name));


        Button btnAddStudent = findViewById(R.id.btn_add_student);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentList.this, AddStudent.class);
                i.putExtra("Section_ID", section_id);
                i.putExtra("Section_Name", section_name);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        int section_id = getIntent().getIntExtra("Section_ID", 0);
        updateRecyclerView(section_id);
    }

    private void updateRecyclerView(int searchSection) {
        studentModels.clear();
        studentModels.addAll(dbhelper.getAllStudents(searchSection));
        adapter.notifyDataSetChanged();
    }
}