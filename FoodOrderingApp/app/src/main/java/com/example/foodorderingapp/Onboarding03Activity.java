package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Onboarding03Activity extends AppCompatActivity {
    Button btn_next_to_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding03);
        btn_next_to_sign = (Button) findViewById(R.id.btn_nextToSign);
        btn_next_to_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding03Activity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}