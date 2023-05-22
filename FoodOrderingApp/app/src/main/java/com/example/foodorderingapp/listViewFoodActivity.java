package com.example.foodorderingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class listViewFoodActivity extends AppCompatActivity {

    TextView titleGetFood;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);

        titleGetFood = (TextView) findViewById(R.id.titleFood);
        Intent receiverTitle = getIntent();
        String receiverValue = receiverTitle.getStringExtra("KEY_SENDER");
        titleGetFood.setText(receiverValue);


    }
}
