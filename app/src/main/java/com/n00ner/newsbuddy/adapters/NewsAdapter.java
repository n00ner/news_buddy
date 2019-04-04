package com.n00ner.newsbuddy.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.n00ner.newsbuddy.BaseApp;
import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.Utils;
import com.n00ner.newsbuddy.feed.FeedView;
import com.n00ner.newsbuddy.models.NewsItem;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.storage.database.TagsController;
import com.n00ner.newsbuddy.themes.ThemesView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {

    private ArrayList<NewsItem> items = new ArrayList<>();
    private FeedView feedView;

    public NewsAdapter(FeedView feedView) {
        this.feedView = feedView;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsHolder(itemView);
    }

    public void setItems(ArrayList<NewsItem> data){
        items = data;
        notifyDataSetChanged();
        if(items.size() == 0){
            feedView.showEmptyList(R.string.empty_news, R.string.empty_news_sub, R.drawable.ic_receipt);
        }
    }

    public void addItems(ArrayList<NewsItem> data, boolean isLast){
        items.addAll(data);
        if(isLast){
            notifyDataSetChanged();
        }
        if(items.size() == 0){
            feedView.showEmptyList(R.string.empty_news, R.string.empty_news_sub, R.drawable.ic_receipt);
        }else {
            feedView.showList();
        }
    }

    public void clearData(){
        items.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, items.get(position).getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(items.get(position).getDescription()).toString().replaceAll("\\s+"," ").trim()
                        + holder.share.getContext().getString(R.string.share_more_link, items.get(position).getLink())
                        + holder.share.getContext().getString(R.string.shared_from, BaseApp.getAppContext().getString(R.string.app_name)));
                feedView.execIntent(Intent.createChooser(sharingIntent, holder.share.getContext().getString(R.string.share_via)));
            }
        });

        holder.openLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedView.openBrowser(items.get(position).getLink(), items.get(position).getTitle());
            }
        });

        if(items.get(position).getTitle() != null && items.get(position).getDescription() != null){
            holder.title.setText(items.get(position).getTitle());
            holder.description.setText(Html.fromHtml(items.get(position).getDescription()).toString().replaceAll("\\s+"," ").trim());
        }
        if(items.get(position).getPubSource() != null){
            holder.pubSource.setText(items.get(position).getPubSource());
            holder.pubSource.setVisibility(View.VISIBLE);
        }else {
            holder.pubSource.setVisibility(View.GONE);
        }

        if(items.get(position).getImageUrl() != null){
            holder.preview.setVisibility(View.VISIBLE);
            Target target = new Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.preview.setImageBitmap(bitmap);
                    Utils.setCornerImage(holder.preview, holder.preview.getContext(), 20);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    holder.preview.setVisibility(View.GONE);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    holder.preview.setImageDrawable(placeHolderDrawable);
                }
            };
            Picasso.get().load(items.get(position).getImageUrl()).placeholder(R.mipmap.ic_launcher).into(target);
        }else {
            holder.preview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class NewsHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.news_title) TextView title;
    @BindView(R.id.news_description) TextView description;
    @BindView(R.id.img_news_preview) ImageView preview;
    @BindView(R.id.news_pub_source) TextView pubSource;
    @BindView(R.id.news_open_browser) ImageView openLink;
    @BindView(R.id.news_bookmark) ImageView addBookmark;
    @BindView(R.id.news_share) ImageView share;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
