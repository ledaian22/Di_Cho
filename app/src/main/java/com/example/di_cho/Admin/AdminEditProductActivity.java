package com.example.di_cho.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.di_cho.R;
import com.example.di_cho.Seller.SellerCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminEditProductActivity extends AppCompatActivity {
    private String productID = "";
    private EditText edName, edPrice, edDesc;
    private ImageView imgProduct;
    private Button btnApply, btnDelete;
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_product);
        InitUI();
        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app").getReference()
                .child("Products")
                .child(productID);

        displaySpecificProductInfo();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }

        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisProduct();
            }
        });
    }

    private void deleteThisProduct() {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminEditProductActivity.this, "Product delete successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AdminEditProductActivity.this, SellerCategoryActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void applyChanges() {
        String pName = edName.getText().toString();
        String pPrice = edPrice.getText().toString();
        String pDesc = edDesc.getText().toString();

        if (pName.equals("")) {
            Toast.makeText(this, "Write Name", Toast.LENGTH_SHORT).show();
        } else if (pPrice.equals("")) {
            Toast.makeText(this, "Write Price", Toast.LENGTH_SHORT).show();
        } else if (pDesc.equals("")) {
            Toast.makeText(this, "Write Description", Toast.LENGTH_SHORT).show();
        } else {

            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("desc", pDesc);
            productMap.put("price", pPrice);
            productMap.put("pname", pName);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminEditProductActivity.this, "Change successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AdminEditProductActivity.this, SellerCategoryActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        }

    }

    private void displaySpecificProductInfo() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String pName = snapshot.child("pname").getValue().toString();
                    String pPrice = snapshot.child("price").getValue().toString();
                    String pDesc = snapshot.child("desc").getValue().toString();
                    String pImage = snapshot.child("image").getValue().toString();

                    edName.setText(pName);
                    edPrice.setText(pPrice);
                    edDesc.setText(pDesc);
                    Picasso.get().load(pImage).into(imgProduct);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void InitUI() {
        edName = findViewById(R.id.ed_name_edit);
        edPrice = findViewById(R.id.ed_price_edit);
        edDesc = findViewById(R.id.ed_desc_edit);
        imgProduct = findViewById(R.id.img_edit_product);
        btnApply = findViewById(R.id.btn_apply_change);
        btnDelete = findViewById(R.id.btn_delete_change);
    }
}