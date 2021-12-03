package com.example.di_cho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterBackUp extends AppCompatActivity {
    EditText edName, edPhone, edPass;
    Button btnRegister;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_back_up);

        edName = findViewById(R.id.ed_reg_name);
        edPhone = findViewById(R.id.ed_reg_phonenumber);
        edPass = findViewById(R.id.ed_reg_password);
        btnRegister = findViewById(R.id.btnRegister);
        loadingBar = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String name = edName.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
         if (TextUtils.isEmpty(name)){
             Toast.makeText(this,"Leave no blank",Toast.LENGTH_SHORT).show();
         } else if (TextUtils.isEmpty(pass)){
             Toast.makeText(this,"Leave no blank",Toast.LENGTH_SHORT).show();
         } else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Leave no blank",Toast.LENGTH_SHORT).show();
        } else {
             loadingBar.setTitle("Creating Acoount");
             loadingBar.setMessage("Checking...");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();

             ValidatePhoneNumber(name,phone,pass);

         }
    }

    private void ValidatePhoneNumber( final String name,final String phone,final String pass) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child("User").child(phone).exists()){
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("name",name);
                    userDataMap.put("phone",phone);
                    userDataMap.put("pass",pass);


                    rootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterBackUp.this,"Success",Toast.LENGTH_SHORT).show();
                                         loadingBar.dismiss();
                                        Intent i = new Intent(RegisterBackUp.this, LoginBackUp.class);
                                        startActivity(i);
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterBackUp.this,"Plaese try again",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                } else {
                    Toast.makeText(RegisterBackUp.this,"Phone is already exist",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent i = new Intent(RegisterBackUp.this, LoginBackUp.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}