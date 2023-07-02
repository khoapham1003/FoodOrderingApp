package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import kotlin.CharCodeKt;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;

public class SignInActivity extends AppCompatActivity {
    Button SignIn;
    EditText edtEmail, edtPassword;
    TextView SignUp, Forgot_Password;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = firebaseAuth.getCurrentUser();
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();

        edtEmail = (EditText) findViewById(R.id.Emal_SI);
        edtPassword = (EditText) findViewById(R.id.PassWord_SI);
        SignIn = (Button) findViewById(R.id.btnSignIn);
        SignUp = (TextView) findViewById(R.id.SignUp_SI);
        Forgot_Password = (TextView) findViewById(R.id.forgot_password);

        Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, Forgot_PasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());

                if (email.isEmpty()) {
                    edtEmail.setError("Enter Email");
                    edtEmail.requestFocus();
                    Toast.makeText(SignInActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    edtPassword.setError("Enter password");
                    edtPassword.requestFocus();
                    Toast.makeText(SignInActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (firebaseAuth.getCurrentUser() != null) {
                                        if (task.isSuccessful()) {
                                            CheckData();
                                        } else {
                                            database.collection("User")
                                                    .document(firebaseAuth.getCurrentUser().getUid())
                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            String email_check = documentSnapshot.getString("Email");
                                                            if (!email.equals(email_check)) {
                                                                edtEmail.requestFocus();
                                                                Toast.makeText(SignInActivity.this, "Email Wrong!", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                edtPassword.requestFocus();
                                                                Toast.makeText(SignInActivity.this, "Password Wrong!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });
                }
//
            }
        });
    }

    void CheckData() {
        if (firebaseAuth.getCurrentUser() != null) {
            database.collection("User")
                    .document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Boolean CheckData = documentSnapshot.getBoolean("Data");
                            Intent intent;
                            if (Boolean.FALSE.equals(CheckData)) {
                                intent = new Intent(SignInActivity.this, EditProfileActivity.class);
                            } else {
                                intent = new Intent(SignInActivity.this, MainActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        }
                    });
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetwork();
    }
}