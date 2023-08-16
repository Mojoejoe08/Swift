package com.example.swift;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionsRecyclerViewAdapter extends RecyclerView.Adapter<QuestionsRecyclerViewAdapter.MyViewHolder> {
    private final ItemRecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<QuestionModel> questionModels;

    public QuestionsRecyclerViewAdapter(Context context, ArrayList<QuestionModel> questionModels, ItemRecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.questionModels = questionModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public QuestionsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new QuestionsRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsRecyclerViewAdapter.MyViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return questionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_question, txt_option1, txt_option2, txt_option3, txt_option4;


        public MyViewHolder(@NonNull View itemView, ItemRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            txt_question = itemView.findViewById(R.id.txt_question);
            txt_option1 = itemView.findViewById(R.id.txt_option1);
            txt_option2 = itemView.findViewById(R.id.txt_option2);
            txt_option3 = itemView.findViewById(R.id.txt_option3);
            txt_option4 = itemView.findViewById(R.id.txt_option4);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemLongClick(position);
                        }
                    }
                    return false;
                }
            });
        }
    }
}
