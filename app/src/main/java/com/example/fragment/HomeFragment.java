package com.example.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.di_cho.R;

//Sơn Tùng

public class HomeFragment extends Fragment {
    private Toolbar toolbar;
    private CardView menuFood, menuDrink, menuDesert, menuSpecial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
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
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,new FoodMenuFragment()).addToBackStack(null)
                        .commit();
            }
        });
        //Drink Menu
        menuDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,new DrinkMenuFragment()).addToBackStack(null)
                        .commit();
            }
        });
        //Desert Menu
        menuDesert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,new DesertMenuFragment()).addToBackStack(null)
                        .commit();
            }
        });
        //Special Menu
        menuSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.frament_container,new SpecialMenuFragment()).addToBackStack(null)
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
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frament_container,new CartFragment()).addToBackStack(null)
                .commit();
        return super.onOptionsItemSelected(item);

    }

}