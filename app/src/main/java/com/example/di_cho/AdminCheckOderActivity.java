package com.example.di_cho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.AdminOders;

public class AdminCheckOderActivity extends AppCompatActivity {
    private RecyclerView odersList;
    private DatabaseReference oderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_oder);

        oderRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Oders");

        odersList = findViewById(R.id.rv_oder_list);
        odersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOders> options =
                new FirebaseRecyclerOptions.Builder<AdminOders>()
                .setQuery(oderRef,AdminOders.class)
                .build();
        FirebaseRecyclerAdapter<AdminOders,AdminOderViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOders, AdminOderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOderViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull AdminOders model) {
                holder.userName.setText("Name: " + model.getName());
                holder.userPhone.setText("Phone: " + model.getPhone());
                holder.userTotalPrice.setText("Total Ammunt: " + model.getTotalAmount());
                holder.userDateTime.setText("Order at: " + model.getDate() + "/" + model.getTime());
                holder.userShipAddress.setText("Address: " + model.getAddress() + "/" + model.getCity());

                holder.btnShowProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uID = getRef(position).getKey();
                        Intent intent = new Intent(AdminCheckOderActivity.this,AdminUserProduct.class);
                        intent.putExtra("uid", uID);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]= new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckOderActivity.this);
                        builder.setTitle("Have order already shipped");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0){
                                    String uID = getRef(position).getKey();
                                    RemoveOrder(uID);
                                } else {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });


            }

            @NonNull
            @Override
            public AdminOderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oder_layout,parent,false);
                return new AdminOderViewHolder(view);
            }
        };
        odersList.setAdapter(adapter);
        adapter.startListening();
    }

    private void RemoveOrder(String uID) {
        oderRef.child(uID).removeValue();
    }

    public static class AdminOderViewHolder extends RecyclerView.ViewHolder{
        public TextView userName,userPhone,userTotalPrice,userDateTime,userShipAddress;
        public Button btnShowProduct;
        public AdminOderViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_oder_item_name);
            userPhone = itemView.findViewById(R.id.tv_oder_item_phone);
            userTotalPrice = itemView.findViewById(R.id.tv_oder_item_totalprice);
            userDateTime = itemView.findViewById(R.id.tv_oder_item_date_time);
            userShipAddress = itemView.findViewById(R.id.tv_oder_item_address);
            btnShowProduct = itemView.findViewById(R.id.btn_show_all_product);

        }
    }
}