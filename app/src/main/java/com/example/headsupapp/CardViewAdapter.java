package com.example.headsupapp;


import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.headsupapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by abhay on 4/5/17.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<Category> categoriesList;

    private Map<String, List<String>> stringListMap;
    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryName;
        public ImageView thumbnail;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.cardview_text);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    public CardViewAdapter(Context mContext, List<Category> categoriesList, Map<String, List<String>> stringListMap) {
        this.mContext = mContext;
        this.categoriesList = categoriesList;
        this.stringListMap = stringListMap;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View categoryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        CategoryViewHolder cvh = new CategoryViewHolder(categoryView);
        return cvh;
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {
        Category categories = categoriesList.get(position);
        holder.categoryName.setText(categories.getName());
        holder.thumbnail.setImageResource(categories.getCoverImage());
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (View) v.getParent();
                TextView textView = (TextView) view.findViewById(R.id.cardview_text);
                Intent intent = new Intent(mContext, CategoryActivity.class);
                intent.putExtra(textView.getText().toString(), new ArrayList<>(stringListMap.get(textView.getText().toString())));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public void deleteItem(int position){
        categoriesList.remove(position);
        this.notifyItemRemoved(position);
    }

}