package com.example.fragment;
//Hoàng Bá Minh thiết kế giao diện
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.di_cho.InfoappScreen;
import com.example.di_cho.LoginScreen;
import com.example.di_cho.R;
import com.example.di_cho.SellScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

//Đại An chuyển màn

public class MoreFragment extends Fragment {
    TextView txt_thong_tin_ud,txt_nhan_tin,txt_thong_bao,txt_don_hang,txt_ban_hang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, container, false);

        //ánh xạ
        txt_thong_tin_ud = v.findViewById(R.id.txt_thong_tin_ud);
        txt_ban_hang = v.findViewById(R.id.txt_ban_hang);

        //Xử lý chuyển màn
        txt_thong_tin_ud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoappScreen.class);
                startActivity(intent);
            }
        });

        txt_ban_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SellScreen.class);
                startActivity(intent);
            }
        });
        return v;
    }
}