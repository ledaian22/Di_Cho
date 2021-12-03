package com.example.di_cho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Users;
import io.paperdb.Paper;

public class LoginBackUp extends AppCompatActivity {
    private EditText edPhoneNumber, edPassword;
    private Button btnLogin, btnRegister;
    private TextView isAdmin, isNotAdmin;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    private CheckBox chk_Remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_back_up);
        InitUI();
        Paper.init(this);
        isNotAdmin.setVisibility(View.INVISIBLE);

        isAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setText("Login as Admin");
                isAdmin.setVisibility(View.INVISIBLE);
                isNotAdmin.setVisibility(View.VISIBLE);
                parentDbName="Admins";
            }
        });
        isNotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setText("Login");
                isAdmin.setVisibility(View.VISIBLE);
                isNotAdmin.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });

        loadingBar = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(LoginBackUp.this, RegisterBackUp.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

    }

    private void InitUI() {
        edPhoneNumber = findViewById(R.id.ed_login_PhoneNumer);
        edPassword = findViewById(R.id.ed_login_Password);
        btnRegister = findViewById(R.id.btnBackUpRegister);
        btnLogin = findViewById(R.id.btnBackUpLogin);
        chk_Remember = findViewById(R.id.chk_remember);
        isAdmin = findViewById(R.id.tv_isAdminTrue);
        isNotAdmin = findViewById(R.id.tv_isAdminFalse);

    }

    private void LoginUser() {
        String pass = edPassword.getText().toString();
        String phone = edPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Leave no blank",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Leave no blank",Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Login...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            
            AllowAccessToAccount(phone,pass);
        }
    }

    private void AllowAccessToAccount(String phone, String pass) {
        if (chk_Remember.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordeKey,pass);
        }


        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()){
                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPass().equals(pass)){
                            //Check Login as Admin
                            if (parentDbName.equals("Admins")){
                                Toast.makeText(LoginBackUp.this,"Logged as Admin",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent i  = new Intent(LoginBackUp.this, AdminAddStore.class);
                                startActivity(i);
                            }
                            //Check Login as User
                            else if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginBackUp.this,"Welcome",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent i  = new Intent(LoginBackUp.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                        //Check Password
                        else {
                            Toast.makeText(LoginBackUp.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                //Check if existing account
                else {
                    Toast.makeText(LoginBackUp.this,"Account do not exist",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}