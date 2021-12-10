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

import com.example.di_cho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {
    private EditText edSellerEmail, edSellerPass;
    private Button btnLogin;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        InitUI();
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSeller();
            }
        });
    }

    private void loginSeller() {
        String pass = edSellerPass.getText().toString().trim();
        String email = edSellerEmail.getText().toString().trim();

        if (!pass.equals("") && !email.equals("")) {

            loadingBar.setTitle("Login in Seller Account");
            loadingBar.setMessage("Checking...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        finish();
                        loadingBar.dismiss();
                    }

                }
            });
        }
        else {
            Toast.makeText(this,"You're not provide enough information",Toast.LENGTH_SHORT).show();
        }
    }

    private void InitUI() {
        edSellerEmail = findViewById(R.id.ed_seller_login_Email);
        edSellerPass = findViewById(R.id.ed_seller_login_Password);
        btnLogin = findViewById(R.id.btn_seller_login);
    }
}