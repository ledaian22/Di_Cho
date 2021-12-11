package com.example.di_cho.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.di_cho.R;
import com.github.kimkevin.cachepot.CachePot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Model.Message;

//Sơn Tùng

public class SellerCategoryActivity extends AppCompatActivity {
Button addFood, addDrink, addDesert, addSpecial;// logout,checkOder, editProduct;
private String permission ="Seller";
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
//       EventBus.getDefault().postSticky(new Message("Admin"));
        CachePot.getInstance().push(permission);
        Log.d("Permission", permission);
        setContentView(R.layout.activity_seller_category);
        addFood = findViewById(R.id.btnAddDoAn);
        addDrink = findViewById(R.id.btnAddDoUong);
        addDesert = findViewById(R.id.btnAddTrangMieng);
        addSpecial = findViewById(R.id.btnAddDacBiet);
/*        */

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerCategoryActivity.this, SellerAddNewProduct.class);
                i.putExtra("Category","Food");
                startActivity(i);
            }
        });

        addDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerCategoryActivity.this, SellerAddNewProduct.class);
                i.putExtra("Category","Drink");
                startActivity(i);
            }
        });

        addDesert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerCategoryActivity.this, SellerAddNewProduct.class);
                i.putExtra("Category","Desert");
                startActivity(i);
            }
        });

        addSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerCategoryActivity.this, SellerAddNewProduct.class);
                i.putExtra("Category","Special");
                startActivity(i);
            }
        });
    }


}