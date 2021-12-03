package com.example.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Interface.ItemClickListener;
import com.example.di_cho.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvProductName, tvProductPrice, tvProductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tv_cart_item_name);
        tvProductPrice = itemView.findViewById(R.id.tv_cart_item_price);
        tvProductQuantity = itemView.findViewById(R.id.tv_cart_item_quantity);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
