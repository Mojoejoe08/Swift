package com.example.swift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentsRecyclerViewAdapter  extends RecyclerView.Adapter<StudentsRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentModel> studentModels;

    public StudentsRecyclerViewAdapter(Context context, ArrayList<StudentModel> studentModels) {
        this.context = context;
        this.studentModels = studentModels;
    }

    @NonNull
    @Override
    public StudentsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_view_row, parent, false);
        return new StudentsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.txt_name.setText(studentModels.get(position).getStudent_lastname() + ", " + studentModels.get(position).getStudent_firstname());
    }

    @Override
    public int getItemCount() {
        return studentModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_student_row);


        }
    }
}