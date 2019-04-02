package com.n00ner.newsbuddy.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.n00ner.newsbuddy.BaseApp;
import com.n00ner.newsbuddy.Constants;
import com.n00ner.newsbuddy.models.Tag;

import java.util.ArrayList;

public class TagsController {
    private SQLiteHelper db;

    public interface OnTagsCountChange{
        void onTagsCountChanged(int count);
    }

    public TagsController(){
        db = new SQLiteHelper(BaseApp.getAppContext());
    }

    public ArrayList<Tag> fetchTagsByThemeId(String id){
        ArrayList<Tag> tags = new ArrayList<>();
        SQLiteDatabase database = db.getDb();
        try{
            Cursor cursor = database.rawQuery(Constants.getAllRecordsQueryById(Constants.TABLE_TAGS, Constants.COL_TAGS_THEME_ID), new String[]{id});
            try{
                if(cursor.moveToFirst()){
                    do{
                       cursor.getColumnCount();
                       tags.add(new Tag(cursor.getString(0), cursor.getString(2), cursor.getString(1)));
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
        return tags;
    }

    public boolean addTag(Tag tag){
        ContentValues values = new ContentValues();
        values.put(Constants.COL_TAGS_ID, tag.getId());
        values.put(Constants.COL_TAG_NAME, tag.getName());
        values.put(Constants.COL_TAGS_THEME_ID, tag.getThemeId());
        return db.insertData(Constants.TABLE_TAGS, values);
    }

    public boolean deleteTag(Tag tag){
       return db.deleteData(Constants.TABLE_TAGS,Constants.COL_TAGS_ID, tag.getId());
    }

    public boolean updateTag(Tag tag){
        ContentValues values = new ContentValues();
        values.put(Constants.COL_TAG_NAME, tag.getName());
        return db.updateData(Constants.TABLE_TAGS,values, Constants.COL_TAGS_ID, tag.getId() );
    }
}
