package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    Button SignUp;
    EditText edtEmail, edtPassword, edtCfPassword;
    TextView SignIn;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();

        edtEmail = (EditText) findViewById(R.id.email_SU);
        edtPassword = (EditText) findViewById(R.id.passWord_SU);
        edtCfPassword = (EditText) findViewById(R.id.CFPassWord_SU);
        SignUp = (Button) findViewById(R.id.btnSignUp);
        SignIn = (TextView) findViewById(R.id.SignIn_SU);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, confirm_password;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());
                confirm_password = String.valueOf(edtCfPassword.getText());
                if (password.isEmpty() && email.isEmpty() && confirm_password.isEmpty()) {
                    edtEmail.setError("Enter Email");
                    edtPassword.setError("Enter password");
                    edtCfPassword.setError("Enter confirm password");
                }
                if (email.isEmpty()) {
                    edtEmail.setError("Enter Email");
                    edtEmail.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    edtPassword.setError("Enter password");
                    edtPassword.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    edtPassword.setError("Password should be greater than 6 character");
                    edtPassword.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Password should be greater than 6 character", Toast.LENGTH_SHORT).show();
                } else if (confirm_password.isEmpty()) {
                    edtCfPassword.setError("Enter confirm password");
                    edtCfPassword.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!confirm_password.equals(password)) {
                    edtPassword.setError("Password not matched");
                    edtPassword.requestFocus();
                    edtCfPassword.setError("Password not matched");
                    edtCfPassword.requestFocus();
                    edtPassword.setText("");
                    edtCfPassword.setText("");
                    Toast.makeText(SignUpActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (firebaseAuth.getCurrentUser() != null) {
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("Email", email);
                                            user.put("Password", password);
                                            user.put("Data", false);
                                            user.put("Name", "New User");
                                            database.collection("User").document(firebaseAuth.getCurrentUser().getUid()).set(user);
                                            sendverificationEmail();
                                        }
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (e instanceof FirebaseAuthUserCollisionException) {
                                        edtEmail.setError("Email Already Registered");
                                        edtEmail.requestFocus();
                                    } else if (!edtEmail.getText().toString().contains("@"))
                                    {
                                        Toast.makeText(SignUpActivity.this, "Please include an @ in the email", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, "SignUp Fail!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    private void sendverificationEmail() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Email has been sent to your email address. \nPlease check your trash email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Log Up fail!", Toast.LENGTH_SHORT).show();
                    }
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