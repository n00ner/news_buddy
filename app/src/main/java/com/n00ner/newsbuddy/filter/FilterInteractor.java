package com.n00ner.newsbuddy.filter;

import com.n00ner.newsbuddy.models.NewsItem;
import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.models.Theme;
import com.n00ner.newsbuddy.storage.database.TagsController;
import com.n00ner.newsbuddy.storage.database.ThemesController;

import java.util.ArrayList;

public class FilterInteractor {

    private ThemesController themesController;
    private TagsController tagsController;

    public FilterInteractor() {
        themesController = new ThemesController();
        tagsController = new TagsController();
    }

    public interface OnFilterChangeListener{
        void onSuccess(ArrayList<SectionTheme> data);
        void onError();
    }

    public interface OnFeedChanges{
        void onSuccessFeed(ArrayList<NewsItem> data);
        void onErrorFeed(String reason);
        void onUpdateFeed(ArrayList<NewsItem> data);
    }

    public void fetchThemes(OnFilterChangeListener listener){
       ArrayList<Theme> themes = themesController.fetchThemes();
       ArrayList<SectionTheme> sectionThemes = new ArrayList<>();

       for (Theme theme : themes){
           sectionThemes.add(new SectionTheme(theme, tagsController.fetchTagsByThemeId(theme.getId())));
       }

       listener.onSuccess(sectionThemes);
    }
}
