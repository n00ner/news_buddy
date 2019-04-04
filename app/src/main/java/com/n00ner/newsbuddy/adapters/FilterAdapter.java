package com.n00ner.newsbuddy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.filter.FilterView;
import com.n00ner.newsbuddy.models.SectionTheme;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterAdapter extends RecyclerView.Adapter<FilterHolder> {

    private ArrayList<SectionTheme> items = new ArrayList<>();
    private FilterView filterView;

    public FilterAdapter(FilterView filterView) {
        this.filterView = filterView;
    }

    @NonNull
    @Override
    public FilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new FilterHolder(itemView);
    }

    public void setItems(ArrayList<SectionTheme> data){
        items = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FilterHolder holder, int position) {
        holder.header.setText(items.get(position).getTheme().getName());
        holder.tagList.setLayoutManager(new LinearLayoutManager(holder.tagList.getContext()));
        holder.tagList.setAdapter(new FilterTagAdapter(items.get(position).getTagList(), filterView));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class FilterHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.filter_section_header_text) TextView header;
    @BindView(R.id.tags_filter_list) RecyclerView tagList;

    public FilterHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
