package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddSection extends AppCompatActivity {
    DatabaseHelperSQLite dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Section > Add Section");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_add_section);

        Button btnBack = findViewById(R.id.btn_back_section);
        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextInputLayout txtAddSection = (TextInputLayout) findViewById(R.id.txt_add_section);
        Button btnSave = findViewById(R.id.btn_save_section);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SectionModel sectionModel = new SectionModel(-1, txtAddSection.getEditText().getText().toString());
                dbhelper =  new DatabaseHelperSQLite(AddSection.this);
                boolean success = dbhelper.addSection(sectionModel);
                if (success){
                    Toast.makeText(AddSection.this, "Successfully added item", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}