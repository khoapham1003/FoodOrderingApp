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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import io.grpc.LoadBalancer;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<Cart> list;
    int totalAmount = 0;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getFoodName());
        holder.price.setText(list.get(position).getPrice() + "VND");
        holder.rvCount.setText("(" + list.get(position).getRvCount() + ")");
        holder.star.setText(list.get(position).getRvStar());
        holder.totalQuantity.setText(list.get(position).getQuantity());
        Glide.with(holder.foodImg.getContext()).load(list.get(position).getFoodImg()).into(holder.foodImg);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Cart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(list.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        /*holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        int drawableRecourseID = holder.itemView.getContext().getResources().getIdentifier(list.get(position).getFoodImg()
                , "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.foodImg.getContext()).load(food.getFoodImg()).into(holder.foodImg);*/

        //Comment: totalAmount pass to Cart Ativity
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
        ImageView foodImg,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_txtview_foodname);
            price = itemView.findViewById(R.id.id_txtview_price);
            //totalPrice = itemView.findViewById(R.id.id_txt_Total);
            totalQuantity = itemView.findViewById(R.id.id_txtview_numberOfItems);
            star = itemView.findViewById(R.id.id_rvStar);
            rvCount = itemView.findViewById(R.id.id_rvCount);
            foodImg = itemView.findViewById(R.id.imageViewFood);
            delete = itemView.findViewById(R.id.delete_item_btn);
        }
    }
}