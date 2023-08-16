package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Item Bank > Add Item");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }
        setContentView(R.layout.activity_add_item);

        String[] resources  = getResources().getStringArray(R.array.options);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, resources);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.answerTextView);
        autoCompleteTextView.setAdapter(adapter);

        TextInputLayout txtQuestion = findViewById(R.id.txt_add_question);
        TextInputLayout txtOptionA = findViewById(R.id.txt_add_option1);
        TextInputLayout txtOptionB = findViewById(R.id.txt_add_option2);
        TextInputLayout txtOptionC = findViewById(R.id.txt_add_option3);
        TextInputLayout txtOptionD = findViewById(R.id.txt_add_option4);
        AutoCompleteTextView txtAnswer = (AutoCompleteTextView) findViewById(R.id.answerTextView);

        Button btn_save = findViewById(R.id.btn_save_item);
        btn_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                QuestionModel questionModel;
                //Toast.makeText(AddItem.this, txtQuestion.getEditText().getText().toString(), Toast.LENGTH_LONG).show();
                questionModel = new QuestionModel(-1, txtQuestion.getEditText().getText().toString(),
                        txtOptionA.getEditText().getText().toString(), txtOptionB.getEditText().getText().toString(),
                        txtOptionC.getEditText().getText().toString(),txtOptionD.getEditText().getText().toString(), txtAnswer.getText().toString());
                DatabaseHelperSQLite dbhelper =  new DatabaseHelperSQLite(AddItem.this);
                boolean success = dbhelper.addItem(questionModel);
                if (success){
                    Toast.makeText(AddItem.this, "Successfully added item", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(AddItem.this, ItemBank.class);
                    //startActivity(i);
                    finish();
                }
            }
        });
        Button btn_back = findViewById(R.id.btn_back_item);
        btn_back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}