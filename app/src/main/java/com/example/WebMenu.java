package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.di_cho.R;
//Sơn Tùng

public class WebMenu extends AppCompatActivity {
WebView web;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_menu);
        web = findViewById(R.id.webView);
        intent=getIntent();
        String link = intent.getStringExtra("link");
        web.loadUrl(link);
        web.setWebViewClient(new WebViewClient());
    }
}