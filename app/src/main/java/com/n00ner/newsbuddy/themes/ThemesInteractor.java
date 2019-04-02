package com.n00ner.newsbuddy.themes;

import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.models.Theme;
import com.n00ner.newsbuddy.storage.database.TagsController;
import com.n00ner.newsbuddy.storage.database.ThemesController;

import java.util.ArrayList;
import java.util.UUID;

public class ThemesInteractor {

    private ThemesController themesController;
    private TagsController tagsController;

     ThemesInteractor(){
            tagsController = new TagsController();
         themesController = new ThemesController();
    }

    public interface OnThemeChangeListener{
        void onSuccess(ThemeAction action);
        void onError(ThemeAction action);
        void onSuccess(ArrayList<Theme> data);
    }

    public interface OnTagChangedListener{
         void onSuccessTag(ThemeAction action);
         void onErrorTag(ThemeAction action);
    }

    public enum ThemeAction{
        ADD,
        DELETE,
        UPDATE,
    }

    void addTag(Theme theme, String name, ThemesInteractor.OnTagChangedListener listener){
        if( tagsController.addTag(new Tag(UUID.randomUUID().toString(), name, theme.getId())) ){
            listener.onSuccessTag(ThemeAction.ADD);
        }else {
            listener.onErrorTag(ThemeAction.ADD);
        }
    }

    void addTheme(Theme theme, OnThemeChangeListener listener){
        if(themesController.addTheme(theme)){
            listener.onSuccess(ThemeAction.ADD);
        }else {
            listener.onError(ThemeAction.ADD);
        }
    }

    void updateTheme(Theme theme, OnThemeChangeListener listener){
        if(themesController.updateTheme(theme)){
            listener.onSuccess(ThemeAction.UPDATE);
        }else {
            listener.onError(ThemeAction.UPDATE);
        }
    }

    void updateTag(Tag tag, OnTagChangedListener listener){
         if(tagsController.updateTag(tag)){
             listener.onSuccessTag(ThemeAction.UPDATE);
         }else {
             listener.onErrorTag(ThemeAction.UPDATE);
         }
    }

     boolean deleteTheme(Theme theme, OnThemeChangeListener listener){
        if(themesController.deleteTheme(theme)){
            listener.onSuccess(ThemeAction.DELETE);
            return true;
        }else {
            listener.onError(ThemeAction.DELETE);
            return false;
        }
    }

    void fetchThemes(OnThemeChangeListener listener){
        listener.onSuccess(themesController.fetchThemes());
    }


}
