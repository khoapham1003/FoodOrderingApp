package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_PasswordActivity extends AppCompatActivity {

    EditText email_FP;
    Button SendEmail,back_btn_forgot;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_FP = (EditText) findViewById(R.id.email_FP);
        SendEmail = (Button) findViewById(R.id.btnSend_FP);
        back_btn_forgot= (Button)  findViewById(R.id.back_btn_forgot);

        back_btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgot_PasswordActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email_FP.getText().toString();

                if (userEmail.isEmpty()) {
                    email_FP.setError("Enter Email");
                    email_FP.requestFocus();
                    Toast.makeText(Forgot_PasswordActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Forgot_PasswordActivity.this, "Please check your email!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Forgot_PasswordActivity.this, SignInActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Forgot_PasswordActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}