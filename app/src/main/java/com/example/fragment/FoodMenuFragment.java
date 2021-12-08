package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.example.di_cho.AdminEditProductActivity;
import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Products;

//Sơn Tùng

public class FoodMenuFragment extends Fragment {
    private Toolbar toolbar;
    private DatabaseReference ProductsRef;
    private SearchView searchView;
    RecyclerView rvTop, rvBottom;
    private String permission ="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_food_menu, container, false);
        //Get permission
//        Log.d("permission", permission);
        //Search Init
        searchView = v.findViewById(R.id.sv_searchFood);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle searchHolder = new Bundle();
                searchHolder.putString("search-item",query);
                DisplaySearchResult displaySearchResult = new DisplaySearchResult();
                //Set bundle data to Fragment
                displaySearchResult.setArguments(searchHolder);
                //Transfer to Fragment
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,displaySearchResult).addToBackStack(null)
                        .commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Search", "onQueryTextChange: " + newText);
                return false;
            }
        });

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
                                //Check if admin

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

        //Layout for RV
        LinearLayoutManager topLayout = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager botLayout = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);


        //RecyclerView Init
        topAdapter.startListening();
        botAdapter.startListening();
        rvTop = v.findViewById(R.id.rv_top_food);
        rvBottom = v.findViewById(R.id.rv_bottom_food);
        rvTop.setLayoutManager(topLayout);
        rvBottom.setLayoutManager(botLayout);
        rvTop.setAdapter(topAdapter);
        rvBottom.setAdapter(botAdapter);


        //Toolbar Init
        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Đồ ăn");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return v;
    }

    //Inflate ToolBar layout
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Move to CartFragment
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
                Bundle cartBundle = new Bundle();
                cartBundle.putString("permission",permission);
                Log.d("Permission", "" +cartBundle);
                //Create Fragment Object
                CartFragment cartFragment = new CartFragment();
                //Set bundle data to Fragment
                cartFragment.setArguments(cartBundle);
                fm.beginTransaction().replace(R.id.frament_container,cartFragment).addToBackStack(null)
                        .commit();
                break;
        }
      return super.onOptionsItemSelected(item);
    }
}
