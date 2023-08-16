package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SectionList extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<SectionModel> sectionModels = new ArrayList<>();
    DatabaseHelperSQLite dbhelper;
    SectionsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sections");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_section_list);
        RecyclerView recyclerView = findViewById(R.id.mRecyclerViewSection);
        dbhelper = new DatabaseHelperSQLite(SectionList.this);
        sectionModels = dbhelper.getAllSections("");
        adapter = new SectionsRecyclerViewAdapter(this, sectionModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnAddSection = findViewById(R.id.btn_add_section);
        btnAddSection.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(SectionList.this, AddSection.class);
                startActivity(i);
            }
        });

        TextInputEditText searchItem = (TextInputEditText) findViewById(R.id.search_section);
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
        TextInputEditText searchSection = (TextInputEditText) findViewById(R.id.search_section);
        searchSection.setText("");
        updateRecyclerView("");
    }

    private void updateRecyclerView(String searchSection) {
        sectionModels.clear();
        sectionModels.addAll(dbhelper.getAllSections(searchSection));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(SectionList.this, StudentList.class);
        intent.putExtra("Section_ID", sectionModels.get(position).getSection_id());
        intent.putExtra("Section_Name", sectionModels.get(position).getSection_name());
        startActivity(intent);
    }


}