package com.n00ner.newsbuddy.storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.n00ner.newsbuddy.Constants;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context){
        super(context, Constants.DATABASE_THEMES, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.QUERY_CREATE_TAGS_TABLE);
        db.execSQL(Constants.QUERY_CREATE_THEMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_TAGS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_THEMES);
    }

    public boolean insertData(String table, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(table, null, values) != -1L;
    }

    public boolean deleteData(String table, String key, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table,key + "= '" + id + "'", null) != -1L;
    }

    public boolean updateData(String table, ContentValues values, String key, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(table, values, key + "= '"+id + "'", null) != -1L;
    }

    public SQLiteDatabase getDb(){
        return this.getReadableDatabase();
    }
}
