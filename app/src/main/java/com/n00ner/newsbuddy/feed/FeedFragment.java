package com.n00ner.newsbuddy.feed;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.adapters.NewsAdapter;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    @BindView(R.id.news_list) RecyclerView newsList;

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
        NewsAdapter adapter = new NewsAdapter();
        ArrayList<String> list = new ArrayList<>();
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        list.add("dsfsdf");
        adapter.setItems(list);
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList.setAdapter(adapter);
    }
}
