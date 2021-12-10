package com.example.di_cho.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.di_cho.LoginBackUp;
import com.example.di_cho.MainActivity;
import com.example.di_cho.R;
import com.github.kimkevin.cachepot.CachePot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Model.Message;

//Sơn Tùng

public class AdminControlPanel extends AppCompatActivity {
Button addFood, addDrink, addDesert, addSpecial, logout,checkOder, editProduct;
private String permission ="Admin";
    //Register Event Bus
    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void sendPermission(Message event){
        permission.equals(event.getMessage());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().postSticky(new Message("Admin"));
        CachePot.getInstance().push(permission);
        Log.d("Permission", permission);
        setContentView(R.layout.activity_admin_add_store);
        addFood = findViewById(R.id.btnAddDoAn);
        addDrink = findViewById(R.id.btnAddDoUong);
        addDesert = findViewById(R.id.btnAddTrangMieng);
        addSpecial = findViewById(R.id.btnAddDacBiet);
        logout = findViewById(R.id.btnAdminLogout);
        checkOder = findViewById(R.id.btnCheckOder);
        editProduct = findViewById(R.id.btn_edit_product);

        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminControlPanel.this, MainActivity.class);
                i.putExtra("Permission","Admin");
                startActivity(i);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlPanel.this, LoginBackUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControlPanel.this,AdminCheckOderActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminControlPanel.this, AdminAddProduct.class);
                i.putExtra("Category","Food");
                startActivity(i);
            }
        });

        addDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminControlPanel.this, AdminAddProduct.class);
                i.putExtra("Category","Drink");
                startActivity(i);
            }
        });

        addDesert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminControlPanel.this, AdminAddProduct.class);
                i.putExtra("Category","Desert");
                startActivity(i);
            }
        });

        addSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminControlPanel.this, AdminAddProduct.class);
                i.putExtra("Category","Special");
                startActivity(i);
            }
        });
    }


}