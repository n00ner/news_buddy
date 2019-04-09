package com.n00ner.newsbuddy.feed;

import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.models.NewsItem;
import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.models.Theme;
import com.n00ner.newsbuddy.storage.AppPreferences;
import com.n00ner.newsbuddy.storage.database.TagsController;
import com.n00ner.newsbuddy.storage.database.ThemesController;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedPresenter implements FeedInteractor.OnFeedListener {

    private FeedView feedView;
    private FeedInteractor feedInteractor;

    public FeedPresenter(FeedView feedView) {
        this.feedView = feedView;
        this.feedView.initList();
        feedInteractor = new FeedInteractor();
    }

    @Override
    public void onSuccessFeed(ArrayList<NewsItem> data, boolean isLast) {
        feedView.addNews(data, isLast);
    }

    @Override
    public void onError(String error) {
        feedView.showEmptyList(R.string.error_network, R.string.error_network_sub, R.drawable.ic_portable_wifi_off);
    }

    private ArrayList<String> buildQueryThemes(ArrayList<String> tags){
        ArrayList<String> themesQuery = new ArrayList<>();
        HashMap<String, String> allThemes = new ThemesController().getMappedThemes();
        for(String tag : tags){
            String theme = allThemes.get(tag);
            if(theme != null && !themesQuery.contains(theme))
                themesQuery.add(theme);
        }
        return themesQuery;
    }

    public void requestNews(){
        ArrayList<String> pickedThemes = buildQueryThemes(new  AppPreferences().getPickedTagList());
        ArrayList<String> allThemes = new ArrayList<>();
        for(Theme theme: new ThemesController().fetchThemes()){
            allThemes.add(theme.getName());
        }
        if(pickedThemes.size() == 0){
            pickedThemes = allThemes;
        }
        if(pickedThemes.size() == 0){
            feedView.showEmptyList("НЕТ ТЭГОВ", "Выберите хотя бы один тэг", R.drawable.ic_local_offer);
            return;
        }
        feedView.showLoadAnimation();
        for(int i = 0; i < pickedThemes.size(); i++){
            feedInteractor.requestNewsByTheme(pickedThemes.get(i), this, pickedThemes.size() - 1 == i);
        }
    }
}
