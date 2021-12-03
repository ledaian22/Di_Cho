package com.example.di_cho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Sơn Tùng

public class AdminAddStore extends AppCompatActivity {
Button addFood, addDrink, addDesert, addSpecial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_store);
        addFood = findViewById(R.id.btnAddDoAn);
        addDrink = findViewById(R.id.btnAddDoUong);
        addDesert = findViewById(R.id.btnAddTrangMieng);
        addSpecial = findViewById(R.id.btnAddDacBiet);

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminAddStore.this, AdminAddProduct.class);
                i.putExtra("Category","Food");
                startActivity(i);
            }
        });

        addDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminAddStore.this, AdminAddProduct.class);
                i.putExtra("Category","Drink");
                startActivity(i);
            }
        });

        addDesert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminAddStore.this, AdminAddProduct.class);
                i.putExtra("Category","Desert");
                startActivity(i);
            }
        });

        addSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminAddStore.this, AdminAddProduct.class);
                i.putExtra("Category","Special");
                startActivity(i);
            }
        });
    }
}