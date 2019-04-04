package com.n00ner.newsbuddy.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.filter.FilterView;
import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.storage.AppPreferences;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterTagAdapter extends RecyclerView.Adapter<TagCheckerHolder> {

    private ArrayList<Tag> items;
    private FilterView filterView;

    public FilterTagAdapter(ArrayList<Tag> items, FilterView filterView) {
        this.items = items;
        this.filterView = filterView;
    }

    @NonNull
    @Override
    public TagCheckerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_tag, parent, false);
        return new TagCheckerHolder(itemView);
    }

    public void setItems(ArrayList<Tag> data){
        items = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TagCheckerHolder holder, int position) {
        holder.tagCheck.setChecked(new AppPreferences().isTagContains(items.get(position)));
        holder.tagName.setText(items.get(position).getName());
        holder.tagCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    new AppPreferences().putTag(items.get(position));
                else
                    new AppPreferences().deleteTag(items.get(position));

                if(new AppPreferences().getCountFilterPick() > 0){
                    filterView.showEndAction();
                }else {
                    filterView.hideEndAction();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class TagCheckerHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tag_check_filter) CheckBox tagCheck;
    @BindView(R.id.tag_name) TextView tagName;

    public TagCheckerHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
