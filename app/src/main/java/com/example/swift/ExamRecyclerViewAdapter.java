package com.example.swift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExamRecyclerViewAdapter extends RecyclerView.Adapter<ExamRecyclerViewAdapter.MyViewHolder>{
    private final ExamRecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ExamModel> examModels;

    public ExamRecyclerViewAdapter(Context context, ArrayList<ExamModel> examModels, ExamRecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.examModels = examModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ExamRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.exam_view_row, parent, false);
        return new ExamRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.txt_teacher.setText(examModels.get(position).getTeacher());
        holder.txt_exam.setText(examModels.get(position).getTitle());
        holder.txt_subject.setText(examModels.get(position).getSubject());
        holder.txt_num_items.setText(String.valueOf(examModels.get(position).getQuestion_count()));
    }

    @Override
    public int getItemCount() {
        return examModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_teacher, txt_subject, txt_num_items, txt_exam;

        public MyViewHolder(@NonNull View itemView, ExamRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            txt_exam = itemView.findViewById(R.id.txt_exam);
            txt_teacher = itemView.findViewById(R.id.txt_teacher);
            txt_subject = itemView.findViewById(R.id.txt_subject);
            txt_num_items = itemView.findViewById(R.id.txt_num_items);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
