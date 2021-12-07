package com.example.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ViewHolder.ProductViewHolder;
import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Products;


public class DisplaySearchResult extends Fragment {
    RecyclerView recyclerView;
    private Toolbar toolbar;
    private String searchQuery ="";
    private String permission="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display_search_result, container, false);
        recyclerView = v.findViewById(R.id.rv_searchdisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Get permission
        permission=this.getArguments().getString("permission");
        //Bundle
        Bundle searchHolder = this.getArguments();
        searchQuery = searchHolder.getString("search-item");
        Log.d("search", "onCreateView: " +searchQuery);
        //Search Process
        DatabaseReference reference = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Products");
        FirebaseRecyclerOptions<Products> searchOption = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("pname").startAt(searchQuery),Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(searchOption) {
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item_view_vertical,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        //Toolbar Init
        toolbar = (androidx.appcompat.widget.Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tìm kiếm");
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
                Bundle bundle = new Bundle();
                bundle.putString("quyen",permission);
                Log.d("Bundle value", ""+bundle);
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.frament_container,homeFragment).addToBackStack(null)
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