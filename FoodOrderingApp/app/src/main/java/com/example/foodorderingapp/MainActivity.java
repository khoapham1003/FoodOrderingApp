package com.example.foodorderingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.foodorderingapp.classes.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    LinearLayout sushi_linear, pizza_linear, salad_linear, spaghetti_linear;
    RecyclerView rvcData;
    FoodAdapter foodAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvcData = findViewById(R.id.recyc_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rvcData.setLayoutManager(gridLayoutManager);

        foodAdapter = new FoodAdapter(this,getlistFood());


        rvcData.setAdapter(foodAdapter);


    }

    private List<Food> getlistFood(){
        List<Food> list1 = new ArrayList<>();

        db.collection("Menu")
                .get()
                 .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list =queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list){
                    Food obj = d.toObject(Food.class);
                    list1.add(obj);
                }
                foodAdapter.notifyDataSetChanged();
            }
        });

//        list.add(new Food(200, "20 mins","food for everyone", 005, "https://images.unsplash.com/photo-1682695794947-17061dc284dd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw2fHx8ZW58MHx8fHx8&auto=format&fit=crop&w=500&q=60", 230, "banh mi truyen thong", "20 banh mi", 32000, 130, 3));
//        list.add(new Food(200, "20 mins","food for everyone", 001, "https://images.unsplash.com/photo-1684749841085-f144067c42ad?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8&auto=format&fit=crop&w=500&q=60", 210, "banh mi cha ca", "20 banh mi", 33000, 130, 4));
//        list.add(new Food(200, "20 mins","food for everyone", 002, "https://images.unsplash.com/photo-1634129366530-61d3e56a84fb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxMHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60", 240, "banh mi trung", "20 banh mi", 35000, 130, 5));
//        list.add(new Food(200, "20 mins","food for everyone", 003, "https://images.unsplash.com/photo-1674574124473-e91fdcabaefc?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxNnx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60", 250, "banh mi thit nguoi", "20 banh mi", 36000, 130, 4));
//        list.add(new Food(200, "20 mins","food for everyone", 004, "https://images.unsplash.com/photo-1684752849045-810967ad990f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxN3x8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60", 260, "banh mi sua", "20 banh mi", 39000, 130, 2));


        return list1;
    }
    protected void onDestroy(){
        super.onDestroy();
        if(foodAdapter!= null){
            foodAdapter.release();
        }

    }
}