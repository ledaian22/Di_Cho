package com.example.di_cho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.fragment.CartFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.MenuFragment;
import com.example.fragment.MoreFragment;
import com.example.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //anh xa
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        //Xu ly click item
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frament_container,
                new HomeFragment()).commit();
    }
    //Xu ly bam chon fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_menu:
                    selectedFragment = new MenuFragment();
                    break;
                case R.id.nav_user:
                    selectedFragment = new UserFragment();
                    break;
                case R.id.nav_more:
                    selectedFragment = new MoreFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frament_container,
                    selectedFragment).commit();
            return true;
        }
    };

    public void onBackPressed(){
        if (getFragmentManager().getBackStackEntryCount() > 0 ) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

}