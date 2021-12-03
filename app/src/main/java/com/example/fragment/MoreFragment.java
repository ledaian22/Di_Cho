package com.example.fragment;
//Hoàng Bá Minh thiết kế giao diện
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.di_cho.LoginScreen;
import com.example.di_cho.R;
import com.google.firebase.auth.FirebaseAuth;


public class MoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, container, false);
        return v;
    }
}