package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FilledProfileActivity extends AppCompatActivity {
//    Button btnEditProfile;
//    TextView Name, btnLogOut, btnChangePassword;
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filled_profile);

//        btnEditProfile = (Button) findViewById(R.id.btnEditProfile_MP1);
//        Name = (TextView) findViewById(R.id.Name_MP1);
//        btnLogOut = (TextView) findViewById(R.id.btnLogOut_MP1);
//        btnChangePassword = (TextView) findViewById(R.id.btnChangePassword_MP1);
//
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //firebaseAuth.signOut();
//                Intent intent = new Intent(MyProfileActivity.this, SignInActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        btnChangePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MyProfileActivity.this, Change_PasswordActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });

    }
}