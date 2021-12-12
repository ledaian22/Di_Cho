package com.example.di_cho;

// Hoàng Bá Minh thiết kế giao diện
//

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import Model.Message;

public class IntroSlide2 extends AppCompatActivity {
    private Button btnMoveToSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slide2);
        EventBus.getDefault().postSticky(new Message("Seller"));

        btnMoveToSplash = findViewById(R.id.btn_slide2);
        btnMoveToSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroSlide2.this, LoginBackUp.class);
                startActivity(i);
            }
        });
    }
}