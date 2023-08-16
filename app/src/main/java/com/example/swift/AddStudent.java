package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Section > Add Student");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_add_student);
        int section_id = getIntent().getIntExtra("Section_ID", 0);
        String section_name = getIntent().getStringExtra("Section_Name");
        TextInputLayout txtAddStudentLastname = (TextInputLayout) findViewById(R.id.txt_add_last_name);
        TextInputLayout txtAddStudentFirstname = (TextInputLayout) findViewById(R.id.txt_add_first_name);
        Button btnAddStudent = findViewById(R.id.btn_save_student);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentModel studentModel = new StudentModel(-1, section_id , txtAddStudentLastname.getEditText().getText().toString(), txtAddStudentFirstname.getEditText().getText().toString());
                DatabaseHelperSQLite dbhelper =  new DatabaseHelperSQLite(AddStudent.this);
                boolean success = dbhelper.addStudent(studentModel);
                if (success){
                    Toast.makeText(AddStudent.this, "Successfully added item", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(AddItem.this, ItemBank.class);
                    //startActivity(i);
                    finish();
                }
            }
        });
        Button btnBackStudent = findViewById(R.id.btn_back_student);
        btnBackStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}