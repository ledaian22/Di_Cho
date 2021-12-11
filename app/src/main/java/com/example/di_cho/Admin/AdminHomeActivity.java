package com.example.di_cho.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.di_cho.LoginBackUp;
import com.example.di_cho.MainActivity;
import com.example.di_cho.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Model.Message;

public class AdminHomeActivity extends AppCompatActivity {
    private Button logout,checkOder, editProduct, approveProduct;
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
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sendPermission(Message event){
        permission.equals(event.getMessage());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        logout = findViewById(R.id.btnAdminLogout);
        checkOder = findViewById(R.id.btnCheckOder);
        editProduct = findViewById(R.id.btn_edit_product);
        approveProduct = findViewById(R.id.btn_approve_product);
        EventBus.getDefault().postSticky(new Message("Admin"));


        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminHomeActivity.this, MainActivity.class);
                i.putExtra("Permission","Admin");
                startActivity(i);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, LoginBackUp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminCheckOderActivity.class);
                startActivity(intent);
                finish();
            }
        });

        approveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminApproveProductActivity.class);
                startActivity(intent);
            }
        });
    }
}