package com.example.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Prevalent.Prevalent;
import com.example.ViewHolder.CartViewHolder;
import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Cart;

//Sơn Tùng
public class CartFragment extends Fragment {
 private Toolbar toolbar;
 private RecyclerView recyclerView;
 private TextView cartFee, totalFee, txtMsg;
 private Button btnBuy;
 private static int overTotalPrice = 0;
 private static int shipFee =0 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        CheckOderStatus();
        //Init UI
        recyclerView = v.findViewById(R.id.rv_cart_detail);
        cartFee = v.findViewById(R.id.tv_cart_fee);
        totalFee = v.findViewById(R.id.tv_total_cart_fee);
        btnBuy = v.findViewById(R.id.btn_confirm_buy);
        txtMsg = v.findViewById(R.id.tv_cart_msg);


        //Firebase Init
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User View")
                                .child(Prevalent.currentonlineUser.getPhone())
                                .child("Products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.tvProductName.setText(model.getPname());
                holder.tvProductPrice.setText(model.getPrice());
                holder.tvProductQuantity.setText("x"+model.getQuantity());

                //Calculate Price [Total = overTotalPrice + oneTypeProductPrice]
                int oneProductPrice = Integer.parseInt(model.getPrice());
                int oneProductQuantity = Integer.parseInt(model.getQuantity());
                int oneTypePPrice = oneProductPrice * oneProductQuantity;
                overTotalPrice = overTotalPrice + oneTypePPrice + shipFee;
                cartFee.setText(Integer.valueOf(oneTypePPrice).toString());
                totalFee.setText((Integer.valueOf(overTotalPrice).toString()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence selectOptions[] = new CharSequence[]{
                                "Edit",
                                "Remove"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext() );
                        builder.setTitle("Option");
                        builder.setItems(selectOptions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    Bundle dataHolder = new Bundle();
                                    dataHolder.putString("pid",model.getPid());
                                    ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                                    //Set bundle data to Fragment
                                    productDetailFragment.setArguments(dataHolder);
                                    //Transfer to Fragment
                                    FragmentManager fm = getFragmentManager();
                                    fm.beginTransaction().replace(R.id.frament_container,productDetailFragment).addToBackStack(null)
                                            .commit();
                                } else if ( which == 1){
                                    cartListRef.child("User View")
                                            .child(Prevalent.currentonlineUser.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(getContext(),"Item removed",Toast.LENGTH_SHORT).show();
                                                        FragmentManager fm = getFragmentManager();
                                                        fm.beginTransaction().replace(R.id.frament_container,new HomeFragment()).addToBackStack(null)
                                                                .commit();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item_view,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        LinearLayoutManager botLayout = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(botLayout);
        adapter.startListening();
        recyclerView.setAdapter(adapter);


        //Toolbar Init
        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Đơn hàng");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle totalFeeHolder = new Bundle();
                totalFeeHolder.putString("total", String.valueOf(overTotalPrice));
                ShipmentDetailFragment shipmentDetailFragment = new ShipmentDetailFragment();
                shipmentDetailFragment.setArguments(totalFeeHolder);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,shipmentDetailFragment).addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        overTotalPrice = 0;
        shipFee = 0;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getFragmentManager();
        switch (item.getItemId()){
            case android.R.id.home:
                fm.beginTransaction().replace(R.id.frament_container,new HomeFragment()).addToBackStack(null)
                        .commit();
                break;
            case R.id.app_bar_cart:
                fm.beginTransaction().replace(R.id.frament_container,new CartFragment()).addToBackStack(null)
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CheckOderStatus(){
        DatabaseReference oderRef;
                oderRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                        .getReference().child("Oders").child(Prevalent.currentonlineUser.getPhone());

                oderRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String shippingStatus = snapshot.child("status").getValue().toString();
                            String userName = snapshot.child("name").getValue().toString();
                            if (shippingStatus.equals("Shipped")){

                                totalFee.setText("Oder is shipped");
                                recyclerView.setVisibility(View.GONE);
                                txtMsg.setVisibility(View.VISIBLE);
                                txtMsg.setText("You order is shipping");
                                btnBuy.setVisibility(View.GONE);
                                Toast.makeText(getContext(),"You can make another oder, once shipped",Toast.LENGTH_SHORT).show();
                            } else if (shippingStatus.equals("Not Shipped")){
                                totalFee.setText("Oder is not shipped");
                                recyclerView.setVisibility(View.GONE);
                                txtMsg.setVisibility(View.VISIBLE);
                                btnBuy.setVisibility(View.GONE);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}