package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodorderingapp.classes.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Onboarding00Activity extends AppCompatActivity {
    Button btn_next_to_01;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding00);


    Customer cus=new Customer(1001,"Nguyen Van A","Male","14/05/2003","vana@gmail.com",
            "0909123409","Viet Nam","minhkhoa21521003","21521003","motcailinknaodo");

    firestore = FirebaseFirestore.getInstance();

    Map<String,Object> users = new HashMap<>();
        users.put("second1","test 04");
        users.put("second2","test 05");
        users.put("second3","test 06");

firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
    @Override
    public void onSuccess(DocumentReference documentReference) {
        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
    }
}).addOnFailureListener(new OnFailureListener(){
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_LONG).show();
    }
});



        btn_next_to_01 = (Button) findViewById(R.id.btn_nextTo01);
        btn_next_to_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding00Activity.this, Onboarding01Activity.class);
                startActivity(intent);
            }
        });
    }
}