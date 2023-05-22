package com.example.foodorderingapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class listViewFoodActivity extends AppCompatActivity {

    TextView titleGetFood, rvStart, rvCount, kcal, unit, cookTime, description, price;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);

        titleGetFood = (TextView) findViewById(R.id.titleFood);
        rvStart = (TextView) findViewById(R.id.rvStart);
        rvCount = (TextView) findViewById(R.id.rvCount);
        kcal = (TextView) findViewById(R.id.kcal);
        unit = (TextView) findViewById(R.id.unit);
        cookTime = (TextView) findViewById(R.id.cookTime);
        description = (TextView) findViewById(R.id.des);
        price = (TextView) findViewById(R.id.price);


        Intent receiverTitle = getIntent();
        String receiverValue = receiverTitle.getStringExtra("KEY_SENDER");
        titleGetFood.setText(receiverValue);

//        create data
//        CollectionReference menu = db.collection("Menu");
//
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("name", "Banh mi thit nguoi");
//        data1.put("id", 0001);
//        data1.put("price", 35000);
//        data1.put("rvCount", 200);
//        data1.put("rvStart", 4.3);
//        data1.put("amount", 20);
//        data1.put("ImgSrc", "https://images.unsplash.com/photo-1682685797366-715d29e33f9d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80");
//        data1.put("kcal", 180);
//        data1.put("unit",   "4 breads");
//        data1.put("cookTime",   20);
//        data1.put("des",   "Banh mi thit nguoi");
//        menu.document("banhmithitnguoi").set(data1);
//
//        Map<String, Object> data2 = new HashMap<>();
//        data2.put("name", "Banh mi trung");
//        data2.put("id", 0002);
//        data2.put("price", 33000);
//        data2.put("rvCount", 100);
//        data2.put("rvStart", 4.3);
//        data2.put("amount", 10);
//        data2.put("ImgSrc", "https://images.unsplash.com/photo-1682685797366-715d29e33f9d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80");
//        data2.put("kcal", 180);
//        data2.put("unit",   "4 breads");
//        data2.put("cookTime",   30);
//        data2.put("des",   "Banh mi trung");
//        menu.document("banhmitrung").set(data2);
//
//        Map<String, Object> data3 = new HashMap<>();
//        data3.put("name", "Banh mi cha bong");
//        data3.put("id", 0003);
//        data3.put("price", 31000);
//        data3.put("rvCount", 130);
//        data3.put("rvStart", 4.1);
//        data3.put("amount", 10);
//        data3.put("ImgSrc", "https://images.unsplash.com/photo-1682685797366-715d29e33f9d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80");
//        data3.put("kcal", 180);
//        data3.put("unit",   "4 breads");
//        data3.put("cookTime",   10);
//        data3.put("des",   "Banh mi cha bong");
//        menu.document("banhmichabong").set(data3);
//

//        end create data

//        get data
        DocumentReference docRef = db.collection("Menu").document("banhmichabong");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG,"df"+ document.getData().get("kcal"));
                        description.setText((CharSequence) document.getData().get("des"));
                        rvStart.setText((CharSequence) document.getData().get("rvStart").toString());
                        rvCount.setText((CharSequence) document.getData().get("rvCount").toString());
                        price.setText((CharSequence) document.getData().get("price").toString());
                        unit.setText((CharSequence) document.getData().get("unit").toString());
                        kcal.setText((CharSequence) document.getData().get("kcal").toString());
                        cookTime.setText((CharSequence) document.getData().get("cookTime").toString());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
//        end get data

    }
}
