package com.example.di_cho;
// Hoàng Bá Minh thiết kế giao diện
//

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.Adapter.ViewPagerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class LoginScreen extends AppCompatActivity {
    private TabLayout tbl_login;
    private ViewPager vpg_login;
    private FloatingActionButton fab_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        anhXa();
        viewPager();
       //FloatingActionButton();
    }

    private void FloatingActionButton(){
        fab_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
            }
        });
    }

    private void viewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpg_login.setAdapter(viewPagerAdapter);
        tbl_login.setupWithViewPager(vpg_login);
    }

    private void anhXa() {
        tbl_login = findViewById(R.id.tbl_login);
        vpg_login = findViewById(R.id.vpg_login);
        fab_google = findViewById(R.id.fab_google);
    }
}