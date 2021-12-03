package com.example.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.Prevalent.Prevalent;
import Model.Users;
import com.example.di_cho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import Model.Products;


public class ProductDetailFragment extends Fragment {
    private Button btnAddToCard;
    private  ImageView imgProduct;
    private TextView  productDesc, detailPrice,productName;
    private ElegantNumberButton numberButton;
    private String productID ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);
        //Init UI
        btnAddToCard = v.findViewById(R.id.btn_add_to_cart);
        detailPrice = v.findViewById(R.id.detail_price);
        imgProduct = v.findViewById(R.id.img_detail);
        productDesc = v.findViewById(R.id.tv_detail_desc);
        numberButton = v.findViewById(R.id.quantity_counter);
        productName = v.findViewById(R.id.tv_detail_pname);
        //Get data from Bundle
        Bundle dataHolder = this.getArguments();
        productID = dataHolder.getString("pid");
        if (dataHolder !=null){
            getProductDetail(productID);
        }
        //Add to Cart
        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingtoCartLst();
            }
        });

        return v;
    }
    //Add to Cart
    private void addingtoCartLst() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price", detailPrice.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());
        cartMap.put("discount", "");

        cartListRef.child("User View").child(Prevalent.currentonlineUser.getPhone())
                .child("Products").child(productID)
        .updateChildren(cartMap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    cartListRef.child("Admin View").child(Prevalent.currentonlineUser.getPhone())
                            .child("Products").child(productID)
                            .updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(),"Added to cart",Toast.LENGTH_SHORT).show();
                                        FragmentManager fm = getFragmentManager();
                                        fm.beginTransaction().replace(R.id.frament_container,new FoodMenuFragment()).addToBackStack(null)
                                                .commit();
                                    }
                                }
                            });
                }
            }
        });


    }

    //Show product detail
    private void getProductDetail(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    detailPrice.setText(products.getPrice());
                    productDesc.setText(products.getDesc());
                    Picasso.get().load(products.getImage()).into(imgProduct);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}