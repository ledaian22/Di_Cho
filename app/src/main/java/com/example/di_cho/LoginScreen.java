package com.example.di_cho;
    // Hoàng Bá Minh thiết kế giao diện
    //

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.Adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class LoginScreen extends AppCompatActivity {
    private TabLayout tbl_login;
    private ViewPager vpg_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        tbl_login = findViewById(R.id.tbl_login);
        vpg_login = findViewById(R.id.vpg_login);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpg_login.setAdapter(viewPagerAdapter);

        tbl_login.setupWithViewPager(vpg_login);
    }
}