package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodorderingapp.classes.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Onboarding00Activity extends AppCompatActivity {
    Button btn_next_to_01;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colrefCustomer = db.collection("Customer");
    Button btn_save_db;


//    DocumentReference docrefCustomer = db.collection("Customer").document("Data");
//    Button btn_read_db;
//    Button btn_add_db;
//    Button btn_remove_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding00);

        Customer customer1 = new Customer(1003, "Pham Minh C", "Male", "14/05/2003", "vana@gmail.com",
                "0909123409", "Viet Nam", "minhkhoa21521003", "21521003", "motcailinknaodo");
        btn_next_to_01 = (Button) findViewById(R.id.btn_nextTo01);


//      btn_read_db = (Button) findViewById(R.id.btn_readDB);
//        btn_add_db = (Button) findViewById(R.id.btn_addDB);
//        btn_remove_db = (Button) findViewById(R.id.btn_removeDB);
//        btn_read_db.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                docrefCustomer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            Object id = documentSnapshot.get("ID");
//                            String name = documentSnapshot.getString("Name");
//
//                            //Map<String,Object> map = documentSnapshot.getData();
//                            Toast.makeText(getApplicationContext(), id + ": " + name, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Failure Read", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//        });


        btn_save_db = (Button) findViewById(R.id.btn_saveDB);
        btn_save_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomerToFireStore(customer1);
            }

            private void addCustomerToFireStore(Customer cus) {

                colrefCustomer.add(cus).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Customer Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Fail to add Customer", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_next_to_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboarding00Activity.this, Onboarding01Activity.class);
                startActivity(intent);
            }
        });
    }
}


