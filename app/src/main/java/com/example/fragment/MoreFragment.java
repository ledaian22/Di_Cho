package com.example.fragment;

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
    Button btndangXuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, container, false);

        //ánh xạ

        btndangXuat = v.findViewById(R.id.btndangXuat);

        //Xử lý đăng xuất
        btndangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }
}