package com.example.di_cho.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Interface.ItemClickListener;
import com.example.ViewHolder.ProductApprovalViewHolder;
import com.example.ViewHolder.ProductViewHolder;
import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Products;

public class AdminApproveProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unVerifiedProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approve_product);

        unVerifiedProducts = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference()
                .child("Products");

        recyclerView = findViewById(R.id.rv_approval_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unVerifiedProducts.orderByChild("productStatus").equalTo("Not Approved"),Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductApprovalViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductApprovalViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductApprovalViewHolder holder, int position, @NonNull Products model) {
                holder.tvProductName.setText(model.getPname());
                holder.tvProductPrice.setText(model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imgProduct);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String productID = model.getPid();

                        CharSequence option[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminApproveProductActivity.this);
                        builder.setTitle("Do you want to approve this product");
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which ==0){
                                    ChangeStatus(productID);
                                }
                                if (which ==1){

                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public ProductApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_approval_list_item_view,parent,false);
                ProductApprovalViewHolder holder = new ProductApprovalViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void ChangeStatus(String productID) {
        unVerifiedProducts.child(productID).child("productStatus").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminApproveProductActivity.this,"Product is Approved",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}