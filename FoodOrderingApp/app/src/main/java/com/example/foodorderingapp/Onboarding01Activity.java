package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Onboarding01Activity extends AppCompatActivity {
    Button btn_next_to_02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding01);
        btn_next_to_02 = (Button) findViewById(R.id.btn_nextTo02);
        btn_next_to_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding01Activity.this, Onboarding02Activity.class);
                startActivity(intent);
            }
        });
    }
}