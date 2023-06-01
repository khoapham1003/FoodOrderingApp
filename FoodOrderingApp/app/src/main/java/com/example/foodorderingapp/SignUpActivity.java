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

    EditText edtEmail, edtPassword, edtCfPassword;
    Button SignUp;
    TextView SignIn;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
                if(email.isEmpty())
                {
                    edtEmail.setError("Enter Email");
                    edtEmail.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty())
                {
                    edtPassword.setError("Enter password");
                    edtPassword.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<6)
                {
                    edtPassword.setError("Password should be greater than 6 character");
                    edtPassword.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Password should be greater than 6 character", Toast.LENGTH_SHORT).show();
                }
                else if(confirm_password.isEmpty())
                {
                    edtCfPassword.setError("Enter confirm password");
                    edtCfPassword.requestFocus();
                    Toast.makeText(SignUpActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else if(!confirm_password.equals(password))
                {
                    edtPassword.setError("Password not matched");
                    edtPassword.requestFocus();
                    edtCfPassword.setError("Password not matched");
                    edtCfPassword.requestFocus();
                    edtPassword.setText("");
                    edtCfPassword.setText("");
                    Toast.makeText(SignUpActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        sendverificationEmail();
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("Email", email);
                                        user.put("Password", password);
                                        user.put("Data", false);
                                        database.collection("User").document(User.getUid()).set(user);
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(e instanceof FirebaseAuthUserCollisionException)
                                    {
                                        edtEmail.setError("Email Already Registered");
                                        edtEmail.requestFocus();
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUpActivity.this,"SignUp Fail!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
    private void LoadData(View v)
    {

    }
    private void sendverificationEmail() {
        if(firebaseAuth.getCurrentUser() != null)
        {
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this, "Email has been sent to your email address. \nPlease check your trash email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this,"Log Up fail!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}