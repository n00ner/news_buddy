package com.n00ner.newsbuddy.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.n00ner.newsbuddy.BaseApp;
import com.n00ner.newsbuddy.Constants;
import com.n00ner.newsbuddy.models.Tag;
import com.n00ner.newsbuddy.models.Theme;

import java.util.ArrayList;
import java.util.HashMap;

public class ThemesController {

    private SQLiteHelper db;

    public ThemesController(){
        db = new SQLiteHelper(BaseApp.getAppContext());
    }

    public boolean addTheme(Theme theme){
        ContentValues values = new ContentValues();
        values.put(Constants.COL_THEMES_ID, theme.getId());
        values.put(Constants.COL_THEMES_NAME, theme.getName());
        return db.insertData(Constants.TABLE_THEMES, values);
    }

    public boolean deleteTheme(Theme theme){
       boolean themeResult =  db.deleteData(Constants.TABLE_THEMES,Constants.COL_THEMES_ID, theme.getId());
       db.deleteData(Constants.TABLE_TAGS, Constants.COL_TAGS_THEME_ID, theme.getId());
       return themeResult;
    }

    public boolean updateTheme(Theme theme){
        ContentValues values = new ContentValues();
        values.put(Constants.COL_THEMES_NAME, theme.getName());
        return db.updateData(Constants.TABLE_THEMES, values, Constants.COL_THEMES_ID, theme.getId());
    }

    public ArrayList<Theme> fetchThemes(){
        ArrayList<Theme> themes = new ArrayList<>();
        SQLiteDatabase database = db.getDb();

        try{
            Cursor cursor = database.rawQuery(Constants.getAllRecordsQuery(Constants.TABLE_THEMES), null);
            try{
                if(cursor.moveToFirst()){
                    do{
                        themes.add(new Theme(cursor.getString(0), cursor.getString(1)));
                    }while (cursor.moveToNext());
                }
            }finally {
                try{
                    cursor.close();
                }catch (Exception exception){}
            }
        }finally {
            try{
                database.close();
            }catch (Exception exception){}
        }
        return themes;
    }

    public Theme getThemeByTag(Tag tag){
       ArrayList<Theme> themes = fetchThemes();
       if(!themes.contains(tag.getThemeId())){
           return null;
       }
       for(Theme theme : themes){
           if(theme.getId().equals(tag.getThemeId())){
               return theme;
           }
       }
       return null;
    }

    public HashMap<String, String> getMappedThemes(){
        HashMap<String, String> mapThemes = new HashMap<>();
        ArrayList<Theme> themes = fetchThemes();
        for(Theme theme : themes){
           ArrayList<Tag> tags = new TagsController().fetchTagsByThemeId(theme.getId());
           for(Tag tag : tags){
               mapThemes.put(tag.getName(), theme.getName());
           }
        }
        return mapThemes;
    }
}
