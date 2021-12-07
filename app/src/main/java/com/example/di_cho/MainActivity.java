package com.example.di_cho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.fragment.HomeFragment;
import com.example.fragment.MenuFragment;
import com.example.fragment.MoreFragment;
import com.example.fragment.ProductDetailFragment;
import com.example.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//Sơn Tùng

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check
        type = getIntent().getExtras().get("Permission").toString();
        Log.d("Permission", type);
        //Bundle
        Bundle bundle = new Bundle();
        bundle.putString("quyen",type);
        Log.d("Bundle value", ""+bundle);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        //anh xa
        //Toolbar Init
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        //Xu ly click item
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frament_container,
                homeFragment).commit();
    }
    //Xu ly bam chon fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            Log.d("Permission Again", type);
            switch (item.getItemId()){
                case R.id.nav_home:
                    Bundle bundle = new Bundle();
                    bundle.putString("permission",type);
                    Log.d("Permission", "" +bundle);
                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    selectedFragment = homeFragment;
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