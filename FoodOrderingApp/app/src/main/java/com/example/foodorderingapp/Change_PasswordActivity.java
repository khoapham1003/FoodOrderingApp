package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Change_PasswordActivity extends AppCompatActivity {

    EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    Button btnChangePassword;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edtOldPassword = (EditText) findViewById(R.id.edtOldPassword);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmNewPassword);
      btnChangePassword = (Button) findViewById(R.id.btnChangePassword_CP);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OldPassword, NewPassword, ConfirmNewPassword;
                OldPassword = String.valueOf(edtOldPassword.getText());
                NewPassword = String.valueOf(edtNewPassword.getText());
                ConfirmNewPassword = String.valueOf(edtConfirmPassword.getText());
                if(OldPassword.isEmpty())
                {
                    edtOldPassword.setError("Enter Old Password");
                    edtOldPassword.requestFocus();
                }
                else if(NewPassword.isEmpty())
                {
                    edtNewPassword.setError("Enter New Password");
                    edtNewPassword.requestFocus();
                }
                else if(ConfirmNewPassword.isEmpty())
                {
                    edtConfirmPassword.setError("Enter Confirm New Password");
                    edtConfirmPassword.requestFocus();
                }
                else if(NewPassword.length()<6)
                {
                    edtNewPassword.setError("Password should be greater than 6 character");
                    edtNewPassword.requestFocus();
                }
                else if(!ConfirmNewPassword.equals(NewPassword))
                {
                    edtNewPassword.setError("Password not matched");
                    edtNewPassword.requestFocus();
                    edtConfirmPassword.setError("Password not matched");
                    edtConfirmPassword.requestFocus();
                    edtNewPassword.setText("");
                    edtConfirmPassword.setText("");
                    Toast.makeText(Change_PasswordActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.collection("User")
                            .document(User.getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String Check_old_password = documentSnapshot.getString("Password");
                                    if(!OldPassword.equals(Check_old_password))
                                    {
                                        edtOldPassword.setError("Old Password wrong!");
                                        edtOldPassword.requestFocus();
                                    }
                                    else
                                    {
                                        User.updatePassword(NewPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            Map<String, Object> user = new HashMap<>();
                                                            user.put("Password", NewPassword);
                                                            database.collection("User").document(User.getUid()).update(user);
                                                        Toast.makeText(Change_PasswordActivity.this, "Password Updated Successfully!", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(Change_PasswordActivity.this, "Password Updated Failed! \nPlease LogOut and LogIn again.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        Intent intent = new Intent(Change_PasswordActivity.this, MyProfileActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }
}