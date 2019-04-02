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

public class TagsAdapter extends RecyclerView.Adapter<TagHolder> {

    private ArrayList<Tag> items = new ArrayList<>();
    private TagsController.OnTagsCountChange changeCountListener;
    private ThemesView themesView;

    public TagsAdapter(ThemesView themesView) {
        this.themesView = themesView;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_themes_tag, parent, false);
        return new TagHolder(itemView);
    }

    public void setItems(ArrayList<Tag> data){
        items = data;
        notifyDataSetChanged();
    }

    public void setCountListener(TagsController.OnTagsCountChange changeCountListener){
        this.changeCountListener = changeCountListener;
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
       holder.tagName.setText(items.get(position).getName());
       holder.wrapper.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                themesView.showUpdateTagDialog(items.get(position));
           }
       });
       holder.deleteTag.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if( new TagsController().deleteTag(items.get(position))){
                   removeAt(position);
               }
           }
       });
    }

    private void removeAt(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
        if(changeCountListener != null)
            changeCountListener.onTagsCountChanged(items.size());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class TagHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tag_name) TextView tagName;
    @BindView(R.id.btn_delete_tag) ImageView deleteTag;
    @BindView(R.id.tag_themes_wrapper) RelativeLayout wrapper;

    public TagHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}


