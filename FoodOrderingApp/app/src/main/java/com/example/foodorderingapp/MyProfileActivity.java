package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {
    Button btnEditProfile, btnBack;
    LinearLayout btnToCart, btnToHome;
    TextView tvName, btnLogOut, btnChangePassword;
    CircleImageView AvatarImg;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();

        btnToCart = (LinearLayout) findViewById(R.id.btnToCart);
        btnToHome = (LinearLayout) findViewById(R.id.btnToHome);
        AvatarImg = (CircleImageView) findViewById(R.id.Avatar1_img1);
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile_MP1);
        btnBack = (Button) findViewById(R.id.btn_backMP);
        tvName = (TextView) findViewById(R.id.Name_MP1);
        btnLogOut = (TextView) findViewById(R.id.btnLogOut_MP1);
        btnChangePassword = (TextView) findViewById(R.id.btnChangePassword_MP1);

        btnToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
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

        StorageReference profileRef = storageReference.child("users/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(AvatarImg);

            }
        });
        String imageUrl = getIntent().getStringExtra("image_url");
        Picasso.get().load(imageUrl).into(AvatarImg);
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