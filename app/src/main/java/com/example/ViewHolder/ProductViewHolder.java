package com.example.ViewHolder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Interface.ItemClickListener;
import com.example.di_cho.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvProductName;
    public ImageView imgProduct;
    public ItemClickListener listener;
    public ProductViewHolder(View itemView){
        super(itemView);
        tvProductName = itemView.findViewById(R.id.tv_product);
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
