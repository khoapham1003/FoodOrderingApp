package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Onboarding02Activity extends AppCompatActivity {
    Button btn_next_to_03;
    TextView txv_skip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding02);
        btn_next_to_03 = (Button) findViewById(R.id.btn_nextTo03);
        txv_skip = (TextView) findViewById(R.id.txv_Skip);

        btn_next_to_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding02Activity.this, Onboarding03Activity.class);
                startActivity(intent);
            }
        });
        txv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding02Activity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}