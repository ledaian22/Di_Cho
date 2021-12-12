package com.example.di_cho;

// Hoàng Bá Minh thiết kế giao diện

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import Model.Message;

public class IntroSlide1 extends AppCompatActivity {
    Button btn_slide1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slide1);
        EventBus.getDefault().postSticky(new Message("Seller"));

        if (!PrefManager.getInstance(this).isFirstTimeLaunch()){
            Intent intent = new Intent(IntroSlide1.this,SplashScreen.class);
            startActivity(intent);
        }

        btn_slide1 = (Button) findViewById(R.id.btn_slide1);
        btn_slide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent slide2 = new Intent(IntroSlide1.this, IntroSlide2.class);
                startActivity(slide2);
            }
        });
    }
}