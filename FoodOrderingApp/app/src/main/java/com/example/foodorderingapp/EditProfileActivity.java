package com.example.foodorderingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    CircleImageView profileImg;
    EditText edtUserName, edtUserDoB, edtUserEmail, edtUserGender, edtUserPhone, edtUserCountry;
    Button btnChangeAva, btnSave;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        profileImg = (CircleImageView) findViewById(R.id.profile_img);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtUserDoB = (EditText) findViewById(R.id.edtUserDoB);
        edtUserEmail = (EditText) findViewById(R.id.edtUserEmail);
        edtUserGender = (EditText) findViewById(R.id.edtUserGender);
        edtUserPhone = (EditText) findViewById(R.id.edtUserPhone);
        edtUserCountry = (EditText) findViewById(R.id.edtUserCountry);

        btnSave = (Button) findViewById(R.id.btnSave_EP);
        btnChangeAva = (Button) findViewById(R.id.btnChangeAva);
        StorageReference profileRef = storageReference.child("users/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImg);

            }
        });

        database.collection("User")
                .document(User.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Boolean CheckData = documentSnapshot.getBoolean("Data");
                        if (Boolean.TRUE.equals(CheckData)) {
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
                        } else {
                            edtUserName.setText("");
                            edtUserDoB.setText("");
                            String Email = documentSnapshot.getString("Email");
                            edtUserEmail.setText(Email);
                            edtUserGender.setText("");
                            edtUserPhone.setText("");
                            edtUserCountry.setText("");
                        }
                    }
                });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String UserName, UserDoB, UserEmail, UserGender, UserPhone, UserCountry;
                UserName = String.valueOf(edtUserName.getText());
                UserDoB = String.valueOf(edtUserDoB.getText());
                UserEmail = String.valueOf(edtUserEmail.getText());
                UserGender = String.valueOf(edtUserGender.getText());
                UserPhone = String.valueOf(edtUserPhone.getText());
                UserCountry = String.valueOf(edtUserCountry.getText());
                if (UserName.isEmpty()) {
                    edtUserName.setError("Enter Name");
                    edtUserName.requestFocus();
                } else if (UserDoB.isEmpty()) {
                    edtUserDoB.setError("Enter Day of Birth");
                    edtUserDoB.requestFocus();
                } else if (UserEmail.isEmpty()) {
                    edtUserEmail.setError("Enter Email");
                    edtUserEmail.requestFocus();
                } else if (UserGender.isEmpty()) {
                    edtUserGender.setError("Enter Gender");
                    edtUserGender.requestFocus();
                } else if (UserPhone.isEmpty()) {
                    edtUserPhone.setError("Enter Phone");
                    edtUserPhone.requestFocus();
                } else if (UserCountry.isEmpty()) {
                    edtUserCountry.setError("Enter Country");
                    edtUserCountry.requestFocus();
                } else {
                    database.collection("User")
                            .document(User.getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("Name", UserName);
                                        user.put("DoB", UserDoB);
                                        user.put("Email", UserEmail);
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

        btnChangeAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                // profileImg.setImageURI(imageUri);
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        // upload image to firebase storage
        StorageReference fileRef = storageReference.child("users/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImg);
                        Intent intent = new Intent(EditProfileActivity.this, MyProfileActivity.class);
                        intent.putExtra("image_url", uri.toString());

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(EditProfileActivity.this, "Failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}