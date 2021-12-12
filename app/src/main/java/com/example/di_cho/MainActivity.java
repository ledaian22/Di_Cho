package com.example.di_cho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.Prevalent.Prevalent;
import com.example.fragment.HomeFragment;
import com.example.fragment.MenuFragment;
import com.example.fragment.MoreFragment;
import com.example.fragment.UserFragment;
import com.github.kimkevin.cachepot.CachePot;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Model.Message;

//Sơn Tùng

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private  String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(MainActivity.this,"Welcome " + Prevalent.currentonlineUser.getName(),Toast.LENGTH_SHORT).show();
        //Event Bus

        //Check
//        type = getIntent().getExtras().getString("Permission");
        Log.d("Activity Permission", ""+type);
        //Bundle
        //anh xa
        //Toolbar Init
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
                    HomeFragment homeFragment = new HomeFragment();
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

    //Register Event Bus

    @Override
    public void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe (threadMode = ThreadMode.MAIN, sticky = true)
    public void onPermissionEvent(Message event){
        type= String.valueOf(event.getMessage())   ;
        Log.d("Type", type);
    }

}