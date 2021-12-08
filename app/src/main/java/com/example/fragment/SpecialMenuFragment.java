package com.example.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ViewHolder.ProductViewHolder;
import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Model.Message;
import Model.Products;
//Sơn Tùng

public class SpecialMenuFragment extends Fragment {
    private Toolbar toolbar;
    private DatabaseReference ProductsRef;
    RecyclerView rv_hot_item_special,rv_hotBelowItemSpecial;
    private String fragPermission;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_special_menu, container, false);

        //Get Permission Event Bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Log.d("Fragment Permission", "" + fragPermission);

        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Khuyến mãi");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        //Firebase Init
        ProductsRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Products");

        //Firebase query
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef,Products.class).build();

        //TopAdapter
        FirebaseRecyclerAdapter<Products, ProductViewHolder> topAdapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.tvProductName.setText(model.getPname());
                        Picasso.get().load(model.getImage()).into(holder.imgProduct);
                        //Set OnClick Item
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Passing data to Fragment using Bundle
                                Bundle dataHolder = new Bundle();
                                dataHolder.putString("pid",model.getPid());
                                ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                                //Set bundle data to Fragment
                                productDetailFragment.setArguments(dataHolder);
                                //Transfer to Fragment
                                FragmentManager fm = getFragmentManager();
                                fm.beginTransaction().replace(R.id.frament_container,productDetailFragment).addToBackStack(null)
                                        .commit();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item_view,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        //BottomAdapter
        FirebaseRecyclerAdapter<Products, ProductViewHolder> botAdapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.tvProductName.setText(model.getPname());
                        Picasso.get().load(model.getImage()).into(holder.imgProduct);
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item_view_vertical,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        //Layout for RV
        LinearLayoutManager topLayout = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager botLayout = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        //RecyclerView Init
        topAdapter.startListening();
        botAdapter.startListening();
        rv_hot_item_special = v.findViewById(R.id.rv_hot_item_special);
        rv_hotBelowItemSpecial = v.findViewById(R.id.rv_hotBelowItemSpecial);
        rv_hot_item_special.setLayoutManager(topLayout);
        rv_hotBelowItemSpecial.setLayoutManager(botLayout);
        rv_hot_item_special.setAdapter(topAdapter);
        rv_hotBelowItemSpecial.setAdapter(botAdapter);

        //Toolbar Init
        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Đặc biệt");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        return v;
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

                HomeFragment homeFragment = new HomeFragment();
                fm.beginTransaction().replace(R.id.frament_container,homeFragment).addToBackStack(null)
                        .commit();
                break;
            case R.id.app_bar_cart:

                //Create Fragment Object
                CartFragment cartFragment = new CartFragment();
                //Set bundle data to Fragment
                fm.beginTransaction().replace(R.id.frament_container,cartFragment).addToBackStack(null)
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Event Bus

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onPermission(Message event){
        fragPermission = String.valueOf(event.getMessage());
    }
}