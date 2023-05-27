package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    EditText edtUserName,edtUserDoB,edtUserEmail,edtUserGender,edtUserPhone,edtUserCountry;
    Button btnChangeAva, btnSave;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtUserDoB = (EditText) findViewById(R.id.edtUserDoB);
        edtUserEmail = (EditText) findViewById(R.id.edtUserEmail);
        edtUserGender = (EditText) findViewById(R.id.edtUserGender);
        edtUserPhone = (EditText) findViewById(R.id.edtUserPhone);
        edtUserCountry = (EditText) findViewById(R.id.edtUserCountry);

        btnSave = (Button) findViewById(R.id.btnSave_EP);
        btnChangeAva = (Button) findViewById(R.id.btnChangeAva);

        database.collection("User")
                .document(User.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Boolean CheckData = documentSnapshot.getBoolean("Data");
                        if(Boolean.TRUE.equals(CheckData))
                        {
                            database.collection("User")
                                    .document(User.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            String Name = documentSnapshot.getString("Name");
                                            edtUserName.setText(Name);
                                            String DoB = documentSnapshot.getString("DoB");
                                            edtUserDoB.setText(DoB);
                                            String Email = documentSnapshot.getString("Email");
                                            edtUserEmail.setText(Email);
                                            String Gender = documentSnapshot.getString("Gender");
                                            edtUserGender.setText(Gender);
                                            String Phone = documentSnapshot.getString("Phone");
                                            edtUserPhone.setText(Phone);
                                            String Country = documentSnapshot.getString("Country");
                                            edtUserCountry.setText(Country);
                                        }
                                    });
                        }
                        else
                        {
                            edtUserName.setText("");
                            edtUserDoB.setText("");
                            edtUserEmail.setText("");
                            edtUserGender.setText("");
                            edtUserPhone.setText("");
                            edtUserCountry.setText("");
                        }
                    }
                });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName, UserDoB, UserEmail, UserGender, UserPhone,UserCountry;
                UserName = String.valueOf(edtUserName.getText());
                UserDoB = String.valueOf(edtUserDoB.getText());
                UserEmail = String.valueOf(edtUserEmail.getText());
                UserGender = String.valueOf(edtUserGender.getText());
                UserPhone = String.valueOf(edtUserPhone.getText());
                UserCountry = String.valueOf(edtUserCountry.getText());
                if(UserName.isEmpty())
                {
                    edtUserName.setError("Enter Name");
                    edtUserName.requestFocus();
                }
                else if(UserDoB.isEmpty())
                {
                    edtUserDoB.setError("Enter Day of Birth");
                    edtUserDoB.requestFocus();
                }
                else if (UserEmail.isEmpty())
                {
                    edtUserEmail.setError("Enter Email");
                    edtUserEmail.requestFocus();
                }
                else if (UserGender.isEmpty())
                {
                    edtUserGender.setError("Enter Gender");
                    edtUserGender.requestFocus();
                }
                else if (UserPhone.isEmpty())
                {
                    edtUserPhone.setError("Enter Phone");
                    edtUserPhone.requestFocus();
                }
                else if (UserCountry.isEmpty())
                {
                    edtUserCountry.setError("Enter Country");
                    edtUserCountry.requestFocus();
                }
                else
                {
                    database.collection("User")
                            .document(User.getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("Name", UserName);
                                        user.put("DoB", UserDoB);
                                        user.put("Email",UserEmail);
                                        user.put("Phone", UserPhone);
                                        user.put("Gender", UserGender);
                                        user.put("Country", UserCountry);
                                        database.collection("User").document(User.getUid()).update(user);
                                    }
                                }
                            });
                    Map<String, Object> user = new HashMap<>();
                    user.put("Data", true);
                    database.collection("User").document(User.getUid()).update(user);
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}