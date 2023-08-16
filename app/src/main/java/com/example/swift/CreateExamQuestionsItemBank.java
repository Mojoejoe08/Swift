package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CreateExamQuestionsItemBank extends AppCompatActivity {
    ArrayList<QuestionModel> questionModels = new ArrayList<>();
    DatabaseHelperSQLite dbhelper;
    AddQuestionsRecyclerViewAdapter adapter;
    TextInputEditText searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add from Item Bank");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_create_exam_questions_item_bank);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        //setUpQuestionModels();
        dbhelper = new DatabaseHelperSQLite(CreateExamQuestionsItemBank.this);
        questionModels = dbhelper.getAllQuestions("");
        adapter = new AddQuestionsRecyclerViewAdapter(this, questionModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchItem = (TextInputEditText) findViewById(R.id.search_item);
        searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                updateRecyclerView(editable.toString());
            }
        });

    }

    private void updateRecyclerView(String searchQuestion) {
        // clear old list
        questionModels.clear();

        questionModels.addAll(dbhelper.getAllQuestions(searchQuestion));

        // notify adapter
        adapter.notifyDataSetChanged();
    }
}