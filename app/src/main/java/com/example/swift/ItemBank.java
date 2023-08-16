package com.example.swift;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ItemBank extends AppCompatActivity implements ItemRecyclerViewInterface {
    ArrayList<QuestionModel> questionModels = new ArrayList<>();
    TextInputEditText searchItem;
    DatabaseHelperSQLite dbhelper;
    QuestionsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Item Bank");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }
        setContentView(R.layout.activity_item_bank);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        //setUpQuestionModels();
        dbhelper = new DatabaseHelperSQLite(ItemBank.this);
        questionModels = dbhelper.getAllQuestions("");
        adapter = new QuestionsRecyclerViewAdapter(this, questionModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnItemBank = findViewById(R.id.btn_add_item);
        btnItemBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemBank.this, AddItem.class);
                startActivity(i);
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        TextInputEditText searchItem = (TextInputEditText) findViewById(R.id.search_item);
        searchItem.setText("");
        updateRecyclerView("");
    }

    private void updateRecyclerView(String searchQuestion) {
        // clear old list
        questionModels.clear();

        questionModels.addAll(dbhelper.getAllQuestions(searchQuestion));

        // notify adapter
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification").setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchItem = (TextInputEditText) findViewById(R.id.search_item);
                        if(dbhelper.deleteItem(questionModels.get(position))){

                            Toast.makeText(ItemBank.this,
                                    "Error: Cannot delete from database",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ItemBank.this,
                                    "Deleted item successfully",
                                    Toast.LENGTH_SHORT).show();
                            questionModels.clear();
                            questionModels.addAll(dbhelper.getAllQuestions(searchItem.getText().toString()));
                            adapter.notifyDataSetChanged();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .create().show();
    }
}