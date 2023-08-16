package com.example.swift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddQuestionsRecyclerViewAdapter extends RecyclerView.Adapter<AddQuestionsRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<QuestionModel> questionModels;

    public AddQuestionsRecyclerViewAdapter(Context context, ArrayList<QuestionModel> questionModels){
        this.context = context;
        this.questionModels = questionModels;
    }

    @NonNull
    @Override
    public AddQuestionsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_exam_item_row, parent, false);
        return new AddQuestionsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddQuestionsRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.txt_question.setText(questionModels.get(position).getQuestion());
        holder.txt_question.setTypeface(null, Typeface.BOLD);
        holder.txt_option1.setText("a.) " + questionModels.get(position).getOption_1());
        holder.txt_option2.setText("b.) " + questionModels.get(position).getOption_2());
        holder.txt_option3.setText("c.) " + questionModels.get(position).getOption_3());
        holder.txt_option4.setText("d.) " + questionModels.get(position).getOption_4());
        if(questionModels.get(position).getAnswer().equals("Option A")){
            holder.txt_option1.setTypeface(null, Typeface.BOLD);
        } else if (questionModels.get(position).getAnswer().equals("Option B")) {
            holder.txt_option2.setTypeface(null, Typeface.BOLD);
        } else if (questionModels.get(position).getAnswer().equals("Option C")) {
            holder.txt_option3.setTypeface(null, Typeface.BOLD);
        } else if (questionModels.get(position).getAnswer().equals("Option D")) {
            holder.txt_option4.setTypeface(null, Typeface.BOLD);
        }
        holder.btnAddToExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateExamQuestions.class);
                intent.putExtra("Item", questionModels.get(position));
                view.getContext().startActivity(intent);
                ((Activity)holder.itemView.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_question, txt_option1, txt_option2, txt_option3, txt_option4;
        Button btnAddToExam;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_question = itemView.findViewById(R.id.txt_question);
            txt_option1 = itemView.findViewById(R.id.txt_option1);
            txt_option2 = itemView.findViewById(R.id.txt_option2);
            txt_option3 = itemView.findViewById(R.id.txt_option3);
            txt_option4 = itemView.findViewById(R.id.txt_option4);
            btnAddToExam = itemView.findViewById(R.id.btn_add_to_exam);

        }
    }
}
