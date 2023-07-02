package com.example.foodorderingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.classes.Cart;
import com.example.foodorderingapp.classes.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.grpc.LoadBalancer;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    int totalAmount = 0;
    Context context;
    List<Cart> list;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public CartAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        totalAmount = getTotalMoney();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Cart data = list.get(position);
        holder.name.setText(data.getFoodName());
        holder.price.setText(Integer.parseInt(data.getPrice())*Integer.parseInt(data.getQuantity()) + " VND");
        holder.rvCount.setText("(" + data.getRvCount() + ")");
        holder.star.setText(data.getRvStar());
        holder.totalQuantity.setText(data.getQuantity());
        Glide.with(holder.foodImg.getContext()).load(data.getFoodImg()).into(holder.foodImg);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Cart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(data.getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //totalAmount = totalAmount - data.getTotalPrice();
                                    list.remove(data);
                                    totalAmount = getTotalMoney();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent("MyTotalAmount");
                                    intent.putExtra("totalAmount", totalAmount);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                } else {
                                    Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public int getTotalMoney() {
        int totalMoney = 0;
        for (int i = 0; i < list.size(); i++) {
            totalMoney += list.get(i).getTotalPrice();
        }
        return totalMoney;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, totalQuantity, totalPrice, rvCount, star;
        ImageView foodImg, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_txtview_foodname);
            price = itemView.findViewById(R.id.id_txtview_price);
            totalPrice = itemView.findViewById(R.id.id_txt_Total);
            totalQuantity = itemView.findViewById(R.id.id_txtview_numberOfItems);
            star = itemView.findViewById(R.id.id_rvStar);
            rvCount = itemView.findViewById(R.id.id_rvCount);
            foodImg = itemView.findViewById(R.id.imageViewFood);
            delete = itemView.findViewById(R.id.delete_item_btn);
        }
    }
}