package com.n00ner.newsbuddy.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.n00ner.newsbuddy.BaseApp;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.models.Theme;
import com.n00ner.newsbuddy.storage.database.ThemesController;

import java.util.ArrayList;

public class AppPreferences {
    private SharedPreferences preferences;

    private static String APP_PREFERENCES;
    private static final String FILTER_PICK = "filter_pick";


    public AppPreferences() {
        this.preferences = BaseApp.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_PREFERENCES = BaseApp.getAppContext().getPackageName();
    }

    private ArrayList<Tag> getFilterPick(){
        return new Gson().fromJson(preferences.getString(FILTER_PICK, "[]"), new TypeToken<ArrayList<Tag>>() {
        }.getType());
    }

    private void setFilterPick(ArrayList<Tag> filterList){
        preferences.edit().putString(FILTER_PICK,new Gson().toJson(filterList)).apply();
    }

    public void putTag(Tag tag){
        ArrayList<Tag> currentPick = getFilterPick();
        currentPick.add(tag);
        setFilterPick(currentPick);
    }

    public void deleteTag(Tag tag){
        ArrayList<Tag> currentPick = getFilterPick();
        currentPick.remove(tag);
        setFilterPick(currentPick);
    }

    public boolean isTagContains(Tag tag){
        ArrayList<Tag> currentPick = getFilterPick();
        return currentPick.contains(tag);
    }

    public ArrayList<String> getPickedTagList(){
        ArrayList<Tag> tags = getFilterPick();
        ArrayList<String> tagsName = new ArrayList<>();
        for(Tag tag : tags){
            tagsName.add(tag.getName());
        }
        return tagsName;
    }

    public int getCountFilterPick(){
        ArrayList<Tag> filterPick = new Gson().fromJson(preferences.getString(FILTER_PICK, "[]"), new TypeToken<ArrayList<Tag>>() {
        }.getType());
        return filterPick.size();
    }
}
