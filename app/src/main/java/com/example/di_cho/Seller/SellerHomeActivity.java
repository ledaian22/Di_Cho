package com.example.di_cho.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.di_cho.LoginBackUp;
import com.example.di_cho.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SellerHomeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_add:
                    return true;
                case R.id.navigation_logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(SellerHomeActivity.this, LoginBackUp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    private void logOut() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView bottomNav = findViewById(R.id.nav_seller_view);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}