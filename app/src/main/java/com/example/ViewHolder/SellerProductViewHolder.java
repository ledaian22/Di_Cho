package com.example.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Interface.ItemClickListener;
import com.example.di_cho.R;

public class SellerProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvProductName, tvProductPrice, tvProductStatus;
    public ImageView imgProduct;
    public ItemClickListener listener;
    public SellerProductViewHolder(View itemView){
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tv_seller_approve_name);
        imgProduct = itemView.findViewById(R.id.img_seller_product);
        tvProductPrice =itemView.findViewById(R.id.tv_seller_approve_product_price);
        tvProductStatus =itemView.findViewById(R.id.tv_seller_approve_product_status);


    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
    listener.onClick(v,getAdapterPosition(),false);
    }
}
