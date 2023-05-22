package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout sushi_linear, pizza_linear, salad_linear, spaghetti_linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sushi_linear = (LinearLayout) findViewById(R.id.sushi);
        pizza_linear = (LinearLayout) findViewById(R.id.pizza);
        salad_linear = (LinearLayout) findViewById(R.id.salad);
        spaghetti_linear = (LinearLayout) findViewById(R.id.spaghetti);


        sushi_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, listViewFoodActivity.class);
                intent.putExtra("KEY_SENDER","Sushi");
                startActivity(intent);
            }
        });
        pizza_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, listViewFoodActivity.class);
                intent.putExtra("KEY_SENDER","Pizza");
                startActivity(intent);
            }
        });
        salad_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, listViewFoodActivity.class);
                intent.putExtra("KEY_SENDER","Salad");
                startActivity(intent);
            }
        });
        spaghetti_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, listViewFoodActivity.class);
                intent.putExtra("KEY_SENDER","Spaghetti");
                startActivity(intent);
            }
        });

    }

}