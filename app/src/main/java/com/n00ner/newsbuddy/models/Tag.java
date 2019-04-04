package com.n00ner.newsbuddy.models;

import androidx.annotation.Nullable;

public class Tag {
    private String id;
    private String name;
    private String themeId;

    public Tag(String id, String name, String themeId) {
        this.id = id;
        this.name = name;
        this.themeId = themeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }

        if(!(obj instanceof Tag)){
            return false;
        }

        Tag o = (Tag)obj;

        if(this.getThemeId().equals(o.getThemeId()) && this.id.equals(o.id) && this.name.equals(o.name)){
            return true;
        }else {
            return false;
        }
    }
}
