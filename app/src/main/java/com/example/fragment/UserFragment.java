package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

//import com.example.di_cho.LoginScreen;
import com.example.di_cho.R;
import com.example.di_cho.SplashScreen;
import com.google.firebase.auth.FirebaseAuth;

//Hoàng Bá minh giao diện
//Đại An đăng xuất


public class UserFragment extends Fragment {
    Button btn_dang_xuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_nguoidung, container, false);

        //ánh xạ

        btn_dang_xuat = v.findViewById(R.id.btn_dang_xuat);

        //Xử lý đăng xuất
        btn_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), SplashScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }
}