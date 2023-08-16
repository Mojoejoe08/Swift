package com.example.swift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SectionsRecyclerViewAdapter extends RecyclerView.Adapter<SectionsRecyclerViewAdapter.MyViewHolder> {
private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<SectionModel> sectionModels;

    public SectionsRecyclerViewAdapter(Context context, ArrayList<SectionModel> sectionModels, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.sectionModels = sectionModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public SectionsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.section_view_row, parent, false);
        return new SectionsRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionsRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.txt_section_row.setText(sectionModels.get(position).getSection_name());
    }

    @Override
    public int getItemCount() {
        return sectionModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_section_row;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            txt_section_row = itemView.findViewById(R.id.txt_section_row);

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
