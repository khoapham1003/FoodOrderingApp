package com.example.foodorderingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.classes.Food;
import com.google.firebase.firestore.auth.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHoder>{


    private List<Food> mListFood;
    private Context mContext;
    public FoodAdapter(Context mContext,List<Food> mListFood){
        this.mContext=mContext;
        this.mListFood=mListFood;
    }

    @NonNull
    @Override
    public FoodViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);

        return new FoodViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHoder holder, int position) {
        Food food= mListFood.get(position);
        if(food == null){
            return;
        }



//        bug: change img
//        holder.imgAvatar.setImageResource(Integer.parseInt(food.getImgsrc()));
          holder.tvName.setText(food.getName());
//        holder.tvAddress.setText(food.getAddress());

//
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickgotoDetail(food);
            }
        });
//
    }



    private void onClickgotoDetail(Food food){
        Intent intent = new Intent(mContext,listViewFoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_food",food);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    public void release(){
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if(mListFood != null){
            return mListFood.size();
        }
        return 0;
    }

    public class FoodViewHoder extends RecyclerView.ViewHolder{
        private ImageView imgAvatar;
        private TextView tvName;
        private TextView tvAddress;

        private CardView layoutItem;
        public FoodViewHoder(@NonNull View itemView) {
            super(itemView);
            layoutItem= itemView.findViewById(R.id.card_item);
            imgAvatar = itemView.findViewById(R.id.img_item);
            tvName = itemView.findViewById(R.id.name_item);
//            tvAddress = itemView.findViewById(R.id.tv_address);

        }
    }
}
