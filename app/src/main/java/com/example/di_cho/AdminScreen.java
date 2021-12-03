package com.example.di_cho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminScreen extends AppCompatActivity {
    Button btnCuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        anhXa();
        cuaHang();
    }

    private void cuaHang() {
        btnCuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminScreen.this,AdminAddStore.class);
                startActivity(intent);
            }
        });
    }


    private void anhXa() {
    btnCuaHang = findViewById(R.id.btnCuaHang);
    }
}