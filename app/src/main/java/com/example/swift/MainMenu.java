package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Menu");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }
        setContentView(R.layout.activity_main_menu);

        Button btnItemBank = findViewById(R.id.btn_item_bank);
        btnItemBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, ItemBank.class);
                startActivity(i);
            }
        });

        Button btnCreateExam = findViewById(R.id.btn_create_exam);
        btnCreateExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, CreateExam.class);
                startActivity(i);
            }
        });

        Button btnCheckExam = findViewById(R.id.btn_check_exam);
        btnCheckExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, CheckExamSection.class);
                //Intent i = new Intent(MainMenu.this, ShowResult.class);
                startActivity(i);
            }
        });



        Button btnSectionList = findViewById(R.id.btn_section);
        btnSectionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this, SectionList.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish(); // Close the activity
    }


}