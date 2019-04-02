package com.n00ner.newsbuddy.models;

import java.util.ArrayList;

public class SectionTheme {
    private Theme theme;
    private ArrayList<Tag> tagList;

    public SectionTheme(Theme theme, ArrayList<Tag> tagList) {
        this.theme = theme;
        this.tagList = tagList;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ArrayList<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }
}
