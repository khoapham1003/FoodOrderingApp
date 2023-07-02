package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderCompleteActivity extends AppCompatActivity {
    Button btn_back_to_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        btn_back_to_home = (Button) findViewById(R.id.btn_backToHome);

        btn_back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCompleteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}