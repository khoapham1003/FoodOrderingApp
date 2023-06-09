package com.example.foodorderingapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    Button btnChangeAva, btnSave,btnBack;
    CircleImageView profileImg;
    DatePickerDialog datePickerDialog;
    EditText edtUserName, edtUserDoB, edtUserEmail, edtUserPhone, edtUserCountry;

    String[] items = {"Male","Female"};
    AutoCompleteTextView edtUserGender;
    ArrayAdapter<String> adapterItems;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        broadcastReceiver = new ConnectionReceiver();
        registerNetworkBroadcast();

        profileImg = (CircleImageView) findViewById(R.id.profile_img);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtUserDoB = (EditText) findViewById(R.id.edtUserDoB);
        edtUserEmail = (EditText) findViewById(R.id.edtUserEmail);
        edtUserGender = (AutoCompleteTextView) findViewById(R.id.edtUserGender);
        edtUserPhone = (EditText) findViewById(R.id.edtUserPhone);
        edtUserCountry = (EditText) findViewById(R.id.edtUserCountry);
        btnBack = (Button) findViewById(R.id.btnback_EP);
        btnSave = (Button) findViewById(R.id.btnSave_EP);
        btnChangeAva = (Button) findViewById(R.id.btnChangeAva);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_gender, items);
        edtUserGender.setAdapter(adapterItems);

        if (firebaseAuth.getCurrentUser() != null) {
            if (!User.isEmailVerified()) {
                Toast.makeText(EditProfileActivity.this, "Email is not verify. \n Please verify it.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }
        edtUserGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                edtUserGender.setText(item);
            }
        });

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        edtUserDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtUserDoB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
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
                else if (!(UserGender.equals("Male") ||UserGender.equals("Female")))
                {
                    edtUserGender.setError("Gender must be Male or Female");
                    edtUserGender.requestFocus();
                }
                else if (UserPhone.isEmpty())
                {
                    edtUserPhone.setError("Enter Phone");
                    edtUserPhone.requestFocus();
                }
                else if (UserPhone.length() > 12 || UserPhone.length() < 9 )
                {
                    edtUserPhone.setError("Phone Number only have 10-11 number");
                    edtUserPhone.requestFocus();
                }
                else if(!checkNumber(UserPhone))
                {
                    edtUserPhone.setError("Phone Number must be number");
                    edtUserPhone.requestFocus();
                }
                else if (UserCountry.isEmpty())
                {
                    edtUserCountry.setError("Enter Country");
                    edtUserCountry.requestFocus();
                }
                else if (!checkLetter(UserCountry))
                {
                    edtUserCountry.setError("Country must be the character");
                    edtUserCountry.requestFocus();
                } else {
                    database.collection("User")
                            .document(User.getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("Name", UserName.trim());
                                        user.put("DoB", UserDoB);
                                        user.put("Email", UserEmail.trim());
                                        user.put("Phone", UserPhone.trim());
                                        user.put("Gender", UserGender);
                                        user.put("Country", UserCountry.trim());
                                        database.collection("User").document(User.getUid()).update(user);
                                        btnBack.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(EditProfileActivity.this, MyProfileActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
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
    public static boolean checkNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++)
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        return true;
    }
    public static boolean checkLetter(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++)
            if (!Character.isLetter(str.charAt(i)) && !(Character.compare(' ',str.charAt(i))==0)) {
                return false;
            }
        return true;
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