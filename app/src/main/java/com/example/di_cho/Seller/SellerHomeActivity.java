package com.example.di_cho.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ViewHolder.ProductApprovalViewHolder;
import com.example.ViewHolder.SellerProductViewHolder;
import com.example.di_cho.Admin.AdminApproveProductActivity;
import com.example.di_cho.LoginBackUp;
import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Products;

public class SellerHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unVerifiedProducts;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_add:
                    Intent i2 = new Intent(SellerHomeActivity.this, SellerCategoryActivity.class);
                    startActivity(i2);
                    return true;
                case R.id.navigation_logout:
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(SellerHomeActivity.this, LoginBackUp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView bottomNav = findViewById(R.id.nav_seller_view);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        unVerifiedProducts = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference()
                .child("Products");

        recyclerView = findViewById(R.id.rv_seller_product_check);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unVerifiedProducts.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, SellerProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, SellerProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull SellerProductViewHolder holder, int position, @NonNull Products model) {
                        holder.tvProductName.setText(model.getPname());
                        holder.tvProductPrice.setText(model.getPrice());
                        holder.tvProductStatus.setText(model.getProductStatus());

                        if (holder.tvProductStatus.getText().equals("Approved")){
                            holder.tvProductStatus.setTextColor(Color.GREEN);
                        } else {
                            holder.tvProductStatus.setTextColor(Color.RED);

                        }

                        Picasso.get().load(model.getImage()).into(holder.imgProduct);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID = model.getPid();

                                CharSequence option[] = new CharSequence[]{
                                        "Yes",
                                        "No"
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                                builder.setTitle("Do you want to delete this product");
                                builder.setItems(option, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which ==0){
                                                deleteProduct(productID);
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
                    public SellerProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view,parent,false);
                        SellerProductViewHolder holder = new SellerProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteProduct(String productID) {
        unVerifiedProducts.child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SellerHomeActivity.this,"Product is deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}