package com.n00ner.newsbuddy.filter;

import com.n00ner.newsbuddy.models.SectionTheme;

import java.util.ArrayList;

public class FilterPresenter implements FilterInteractor.OnFilterChangeListener {

    private FilterView filterView;

    public FilterPresenter(FilterView filterView) {
        this.filterView = filterView;
        this.filterView.initList();
        new FilterInteractor().fetchThemes(this);
    }

    @Override
    public void onSuccess(ArrayList<SectionTheme> data) {
        ArrayList<SectionTheme> clearThemes = new ArrayList<>();
        for (SectionTheme section : data){
            if(section.getTagList().size() > 0){
                clearThemes.add(section);
            }
        }
        if(clearThemes.size() > 0){
            filterView.showList();
        }else {
            filterView.showEmpty();
        }
        filterView.updateList(clearThemes);
    }

    @Override
    public void onError() {
        filterView.showEmpty();
    }
}
