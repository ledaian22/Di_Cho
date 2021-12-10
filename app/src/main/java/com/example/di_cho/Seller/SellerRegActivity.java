package com.example.di_cho.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.di_cho.LoginBackUp;
import com.example.di_cho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegActivity extends AppCompatActivity {
    private EditText edSellerName, edSellerPhone ,edSellerEmail, edSellerPass, edSellerShopName;
    private Button btnSellerLogin, btnSellerRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_reg);
        InitUI();
        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        btnSellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });

        btnSellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerRegActivity.this,SellerLoginActivity.class);
                startActivity(i);
            }
        });


    }

    private void registerSeller() {
        String name = edSellerName.getText().toString().trim();
        String phone = edSellerPhone.getText().toString().trim();
        String pass = edSellerPass.getText().toString().trim();
        String email = edSellerEmail.getText().toString().trim();
        String shopName = edSellerShopName.getText().toString().trim();

        if (!name.equals("") && !phone.equals("") && !pass.equals("") && !email.equals("") && !shopName.equals("")){

            loadingBar.setTitle("Creating Seller Account");
            loadingBar.setMessage("Checking...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                final DatabaseReference rootRef;
                                rootRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

                                String sid = mAuth.getCurrentUser().getUid();

                                HashMap<String,Object> sellerMap = new HashMap<>();
                                sellerMap.put("sid",sid);
                                sellerMap.put("name",name);
                                sellerMap.put("phone",phone);
                                sellerMap.put("email",email);
                                sellerMap.put("shopName",shopName);

                                rootRef.child("Sellers").child(sid).updateChildren(sellerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                         loadingBar.dismiss();
                                        Toast.makeText(SellerRegActivity.this,"Seller Register successfully",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SellerRegActivity.this, SellerHomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }
                        }
                    });
        } else {
            Toast.makeText(this,"You're not provide enough information",Toast.LENGTH_SHORT).show();
        }

    }

    private void InitUI() {
        edSellerName = findViewById(R.id.ed_seller_reg_Name);
        edSellerPhone = findViewById(R.id.ed_seller_reg_Phone);
        edSellerEmail = findViewById(R.id.ed_seller_reg_Email);
        edSellerPass = findViewById(R.id.ed_seller_reg_Password);
        edSellerShopName = findViewById(R.id.ed_seller_reg_shopName);
        btnSellerLogin = findViewById(R.id.btn_seller_toLogin);
        btnSellerRegister = findViewById(R.id.btn_seller_reg);
    }
}