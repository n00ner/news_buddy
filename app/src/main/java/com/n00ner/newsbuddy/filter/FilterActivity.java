package com.n00ner.newsbuddy.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.adapters.FilterAdapter;
import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.storage.AppPreferences;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity implements  FilterView {

    @BindView(R.id.filter_list) RecyclerView filterList;
    @BindView(R.id.fab_end_filter_action) FloatingActionButton fabEndFilterAction;
    @BindView(R.id.empty_list_wrapper) RelativeLayout emptyPlaceholder;
    @BindView(R.id.empty_list_title) TextView emptyTitle;
    @BindView(R.id.empty_list_subtitle) TextView emptySubtitle;
    @BindView(R.id.img_empty_list_action) ImageView emptyIcon;

    private FilterAdapter adapter;
    private FilterPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        adapter = new FilterAdapter(this);
        presenter = new FilterPresenter(this);
        filterList.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy<0 && !fabEndFilterAction.isShown())
                    if(new AppPreferences().getCountFilterPick() > 0){
                        showEndAction();
                    }else {
                        hideEndAction();
                    }
                else if(dy>0 && fabEndFilterAction.isShown())
                    fabEndFilterAction.setVisibility(View.GONE);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        fabEndFilterAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(new AppPreferences().getCountFilterPick() > 0){
           showEndAction();
        }else {
            hideEndAction();
        }
    }

    @Override
    public void initList() {
        filterList.setLayoutManager(new LinearLayoutManager(this));
        filterList.setAdapter(adapter);
    }

    @Override
    public void updateList(ArrayList<SectionTheme> data) {
        adapter.setItems(data);
    }

    @Override
    public void showEndAction() {
        fabEndFilterAction.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEndAction() {
        fabEndFilterAction.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        emptyPlaceholder.setVisibility(View.VISIBLE);
        filterList.setVisibility(View.GONE);
        emptyTitle.setText(R.string.empty_tag_filter);
        emptySubtitle.setText(R.string.empty_tag_filter_sub);
        emptyIcon.setImageResource(R.drawable.ic_local_offer);
    }

    @Override
    public void showList() {
        emptyPlaceholder.setVisibility(View.GONE);
        filterList.setVisibility(View.VISIBLE);
    }
}
