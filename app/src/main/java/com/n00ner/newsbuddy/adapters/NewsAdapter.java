package com.n00ner.newsbuddy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.storage.database.TagsController;
import com.n00ner.newsbuddy.themes.ThemesView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private ArrayList<String> items = new ArrayList<>();

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsHolder(itemView);
    }

    public void setItems(ArrayList<String> data){
        items = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

    }

    private void removeAt(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class NewsHolder extends RecyclerView.ViewHolder{

    public NewsHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
