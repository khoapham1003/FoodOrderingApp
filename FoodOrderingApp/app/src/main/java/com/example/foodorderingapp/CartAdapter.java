package com.example.foodorderingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.classes.Cart;

import java.util.List;

import io.grpc.LoadBalancer;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<Cart> list;
    int totalAmount = 0;
    public CartAdapter(Context context , List<Cart> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getFoodName());
        holder.price.setText(list.get(position).getPrice() + "VND");
        holder.rvCount.setText("(" + list.get(position).getRvCount() + ")");
        holder.star.setText(list.get(position).getRvStar());
        holder.totalQuantity.setText(list.get(position).getQuantity());
        Glide.with(holder.foodImg.getContext()).load(list.get(position).getFoodImg()).into(holder.foodImg);
        //holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        /*
        int drawableRecourseID = holder.itemView.getContext().getResources().getIdentifier(list.get(position).getFoodImg()
                ,"drawable",holder.itemView.getContext().getPackageName());
        */
        //Glide.with(context).load(list.get(position).getFoodImg()).into(holder.foodImg);


        //  Comment: totalAmount pass to Cart Ativity
        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, totalQuantity, totalPrice, rvCount, star;
        ImageView foodImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_txtview_foodname);
            price = itemView.findViewById(R.id.id_txtview_price);
            //totalPrice = itemView.findViewById(R.id.id_txt_Total);
            totalQuantity = itemView.findViewById(R.id.id_txtview_numberOfItems);
            star = itemView.findViewById(R.id.id_rvStar);
            rvCount = itemView.findViewById(R.id.id_rvCount);
            foodImg = itemView.findViewById(R.id.imageViewFood);
        }
    }
}