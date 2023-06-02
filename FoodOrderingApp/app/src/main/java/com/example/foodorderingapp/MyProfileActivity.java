package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyProfileActivity extends AppCompatActivity {

    Button btnEditProfile,btnBack;
    TextView tvName, btnLogOut, btnChangePassword;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        btnEditProfile = (Button) findViewById(R.id.btnEditProfile_MP1);
        btnBack = (Button) findViewById(R.id.btn_backMP);
        tvName = (TextView) findViewById(R.id.Name_MP1);
        btnLogOut = (TextView) findViewById(R.id.btnLogOut_MP1);
        btnChangePassword = (TextView) findViewById(R.id.btnChangePassword_MP1);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(MyProfileActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        database.collection("User")
                .document(User.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        database.collection("User")
                                .document(User.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        String Name = documentSnapshot.getString("Name");
                                        tvName.setText(Name);
                                    }
                                });
                    }
                });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, Change_PasswordActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}