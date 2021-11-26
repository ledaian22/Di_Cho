package com.example.RSS.ParseHTML;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WebMenu;
import com.example.di_cho.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.ParseItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NewsHolder> {
    private ArrayList<ParseItem> parseItems;
    private Context context;
    private OnMenuListener onMenuListener;
    public MyAdapter(ArrayList<ParseItem> parseItems, Context context, OnMenuListener onMenuListener){
        this.parseItems = parseItems;
        this.context = context;
        this.onMenuListener = onMenuListener;
    }





    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item_view,parent,false);

        return new NewsHolder(view,onMenuListener);
    }


    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, @SuppressLint("RecyclerView") int position) {
        ParseItem parseItem = parseItems.get(position);
        holder.title.setText(parseItem.getTitle());
        Picasso.get().load(parseItem.getImgUrl()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseItem parseItem = parseItems.get(position);
                Intent intent = new Intent(context, WebMenu.class);
                intent.putExtra("link",parseItem.getLink());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title;
        ImageView img;
        ParseItem parseItem;
        OnMenuListener onMenuListener;
        public NewsHolder(@NonNull View itemView, OnMenuListener onMenuListener) {
            super(itemView);
            title = itemView.findViewById(R.id.rss_title);
            img = itemView.findViewById(R.id.rss_image);
            this.onMenuListener = onMenuListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMenuListener.onMenuClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
    public interface OnMenuListener{
        void onMenuClick(int position);
    }
}
