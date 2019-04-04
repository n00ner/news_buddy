package com.n00ner.newsbuddy.feed;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.n00ner.newsbuddy.Constants;
import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.adapters.NewsAdapter;
import com.n00ner.newsbuddy.api.NewsApi;
import com.n00ner.newsbuddy.filter.FilterActivity;
import com.n00ner.newsbuddy.models.NewsItem;
import com.n00ner.newsbuddy.util.BrowserActivity;
import com.n00ner.newsbuddy.util.RSSParser;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class FeedFragment extends Fragment implements FeedView{

    @BindView(R.id.news_list) RecyclerView newsList;
    @BindView(R.id.fab_filter_news) FloatingActionButton fabFilter;
    @BindView(R.id.animation_view) LottieAnimationView animationView;
    @BindView(R.id.empty_list_wrapper) RelativeLayout emptyWrapper;
    @BindView(R.id.empty_list_title) TextView emptyTitle;
    @BindView(R.id.empty_list_subtitle) TextView emptySubtitle;
    @BindView(R.id.img_empty_list_action) ImageView emptyIcon;
    private NewsAdapter adapter;
    private FeedPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FeedPresenter(this);
        adapter.clearData();
        presenter.requestNews();
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), FilterActivity.class), Constants.ACTIVITY_FILTER_PICKED);
            }
        });

        newsList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !fabFilter.isShown())
                    fabFilter.show();
                else if(dy>0 && fabFilter.isShown())
                    fabFilter.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void initList() {
        adapter = new NewsAdapter(this);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList.setAdapter(adapter);
    }

    @Override
    public void updateList(ArrayList<NewsItem> news) {
        adapter.setItems(news);
    }

    @Override
    public void showLoadAnimation() {
        hidePlaceholders();
        animationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList() {
        hidePlaceholders();
        newsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyList(String title, String subtitle, int resIcon) {
        hidePlaceholders();
        emptyWrapper.setVisibility(View.VISIBLE);
        emptyIcon.setImageResource(resIcon);
        emptySubtitle.setText(subtitle);
        emptyTitle.setText(title);
    }

    @Override
    public void showEmptyList(int resTitle, int resSubtitle, int resIcon) {
        hidePlaceholders();
        emptyWrapper.setVisibility(View.VISIBLE);
        emptyIcon.setImageResource(resIcon);
        emptyIcon.setColorFilter(getContext().getResources().getColor(R.color.colorPrimary));
        emptySubtitle.setText(getString(resSubtitle));
        emptyTitle.setText(getString(resTitle));
    }

    @Override
    public void addNews(ArrayList<NewsItem> data, boolean isLast) {
        adapter.addItems(data, isLast);
    }

    private void hidePlaceholders(){
        animationView.setVisibility(View.GONE);
        newsList.setVisibility(View.GONE);
        emptyWrapper.setVisibility(View.GONE);
    }

    @Override
    public void execIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void openBrowser(String link, String label) {
        Intent intent = new Intent(getActivity(), BrowserActivity.class);
        intent.putExtra(Constants.BROWSER_LINK, link);
        intent.putExtra(Constants.BROWSER_LABEL, label);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.ACTIVITY_FILTER_PICKED){
            adapter.clearData();
            presenter.requestNews();
        }
    }
}
