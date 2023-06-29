package com.example.foodorderingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.classes.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHoder> {
    private List<Food> mListFood;
    private List<Food> mFoods;
    private Context mContext;

    public FoodAdapter(Context mContext, List<Food> mListFood) {
        this.mContext = mContext;
        this.mListFood = mListFood;
        this.mFoods = mListFood;
    }

    public void setFilterList(String text) {
        if (this.mListFood.isEmpty()) {
            this.mListFood = mFoods;
        }
        List<Food> filterList = new ArrayList<>();
        for (Food item : mFoods) {
            if (item.getName().toLowerCase().contains(text.toLowerCase().trim())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            this.mListFood.clear();
        } else {
            this.mListFood = filterList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);

        return new FoodViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHoder holder, int position) {
        Food food = mListFood.get(position);
        if (food == null) {
            return;
        }

//        bug: change img
//        fixed
        Glide.with(holder.imgAvatar.getContext()).load(food.getImgsrc()).into(holder.imgAvatar);
        holder.tvName.setText(food.getName());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickgotoDetail(food);
            }
        });
//
    }

    private void onClickgotoDetail(Food food) {
        Intent intent = new Intent(mContext, listViewFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_food", food);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public void release() {
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if (mListFood != null) {
            return mListFood.size();
        }
        return 0;
    }

    public class FoodViewHoder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvAddress;

        private CardView layoutItem;

        public FoodViewHoder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.card_item);
            imgAvatar = itemView.findViewById(R.id.img_item);
            tvName = itemView.findViewById(R.id.name_item);
//            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}