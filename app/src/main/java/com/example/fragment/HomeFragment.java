package com.example.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.di_cho.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;

//Sơn Tùng

public class HomeFragment extends Fragment {
    private Toolbar toolbar;
    private CardView menuFood, menuDrink, menuDesert, menuSpecial;
    private String permission="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //Get Permission
        permission = this.getArguments().getString("quyen");
        //Init UI
        menuFood = v.findViewById(R.id.menu_doan);
        menuDrink = v.findViewById(R.id.menu_douong);
        menuDesert = v.findViewById(R.id.menu_trangmieng);
        menuSpecial = v.findViewById(R.id.menu_khuyenmai);
        //OnClick CardView
        //Food Menu
        menuFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing Permission
                Bundle bundle = new Bundle();
                bundle.putString("permission",permission);
                Log.d("Permission", "" +bundle);
                //Create Fragment object
                FoodMenuFragment foodMenuFragment = new FoodMenuFragment();
                //Set bundle data to Fragment
                foodMenuFragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,foodMenuFragment).addToBackStack(null)
                        .commit();
            }
        });
        //Drink Menu
        menuDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing Permission
                Bundle bundle = new Bundle();
                bundle.putString("permission",permission);
                Log.d("Permission", "" +bundle);
                //Create Fragment Object
                DrinkMenuFragment drinkMenuFragment = new DrinkMenuFragment();
                //Set bundle data to Fragment
                drinkMenuFragment.setArguments(bundle);
                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,drinkMenuFragment).addToBackStack(null)
                        .commit();
            }
        });
        //Desert Menu
        menuDesert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing Permission
                Bundle bundle = new Bundle();
                bundle.putString("permission",permission);
                Log.d("Permission", "" +bundle);
                //Create Fragment Object
                DesertMenuFragment desertMenuFragment = new DesertMenuFragment();
                //Set bundle data to Fragment
                desertMenuFragment.setArguments(bundle);
                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,desertMenuFragment).addToBackStack(null)
                        .commit();
            }
        });
        //Special Menu
        menuSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing Permission
                Bundle bundle = new Bundle();
                bundle.putString("permission",permission);
                Log.d("Permission", "" +bundle);
                //Create Fragment Object
                SpecialMenuFragment specialMenuFragment = new SpecialMenuFragment();
                //Set bundle data to Fragment
                specialMenuFragment.setArguments(bundle);
                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,specialMenuFragment).addToBackStack(null)
                        .commit();
            }
        });

        //Set Toolbar for Fragment
        toolbar = (Toolbar) v.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
        Bundle bundle = new Bundle();
        bundle.putString("permission",permission);
        Log.d("Permission", "" +bundle);
        //Create Fragment Object
        CartFragment cartFragment = new CartFragment();
        //Set bundle data to Fragment
        cartFragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frament_container,cartFragment).addToBackStack(null)
                .commit();
        return super.onOptionsItemSelected(item);

    }

}