package com.n00ner.newsbuddy.feed;

import android.content.Intent;

import com.n00ner.newsbuddy.models.NewsItem;

import java.util.ArrayList;

public interface FeedView {
    void initList();
    void updateList(ArrayList<NewsItem> news);
    void showLoadAnimation();
    void showList();
    void execIntent(Intent intent);
    void openBrowser(String link, String label);
    void showEmptyList(String title, String subtitle, int resIcon);
    void showEmptyList(int resTitle, int resSubtitle, int resIcon);
    void addNews(ArrayList<NewsItem> data, boolean isLast);
}
