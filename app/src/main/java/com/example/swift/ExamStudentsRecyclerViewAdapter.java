package com.example.swift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExamStudentsRecyclerViewAdapter extends RecyclerView.Adapter<ExamStudentsRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentModel> studentModels;
    int exam_id;
    int section_id;
    String section_name;
    DatabaseHelperSQLite dbhelper;

    public ExamStudentsRecyclerViewAdapter(Context context, ArrayList<StudentModel> studentModels, int exam_id, String section_name, int section_id) {
        this.context = context;
        this.studentModels = studentModels;
        this.exam_id = exam_id;
        this.section_name = section_name;
        this.section_id = section_id;
    }

    @NonNull
    @Override
    public ExamStudentsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exam_student_view_row, parent, false);
        return new ExamStudentsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamStudentsRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.txt_student_row.setText(studentModels.get(position).getStudent_lastname() + ", " + studentModels.get(position).getStudent_firstname());
        holder.cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("Section_ID", section_id);
                intent.putExtra("Section_Name", section_name);
                intent.putExtra("Student_ID", studentModels.get(position).getStudent_id());
                intent.putExtra("Student_Name", studentModels.get(position).getStudent_lastname() + ", " + studentModels.get(position).getStudent_firstname());
                intent.putExtra("Exam_ID", exam_id);
                view.getContext().startActivity(intent);
                ((Activity) holder.itemView.getContext()).finish();
            }
        });
        holder.txt_student_result.setText("Unchecked");
        dbhelper = new DatabaseHelperSQLite(holder.cameraImageButton.getContext());
        if(dbhelper.hasResult(studentModels.get(position).getStudent_id(), exam_id)){
            holder.cameraImageButton.setVisibility(View.INVISIBLE);
            int score = dbhelper.getScore(studentModels.get(position).getStudent_id(), exam_id);
            holder.txt_student_result.setText("Score: " + score);
        }else{
            holder.cameraImageButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return studentModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_student_row, txt_student_result;
        ImageView cameraImageButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_student_row = itemView.findViewById(R.id.txt_student_row);
            txt_student_result = itemView.findViewById(R.id.txt_student_result);
            cameraImageButton = itemView.findViewById(R.id.cameraView);

        }
    }
}