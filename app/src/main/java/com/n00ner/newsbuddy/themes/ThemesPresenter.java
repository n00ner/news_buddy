package com.n00ner.newsbuddy.themes;

import com.n00ner.newsbuddy.R;
import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.models.Theme;
import com.n00ner.newsbuddy.storage.database.TagsController;

import java.util.ArrayList;
import java.util.UUID;

public class ThemesPresenter implements ThemesInteractor.OnThemeChangeListener, ThemesInteractor.OnTagChangedListener{

    private ThemesView themesView;
    private ThemesInteractor themesInteractor;

    ThemesPresenter(ThemesView themesView, ThemesInteractor themesInteractor){
        this.themesInteractor = themesInteractor;
        this.themesView = themesView;
    }

    @Override
    public void onSuccess(ThemesInteractor.ThemeAction action) {
        switch (action){
            case ADD:
                themesView.showToast(R.string.success_theme_add);
                requestThemes();
                break;
            case DELETE:
                themesView.showToast(R.string.success_theme_delete);
                break;
            case UPDATE:
                themesView.showToast(R.string.success_theme_update);
                requestThemes();
                break;
        }
    }

    @Override
    public void onError(ThemesInteractor.ThemeAction action) {
        switch (action){
            case ADD:
                themesView.showToast(R.string.error_theme_add);
                break;
            case DELETE:
                themesView.showToast(R.string.error_theme_delete);
                break;
            case UPDATE:
                themesView.showToast(R.string.error_theme_update);
                break;
        }
    }

    @Override
    public void onSuccess(ArrayList<Theme> data) {
        ArrayList<SectionTheme> sectionThemes = new ArrayList<>();
        for (Theme theme: data) {
            sectionThemes.add(new SectionTheme(theme, new TagsController().fetchTagsByThemeId(theme.getId())));
        }
        themesView.updateThemesList(sectionThemes);
    }

    void requestThemes(){
        themesInteractor.fetchThemes(this);
    }

    public boolean deleteTheme(Theme theme){
       return themesInteractor.deleteTheme(theme, this);
    }

    void onAddThemeClicked(){
        themesView.showAddThemeDialog();
    }

    boolean addTheme(String name){
        if(!name.isEmpty()){
            themesInteractor.addTheme(new Theme(UUID.randomUUID().toString(), name), this);
            return true;
        }
        else{
            themesView.showToast(R.string.warn_empty_add_theme);
            return false;
        }
    }

    boolean addTag(String name, Theme theme){
        if(!name.isEmpty()) {
            themesInteractor.addTag(theme, name, this);
            return true;
        }else {
            themesView.showToast(R.string.warn_empty_add_tag);
            return false;
        }
    }

    boolean updateTheme(Theme theme, String name){
        if(!name.isEmpty()){
            theme.setName(name);
            themesInteractor.updateTheme(theme, this);
            return true;
        }else {
            themesView.showToast(R.string.warn_empty_add_theme);
            return false;
        }
    }

    @Override
    public void onSuccessTag(ThemesInteractor.ThemeAction action) {
        switch (action){
            case ADD:
                themesView.showToast(R.string.success_tag_add);
                requestThemes();
                break;
            case DELETE:
                themesView.showToast(R.string.success_tag_delete);
                break;
            case UPDATE:
                themesView.showToast(R.string.success_tag_update);
                requestThemes();
                break;
        }
    }

    @Override
    public void onErrorTag(ThemesInteractor.ThemeAction action) {
        switch (action){
            case ADD:
                themesView.showToast(R.string.error_tag_add);
                break;
            case DELETE:
                themesView.showToast(R.string.error_tag_delete);
                break;
            case UPDATE:
                themesView.showToast(R.string.error_tag_update);
                break;
        }
    }

    public boolean updateTag(Tag tag, String name) {
        if(!name.isEmpty()){
            tag.setName(name);
            themesInteractor.updateTag(tag, this);
            return true;
        }else {
            themesView.showToast(R.string.warn_empty_add_tag);
            return false;
        }
    }
}
