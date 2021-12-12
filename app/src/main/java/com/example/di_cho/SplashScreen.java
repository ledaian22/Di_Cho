package com.example.di_cho;
// Hoàng Bá Minh thiết kế giao diện
//

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

import Model.Message;

//Đại An chuyển màn

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        EventBus.getDefault().postSticky(new Message("Guest"));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 2000);


    }

    private void nextActivity() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            //Chưa đăng nhập
            Intent intent = new Intent(this, IntroSlide1.class);
            startActivity(intent);
        } else {
            //Đã đăng nhập
            Intent intent = new Intent(this, LoginBackUp.class);
            startActivity(intent);
        }
        finish();
    }
}