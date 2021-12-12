package com.example.fragment;

import android.content.Intent;
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
import com.example.di_cho.Admin.AdminEditProductActivity;
import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Model.Message;
import Model.Products;


public class SeeMoreFragment extends Fragment {
    RecyclerView recyclerView;
    private Toolbar toolbar;
    private DatabaseReference ProductsRef;
    private String seeMoreQuery ="";
    private String fragPermission;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_see_more, container, false);
        recyclerView = v.findViewById(R.id.rv_seeMoreDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Bundle
        Bundle searchHolder = this.getArguments();
        seeMoreQuery = searchHolder.getString("Category");
        Log.d("See More Item:", "Item from" + seeMoreQuery);

        //Get Permission Event Bus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //Search Process
        ProductsRef = FirebaseDatabase.getInstance("https://login-b73c7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Products");
        //Firebase query
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef.orderByChild("catergory").equalTo(seeMoreQuery),Products.class).build();
        //Adapter
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.tvProductName.setText(model.getPname());
                        Picasso.get().load(model.getImage()).resize(400,400).centerCrop().into(holder.imgProduct);
                        //Set OnClick Item
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Check if admin
                                if (fragPermission.equals("Admin")) {
                                    Intent i = new Intent(getActivity(), AdminEditProductActivity.class);
                                    Bundle pidHolder = new Bundle();
                                    pidHolder.putString("pid", model.getPid());
                                    i.putExtra("pid",model.getPid());
                                    startActivity(i);
                                } else {
                                    //Passing data to Fragment using Bundle
                                    Bundle dataHolder = new Bundle();
                                    dataHolder.putString("pid", model.getPid());
                                    ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                                    //Set bundle data to Fragment
                                    productDetailFragment.setArguments(dataHolder);
                                    //Transfer to Fragment
                                    FragmentManager fm = getFragmentManager();
                                    fm.beginTransaction().replace(R.id.frament_container, productDetailFragment).addToBackStack(null)
                                            .commit();
                                }
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Xem thêm");
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
                fm.beginTransaction().replace(R.id.frament_container,new CartFragment()).addToBackStack(null)
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