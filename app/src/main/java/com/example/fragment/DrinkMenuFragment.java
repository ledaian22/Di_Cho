package com.example.fragment;

import android.os.Bundle;
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

import Model.Products;

//Sơn Tùng

public class DrinkMenuFragment extends Fragment {
    private Toolbar toolbar;
    private DatabaseReference ProductsRef;
    RecyclerView rv_hot_item_drink,rv_hotBelowItemDrink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drink_menu, container, false);

        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Đồ uống");
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
        rv_hot_item_drink = v.findViewById(R.id.rv_hot_item_drink);
        rv_hotBelowItemDrink = v.findViewById(R.id.rv_hotBelowItemDrink);
        rv_hot_item_drink.setLayoutManager(topLayout);
        rv_hotBelowItemDrink.setLayoutManager(botLayout);
        rv_hot_item_drink.setAdapter(topAdapter);
        rv_hotBelowItemDrink.setAdapter(botAdapter);

        //Toolbar Init
        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Đồ uống");
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
}