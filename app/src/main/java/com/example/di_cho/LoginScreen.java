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

//Đại An Đăng nhập

public class LoginScreen extends AppCompatActivity {
    private TabLayout tbl_login;
    private ViewPager vpg_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        anhXa();
        viewPager();
    }

    private void viewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpg_login.setAdapter(viewPagerAdapter);
        tbl_login.setupWithViewPager(vpg_login);
    }

    private void anhXa() {
        tbl_login = findViewById(R.id.tbl_login);
        vpg_login = findViewById(R.id.vpg_login);
    }
}