package com.example.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.RSS.ParseHTML.MyAdapter;
import com.example.di_cho.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import Model.ParseItem;

//Sơn Tùng

public class MenuFragment extends Fragment implements MyAdapter.OnMenuListener {
    final static String urlAddress="https://www.24h.com.vn/upload/rss/amthuc.rss";
    RecyclerView rv;
    MyAdapter adapter;
    ParseItem parseItem;
    ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_menu, container, false);

        progressBar = v.findViewById(R.id.progressbar);
        rv = v.findViewById(R.id.menu_rv);
        Content content = new Content();
        content.execute();
        rv.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        adapter = new MyAdapter(parseItems,this.getContext(),this);

        rv.setAdapter(adapter);
        return v;
    }

    @Override
    public void onMenuClick(int position) {
//        parseItems.get(position);
//        Intent intent = new Intent(this.getContext(), WebMenu.class);
//        intent.putExtra("link",parseItem.getLink());
//        startActivity(intent);
    }

    private  class Content extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url ="https://eva.vn/rss/rss.php/162";
                Document doc = Jsoup.connect(url).get();
                Elements data = doc.select("item");
                Log.d("items","img:"+ data);
                int size = data.size();
                for ( int i =0; i<size;i++){
                    String link = data.select("link").eq(i).text();
                    String imgUrl = data.select("item").select("img").eq(i).attr("src");
                    String title = data.select("title").eq(i).text();
                    parseItems.add(new ParseItem(title,imgUrl,link));
                    Log.d("items","img:"+ imgUrl + ", title:" + title + ",link: " +link );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}