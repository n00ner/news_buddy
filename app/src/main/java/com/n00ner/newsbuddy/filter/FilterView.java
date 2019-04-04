package com.n00ner.newsbuddy.filter;

import com.n00ner.newsbuddy.models.SectionTheme;

import java.util.ArrayList;



public interface FilterView {
    void initList();
    void updateList(ArrayList<SectionTheme> data);
    void showEndAction();
    void hideEndAction();
    void showEmpty();
    void showList();

}
