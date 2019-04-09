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
import com.n00ner.newsbuddy.util.RSSParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
            sort();
            feedView.showList();
        }
    }

    public void clearData(){
        items.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

       /* holder.share.setOnClickListener(new View.OnClickListener() {
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
        });*/

       /* holder.openLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedView.openBrowser(items.get(position).getLink(), items.get(position).getTitle());
            }
        });*/

       holder.wrapper.setOnClickListener(new View.OnClickListener() {
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

        if(items.get(position).getPubDate() != null){
            holder.pubDate.setText(new SimpleDateFormat("HH:mm - d MMM yyyy").format(new Date(items.get(position).getPubDate())));
        }

        if(items.get(position).getImageUrl() != null){
            holder.preview.setVisibility(View.VISIBLE);
            imageLoader(items.get(position).getImageUrl(), holder.preview);
        }else {
            //holder.preview.setVisibility(View.GONE);
            getPage(items.get(position).getLink())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(String s) {
                            imageLoader(new RSSParser().fetchOGtag(s), holder.preview);
                        }

                        @Override
                        public void onError(Throwable e) {
                            holder.preview.setVisibility(View.GONE);
                        }
                    });
        }
    }

    private void imageLoader(String url, ImageView imageView){
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                Utils.setCornerImage(imageView, imageView.getContext(), 20);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                imageView.setVisibility(View.GONE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                imageView.setImageDrawable(placeHolderDrawable);
            }
        };
        Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).into(target);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void sort(){
        for(int i = 0; i < items.size(); i++){
            for (int j = 0; j < items.size() - i; j++){
                if(j != items.size() - 1)
                if( items.get(j).getPubDateInMillis() <  items.get( j + 1 ).getPubDateInMillis()){
                    Collections.swap(items, j + 1, j);
                }
            }
        }
    }

    private Single<String> getPage(String url){
        return Single.create(emitter -> {
            OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(new Request.Builder().url(url).build()).execute();
                if(response != null)
                    if(response.isSuccessful()){
                        emitter.onSuccess(response.body().string());
                    }else {
                        emitter.onError(new Throwable());
                    }

        });
    }
}

class NewsHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.news_title) TextView title;
    @BindView(R.id.news_description) TextView description;
    @BindView(R.id.img_news_preview) ImageView preview;
    @BindView(R.id.news_pub_source) TextView pubSource;
    @BindView(R.id.news_pub_date) TextView pubDate;
    @BindView(R.id.news_wrapper) CardView wrapper;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
