package com.example.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Prevalent.Prevalent;
import com.example.di_cho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ShipmentDetailFragment extends Fragment {
    EditText edName, edPhone, edAddress, edCity;
    CheckBox chkConfirm;
    Button btnConfirm;
    String totalAmount ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_shipment_detail, container, false);
        //Get Data from Bundle
        Bundle totalFeeHolder = this.getArguments();
        totalAmount = totalFeeHolder.getString("total");
        Log.d("Price", "onCreateView: " + totalAmount);

        //Init UI
        edName = v.findViewById(R.id.ed_ship_name);
        edPhone = v.findViewById(R.id.ed_ship_phonenumber);
        edAddress = v.findViewById(R.id.ed_ship_address);
        edCity = v.findViewById(R.id.ed_ship_city);
        chkConfirm = v.findViewById(R.id.chk_confirm_address);
        btnConfirm = v.findViewById(R.id.btn_confirm_ship);
        btnConfirm.setVisibility(View.VISIBLE);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShip();
            }
        });
//        if (!chkConfirm.isChecked()){
//            btnConfirm.setVisibility(View.INVISIBLE);
//        }
        return v;
    }

    private void checkShip() {
        if (TextUtils.isEmpty(edName.getText().toString())){
            Toast.makeText(getContext(),"Please write your name",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edAddress.getText().toString())){
            Toast.makeText(getContext(),"Please write your address",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edCity.getText().toString())){
            Toast.makeText(getContext(),"Please write your city",Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edPhone.getText().toString())){
            Toast.makeText(getContext(),"Please write your phone number",Toast.LENGTH_SHORT).show();
        } else {
            ConfirmOder();
        }
    }

    private void ConfirmOder() {

        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference odersRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("Oders").child(Prevalent.currentonlineUser.getPhone());

        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount", totalAmount);
        orderMap.put("name",edName.getText().toString());
        orderMap.put("phone", edPhone.getText().toString());
        orderMap.put("city", edCity.getText().toString());
        orderMap.put("address", edAddress.getText().toString());
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);

        orderMap.put("status","Not Shipped");

        odersRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app")
                            .getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentonlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(),"Oder successfully",Toast.LENGTH_SHORT).show();
                                        FragmentManager fm = getFragmentManager();
                                        fm.beginTransaction().replace(R.id.frament_container,new HomeFragment()).addToBackStack(null)
                                                .commit();
                                    }
                                }
                            });

                }
            }
        });

    }
}