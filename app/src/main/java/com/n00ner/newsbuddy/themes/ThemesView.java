package com.n00ner.newsbuddy.themes;

import com.n00ner.newsbuddy.models.SectionTheme;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.models.Theme;

import java.util.ArrayList;

public interface ThemesView {
    void showThemesList();
    void showEmptyList();
    void updateThemesList(ArrayList<SectionTheme> data);
    void showToast(int resId);
    void showAddThemeDialog();
    void showUpdateThemeDialog(Theme theme);
    void showAddTagDialog(Theme theme);
    void showUpdateTagDialog(Tag tag);
    void initList();
}
