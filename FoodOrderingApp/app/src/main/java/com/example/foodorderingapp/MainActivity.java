package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderingapp.classes.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout btnToCart, btnToUser;
    TextView btnProfile;
    RecyclerView rvcData;
    FoodAdapter foodAdapter;
    SearchView searchBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();

        btnProfile = (TextView) findViewById(R.id.btnProfile);
        btnToCart = (LinearLayout) findViewById(R.id.btnToCart);
        btnToUser = (LinearLayout) findViewById(R.id.btnToUser);

        database.collection("User")
                .document(User.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        database.collection("User")
                                .document(User.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        String Name = documentSnapshot.getString("Name");
                                        btnProfile.setText(String.format("Hi, %s", Name));
                                    }
                                });
                    }
                });
        btnToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        btnToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        data
        rvcData = findViewById(R.id.recyc_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvcData.setLayoutManager(gridLayoutManager);
        foodAdapter = new FoodAdapter(this, getlistFood());
        rvcData.setAdapter(foodAdapter);
//      search
        searchBar = findViewById(R.id.tv_search);
        searchBar.clearFocus();
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private List<Food> getlistFood() {
        List<Food> list1 = new ArrayList<>();
        db.collection("Menu")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Food obj = d.toObject(Food.class);
                            list1.add(obj);
                        }
                        foodAdapter.notifyDataSetChanged();
                    }
                });
        return list1;
    }
    private void filterList(String text) {
        foodAdapter.setFilterList(text);
        if (text == null || text == "" || text.isEmpty()) {
            foodAdapter = new FoodAdapter(this, getlistFood());
            rvcData.setAdapter(foodAdapter);
        }
    }
    protected void registerNetworkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    protected void unregisterNetwork() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        if (foodAdapter != null) {
            foodAdapter.release();
        }
        unregisterNetwork();
    }
}






