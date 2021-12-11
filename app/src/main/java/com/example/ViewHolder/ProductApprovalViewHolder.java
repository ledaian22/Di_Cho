package com.example.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Interface.ItemClickListener;
import com.example.di_cho.R;

public class ProductApprovalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvProductName, tvProductPrice;
    public ImageView imgProduct;
    public ItemClickListener listener;
    public ProductApprovalViewHolder(View itemView){
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tv_approve_name);
        tvProductPrice = itemView.findViewById(R.id.tv_approve_product_price);
        imgProduct = itemView.findViewById(R.id.img_product);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
    listener.onClick(v,getAdapterPosition(),false);
    }
}
